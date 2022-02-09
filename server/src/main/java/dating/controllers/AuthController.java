package dating.controllers;

import dating.security.AppUserService;
import dating.security.JwtConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/security")
public class AuthController {

    AppUserService service;
    AuthenticationManager authManager;
    JwtConverter converter;

    public AuthController(AuthenticationManager authManager, JwtConverter converter, AppUserService service) {
        this.authManager = authManager;
        this.converter = converter;
        this.service = service;
    }

    @PostMapping("/authenticate")
    public ResponseEntity authenticate(@RequestBody Map<String,String> credentials) {

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        credentials.get("username"),
                        credentials.get("password"));

        try {
            Authentication authResult = authManager.authenticate(token);

            if (authResult.isAuthenticated()) {

                String jwt = converter.getTokenFromUser((User)authResult.getPrincipal());
                Map<String,String> tokenWrapper = new HashMap<>();
                tokenWrapper.put("jwt_token", jwt);

                return ResponseEntity.ok(tokenWrapper);
            }
        } catch (AuthenticationException ex) {

            System.err.println(ex.getMessage());
            ex.printStackTrace(System.err);
        }

        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<Map<String, String>> refreshToken(UsernamePasswordAuthenticationToken principal) {
        User user = new User(principal.getName(), principal.getName(), principal.getAuthorities());
        String jwtToken = converter.getTokenFromUser(user);

        HashMap<String, String> map = new HashMap<>();
        map.put("jwt_token", jwtToken);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}

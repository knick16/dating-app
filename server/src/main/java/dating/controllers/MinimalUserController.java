package dating.controllers;

import dating.domain.Result;
import dating.models.MinimalUser;
import dating.security.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/user/profile")
public class MinimalUserController {

    // Field
    AppUserService appUserService;

    // Constructor
    public MinimalUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    // Method: Create new user preferences.
    @PostMapping("/preferences/add")
    public ResponseEntity<Object> createPreferences(@RequestBody MinimalUser user) {

        Result<Object> result = appUserService.createPreferences(user.getUserId(), user.getAge(), user.getUserGender(),
                user.getPreferredGender(), user.getTravelRadius());

        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }

        return ErrorResponse.build(result);
    }
}

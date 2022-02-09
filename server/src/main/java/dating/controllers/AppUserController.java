package dating.controllers;

import dating.security.AppUserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/user")
public class AppUserController {

    // Field
    AppUserService appUserService;

    // Constructor
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }
}

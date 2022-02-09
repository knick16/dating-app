package dating.security;

import dating.data.AppUserRepository;
import dating.models.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService implements UserDetailsService {

    // Fields
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder encoder;

    // Constructor
    public AppUserService(AppUserRepository repository,
                          PasswordEncoder encoder) {
        this.appUserRepository = repository;
        this.encoder = encoder;
    }

    // Method: Load a user by username.
    @Override
    public AppUser loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser appUser = appUserRepository.findByUsername(username);

        if (appUser == null || !appUser.isEnabled()) {
            throw new UsernameNotFoundException(username + " not found.");
        }

        return appUser;
    }

    // Method: Find all standard users.
    public List<AppUser> findAllStandard() {
        List<AppUser> users = appUserRepository.findAllStandard();

        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("No users found.");
        }

        return users;
    }
}
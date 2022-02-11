package dating.security;

import dating.data.AppUserRepository;
import dating.domain.Result;
import dating.domain.ResultType;
import dating.models.AppUser;
import dating.models.MinimalUser;
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

    // Method: Register a new user.
    public Result<AppUser> create(String username, String password, String email, String firstName, String lastName) {

        Result<AppUser> result = validateUser(username, password, email, firstName, lastName);
        if (!result.isSuccess()) return result;

        password = encoder.encode(password);

        AppUser appUser = new AppUser(0, username, password, email, firstName, lastName, false, List.of("USER"));

        AppUser createdUser = appUserRepository.create(appUser);

        if (createdUser == null) {
            result.addMessage("Unknown data access error.", ResultType.NOT_FOUND);
        } else {
            result.setPayload(createdUser);
        }

        return result;
    }

    // Method: Add user profile information.
    public Result<Object> createPreferences(int userId, int age, String userGender, String preferredGender, int travelRadius) {

        Result<Object> result = validatePreferences(age, userGender, preferredGender, travelRadius);
        if (!result.isSuccess()) return result;

        // TODO: Validate userId as well!

        MinimalUser user = new MinimalUser();
        user.setUserId(userId);
        user.setAge(age);
        user.setUserGender(userGender);
        user.setPreferredGender(preferredGender);
        user.setTravelRadius(travelRadius);

        if (!appUserRepository.createPreferences(user)) {
            result.addMessage("Failed to update user preferences.", ResultType.INVALID);
        }

        return result;
    }


    // Method: Validate that a new user meets all requirements.
    private Result<AppUser> validateUser(String username, String password, String email, String firstName,
                                         String lastName) {
        Result<AppUser> result = new Result<>();
        validateFirstName(firstName, result);
        validateLastName(lastName, result);
        validateUsername(username, result);
        validatePassword(password, result);
        validateEmail(email, result);

        return result;
    }

    // Method: Validate username.
    private Result<AppUser> validateUsername(String username, Result<AppUser> result) {

        // Username must not be null or blank.
        if (username == null || username.isBlank()) {
            result.addMessage("Username is required.", ResultType.INVALID);
            return result;
        }

        // Username must be less than 50 characters.
        if (username.length() > 50) {
            result.addMessage("Username must be less than 50 characters.", ResultType.INVALID);
            return result;
        }

        // Username must be unique in the database.
        AppUser user = appUserRepository.findByUsername(username);
        if (user != null) {
            result.addMessage("Account already exists with given username.", ResultType.INVALID);
        }

        return result;
    }

    // Method: Validate password.
    private Result<AppUser> validatePassword(String password, Result<AppUser> result) {

        // Password can't be null or less than length 8 characters.
        if (password == null || password.length() < 8) {
            result.addMessage("Password must be at least 8 characters.", ResultType.INVALID);
            return result;
        }

        // Password must include at least 1 digit, 1 letter, and 1 special character.
        int digits = 0;
        int letters = 0;
        int others = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                digits++;
            } else if (Character.isLetter(c)) {
                letters++;
            } else {
                others++;
            }
        }

        if (digits == 0 || letters == 0 || others == 0) {
            result.addMessage("Password must contain a digit, a letter, and a special character.", ResultType.INVALID);
        }

        return result;
    }

    // Method: Validate email.
    private Result<AppUser> validateEmail(String email, Result<AppUser> result) {

        // Email must not be null or blank.
        if (email == null || email.isBlank()) {
            result.addMessage("Email is required.", ResultType.INVALID);
            return result;
        }

        // Email must be unique in the database.
        AppUser user = appUserRepository.findByEmail(email);
        if (user != null) {
            result.addMessage("Account already exists with given email.", ResultType.INVALID);
        }

        return result;
    }

    // Method: Validate first name.
    private Result<AppUser> validateFirstName(String firstName, Result<AppUser> result) {

        // Name must not be null or blank.
        if (firstName == null || firstName.isBlank()) {
            result.addMessage("First name is required.", ResultType.INVALID);
            return result;
        }

        // Name must be less than 50 characters.
        if (firstName.length() > 50) {
            result.addMessage("First name must be less than 50 characters.", ResultType.INVALID);
            return result;
        }

        return result;
    }

    // Method: Validate last name.
    private Result<AppUser> validateLastName(String lastName, Result<AppUser> result) {

        // Name must not be null or blank.
        if (lastName == null || lastName.isBlank()) {
            result.addMessage("Last name is required.", ResultType.INVALID);
            return result;
        }

        // Name must be less than 50 characters.
        if (lastName.length() > 50) {
            result.addMessage("Last name must be less than 50 characters.", ResultType.INVALID);
            return result;
        }

        return result;
    }

    // Method: Validate user preferences.
    private Result<Object> validatePreferences(int age, String userGender, String preferredGender, int travelRadius) {
        Result<Object> result = new Result<>();
        validateAge(age, result);
        validateUserGender(userGender, result);
        validatePreferredGender(preferredGender, result);
        validateTravelRadius(travelRadius, result);

        return result;
    }

    // Method: Validate age.
    private Result<Object> validateAge(int age, Result<Object> result) {

        // Age must be greater than 17.
        if (age < 18) {
            result.addMessage("Age must be 18+.", ResultType.INVALID);
            return result;
        }

        return result;
    }

    // Method: Validate user gender.
    private Result<Object> validateUserGender(String userGender, Result<Object> result) {

        // Gender must not be null or blank.
        if (userGender == null || userGender.isBlank()) {
            result.addMessage("Your gender is required.", ResultType.INVALID);
        }

        // Gender must be one of three options.
        String female = "female";
        String male = "male";
        String nonBinary = "non-binary";
        if (!userGender.equals(female) && !userGender.equals(male) && !userGender.equals(nonBinary)) {
            result.addMessage("User gender must be male, female, or non-binary.", ResultType.INVALID);
        }

        return result;
    }

    // Method: Validate user gender.
    private Result<Object> validatePreferredGender(String preferredGender, Result<Object> result) {

        // Gender must not be null or blank.
        if (preferredGender == null || preferredGender.isBlank()) {
            result.addMessage("Preferred gender is required.", ResultType.INVALID);
        }

        // Gender must be one of three options.
        String female = "female";
        String male = "male";
        String none = "none";
        if (!preferredGender.equals(female) && !preferredGender.equals(male) && !preferredGender.equals(none)) {
            result.addMessage("Preferred gender must be male, female, or none.", ResultType.INVALID);
        }

        return result;
    }

    // Method: Validate travel radius.
    private Result<Object> validateTravelRadius(int travelRadius, Result<Object> result) {

        // Travel radius must be greater than zero and less than or equal to 100 miles.
        if (travelRadius < 1 || travelRadius > 100) {
            result.addMessage("Travel radius must be greater than zero and less than or equal to 100 miles.", ResultType.INVALID);
        }

        return result;
    }
}
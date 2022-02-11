package dating.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AppUser extends User {

    // fields
    private static final String AUTHORITY_PREFIX = "ROLE_";

    private int userId;
    private List<String> roles;
    private String username;
    private String email;
    private Location geolocation;

    private String firstName;
    private String lastName;


    // constructors
    public AppUser( int userId, String username, String password, String email, String firstName, String lastName,
                    boolean disabled, List<String> roles) {
        super(
                username,
                password,
                !disabled,
                true,
                true,
                true,
                convertRolesToAuthorities(roles) );

        this.userId = userId;
        this.roles = roles;
        this.email = email;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    // roles-to-authorities method
    public static List<GrantedAuthority> convertRolesToAuthorities(List<String> roles){

        return roles.stream()
                .map( r -> new SimpleGrantedAuthority( "ROLE_" + r ))
                .collect(Collectors.toList());
    }


    // authorities-to-roles method
    public static List<String> convertAuthoritiesToRoles(Collection<GrantedAuthority> authorities) {
        return authorities.stream()
                .map(a -> a.getAuthority().substring(AUTHORITY_PREFIX.length()))
                .collect(Collectors.toList());
    }


    // getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Location getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(Location geolocation) {
        this.geolocation = geolocation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

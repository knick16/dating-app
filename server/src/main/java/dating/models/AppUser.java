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

    private String userGender;
    private String preferredGender;
    private int age;
    private int travelRadius;  // miles
    private String photo;

    private String race;
    private String ethnicity;


    // constructors
    public AppUser( int userId, String username, String password, String email, int age, String userGender,
                    String preferredGender, int travelRadius, String race, String ethnicity,
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
        this.userGender = userGender;
        this.preferredGender = preferredGender;
        this.age = age;
        this.travelRadius = travelRadius;
        this.race = race;
        this.ethnicity = ethnicity;
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

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getTravelRadius() {
        return travelRadius;
    }

    public void setTravelRadius(int travelRadius) {
        this.travelRadius = travelRadius;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPreferredGender() {
        return preferredGender;
    }

    public void setPreferredGender(String preferredGender) {
        this.preferredGender = preferredGender;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }
}

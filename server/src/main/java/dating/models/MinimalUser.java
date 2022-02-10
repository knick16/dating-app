package dating.models;

public class MinimalUser {

    // fields
    private int userId;
    private String username;
    private Location geolocation;

    private String userGender;
    private String preferredGender;
    private int age;
    private int travelRadius;  // miles
    private String photo;


    // constructor
    public MinimalUser() {
        // empty constructor
    }


    // getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPreferredGender() {
        return preferredGender;
    }

    public void setPreferredGender(String preferredGender) {
        this.preferredGender = preferredGender;
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
}

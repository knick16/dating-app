package dating.data.mappers;

import dating.models.AppUser;
import dating.models.Location;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AppUserMapper implements RowMapper<AppUser> {
    private List<String> roles;

    public AppUserMapper(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public AppUser mapRow(ResultSet rs, int i) throws SQLException {

        String lat = rs.getString("latitude");
        String lon = rs.getString("longitude");
        Location geoLocation = null;

        if (lat != null && lon != null){
            geoLocation = new Location();
            geoLocation.setLatitude(new BigDecimal(lat));
            geoLocation.setLongitude(new BigDecimal(lon));
        }

        AppUser mappedUser = new AppUser(
                rs.getInt("user_id"),
                rs.getString("username"),
                rs.getString("password_hash"),
                rs.getString("email"),
                rs.getInt("age"),
                rs.getString("user_gender"),
                rs.getString("preferred_gender"),
                rs.getInt("travel_radius"),
                rs.getString("race"),
                rs.getString("ethnicity"),
                rs.getBoolean("disabled"),
                roles);

        mappedUser.setGeolocation(geoLocation);

        return mappedUser;
    }
}

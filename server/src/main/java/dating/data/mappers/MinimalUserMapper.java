package dating.data.mappers;

import dating.models.Location;
import dating.models.MinimalUser;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MinimalUserMapper implements RowMapper<MinimalUser> {

    @Override
    public MinimalUser mapRow(ResultSet rs, int i) throws SQLException {

        String lat = rs.getString("latitude");
        String lon = rs.getString("longitude");
        Location geoLocation = null;

        if (lat != null && lon != null){
            geoLocation = new Location();
            geoLocation.setLatitude(new BigDecimal(lat));
            geoLocation.setLongitude(new BigDecimal(lon));
        }

        String lastName = rs.getString("last_name");
        String lastNameInitial = lastName.charAt(0) + ".";

        MinimalUser mappedUser = new MinimalUser();

        mappedUser.setUserId(rs.getInt("user_id"));
        mappedUser.setUsername(rs.getString("username"));
        mappedUser.setGeolocation(geoLocation);
        mappedUser.setUserGender(rs.getString("user_gender"));
        mappedUser.setPreferredGender(rs.getString("preferred_gender"));
        mappedUser.setFirstName(rs.getString("first_name"));
        mappedUser.setLastName(lastNameInitial);
        mappedUser.setAge(rs.getInt("age"));
        mappedUser.setTravelRadius(rs.getInt("travel_radius"));
        mappedUser.setUserGender(rs.getString("user_gender"));


        return mappedUser;
    }
}

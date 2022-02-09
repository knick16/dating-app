package dating.data;

import dating.data.mappers.AppUserMapper;
import dating.models.AppUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AppUserJdbcTemplateRepository implements AppUserRepository {

    // Field
    private final JdbcTemplate jdbcTemplate;

    // Constructor
    public AppUserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Method: Find all standard users.
    @Override
    public List<AppUser> findAllStandard() {

        List<String> roles = new ArrayList<>();
        roles.add("USER"); // Only standard users can be found.

        final String sql = "select * from users u "
                + "inner join users_roles ur on u.user_id = ur.user_id "
                + "where role_id = 2;";

        List<AppUser> users = jdbcTemplate.query(sql, new AppUserMapper(roles));

        return users;
    }

    // Method: Find user by username (login).
    @Transactional
    @Override
    public AppUser findByUsername(String username) {

        List<String> roles = getRolesByUsername(username);

        final String sql = "select * from users "
                + "where username = ?;";

        AppUser user = jdbcTemplate.query(sql, new AppUserMapper(roles), username)
                .stream()
                .findFirst().orElse(null);

        return user;
    }

    // Method: Find a user by email (validation).
    @Override
    public AppUser findByEmail(String email) {

        final String sql = "select * from users "
                + "where email = ?;";

        AppUser user = jdbcTemplate.query(sql, new AppUserMapper(new ArrayList<>()), email)
                .stream()
                .findFirst().orElse(null);

        if (user != null) {
            List<String> roles = getRolesByUsername(user.getUsername());
            user.setRoles(roles);
        }

        return user;
    }

    // Method: Register a new user.
    @Override
    public AppUser create(AppUser user) {
        return null;
    }

    @Override
    public boolean updateLocation(AppUser user) {
        return false;
    }

    @Override
    public boolean toggleUser(String username, boolean disabled) {
        return false;
    }

    @Override
    public boolean addFriendship(String usernameA, String usernameB) {
        return false;
    }

    @Override
    public boolean removeFriendship(String usernameA, String usernameB) {
        return false;
    }

    // Method: Find roles for a user.
    private List<String> getRolesByUsername(String username) {
        final String sql = "select "
                + "r.role_name "
                + "from users_roles ur "
                + "inner join roles r on ur.role_id = r.role_id "
                + "inner join users u on ur.user_id = u.user_id "
                + "where u.username = ?;";
        return jdbcTemplate.query(sql, (rs, rowId) -> rs.getString("role_name"), username);
    }

}

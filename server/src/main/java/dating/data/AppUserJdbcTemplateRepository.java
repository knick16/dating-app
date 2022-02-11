package dating.data;

import dating.data.mappers.AppUserMapper;
import dating.models.AppUser;
import dating.models.MinimalUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
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
    @Transactional
    public AppUser create(AppUser user) {

        final String sql = "insert into users "
                + "(username, "
                + "password_hash, "
                + "email, "
                + "first_name, "
                + "last_name) "
                + "values (?, ?, ?, ?, ?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getFirstName());
            ps.setString(5, user.getLastName());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        user.setUserId(keyHolder.getKey().intValue());

        updateRoles(user);

        return user;
    }

    // Method: Add user profile information.
    @Override
    public boolean createPreferences(MinimalUser user) {

        final String sql = "insert into user_preferences "
                + "(user_id, "
                + "age, "
                + "user_gender, "
                + "preferred_gender, "
                + "travel_radius) "
                + "values (?, ?, ?, ?, ?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, user.getUserId());
            ps.setInt(2, user.getAge());
            ps.setString(3, user.getUserGender());
            ps.setString(4, user.getPreferredGender());
            ps.setInt(5, user.getTravelRadius());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return false;
        }

        return true;
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

    // Method: Update roles when creating a new user.
    private void updateRoles(AppUser user) {

        // Delete all roles, then re-add.
        jdbcTemplate.update("delete from users_roles where user_id = ?;", user.getUserId());

        Collection<GrantedAuthority> authorities = user.getAuthorities();

        if (authorities == null) {
            return;
        }

        for (String role : AppUser.convertAuthoritiesToRoles(authorities)) {
            String sql = "insert into users_roles (user_id, role_id) "
                    + "values (?, (select role_id from roles where role_name = ?));";
            jdbcTemplate.update(sql, user.getUserId(), role);
        }
    }
}

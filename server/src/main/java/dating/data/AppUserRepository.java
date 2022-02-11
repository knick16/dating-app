package dating.data;

import dating.models.AppUser;
import dating.models.MinimalUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AppUserRepository {

    @Transactional
    List<AppUser> findAllStandard();

    @Transactional
    AppUser findByUsername(String username);

    @Transactional
    AppUser findByEmail(String email);

    @Transactional
    AppUser create(AppUser user);

    @Transactional
    boolean createPreferences(MinimalUser user);

    @Transactional
    boolean updateLocation(AppUser user);

    @Transactional
    boolean toggleUser(String username, boolean disabled);

    @Transactional
    boolean addFriendship(String usernameA, String usernameB);

    @Transactional
    boolean removeFriendship(String usernameA, String usernameB);
}

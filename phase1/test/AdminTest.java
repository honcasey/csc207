import Admins.AdminManager;
import Admins.AdminUser;
import Exceptions.InvalidAdminException;
import Users.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AdminTest {

    private Object InvalidUserException;

    // Admins.AdminUser entity tests
    @Test
    void testAdmin() { AdminUser admin1 = new AdminUser("casey", "pwd123");
    }

    @Test
    void getUsername() {
        AdminUser admin1 = new AdminUser("casey", "pwd123");
        assertEquals(admin1.getUsername(), "casey");
    }

    @Test
    void getPassword() {
        AdminUser admin1 = new AdminUser("casey", "pwd123");
        assertEquals(admin1.getPassword(), "pwd123");
    }

    @Test
    void testToString() {
        AdminUser admin1 = new AdminUser("casey", "pwd123");
        assertEquals(admin1.toString(), "casey, " + admin1.getAdminId());
    }

    @Test
    void isFirstAdmin() {
        AdminUser admin1 = new AdminUser("casey", "pwd123");
        assertFalse(admin1.isFirstAdmin());
    }

    // Admins.AdminManager use case tests
    @Test
    void addAdmin() throws InvalidAdminException {
        AdminUser admin1 = new AdminUser("annie", "pwd123");
        AdminUser admin2 = new AdminUser("casey", "pwd123");
        List<AdminUser> adminUserList = new ArrayList<>();
        List<User> flaggedAccounts = new ArrayList<>();
        List<User> frozenAccounts = new ArrayList<>();
        adminUserList.add(admin1);
        adminUserList.add(admin2);
        AdminManager am = new AdminManager(adminUserList, flaggedAccounts, frozenAccounts);
        assertEquals(am.addAdmin("anna", "pwd123").getUsername(), "anna");
        assertNull(am.addAdmin("annie", "pwd456"));

    }

    @Test
    void getAdmin() throws Exception {
        AdminUser admin1 = new AdminUser("annie", "pwd123");
        AdminUser admin2 = new AdminUser("casey", "pwd123");
        List<AdminUser> adminUserList = new ArrayList<>();
        List<User> flaggedAccounts = new ArrayList<>();
        List<User> frozenAccounts = new ArrayList<>();
        adminUserList.add(admin1);
        adminUserList.add(admin2);
        AdminManager am = new AdminManager(adminUserList, flaggedAccounts, frozenAccounts);
        assertEquals(am.getAdmin("annie"), admin1);
        assertEquals(am.getAdmin("casey"), admin2);
    }

    @Test
    void getAllAdmins() {
        AdminUser admin1 = new AdminUser("annie", "pwd123");
        AdminUser admin2 = new AdminUser("casey", "pwd123");
        List<AdminUser> adminUserList = new ArrayList<>();
        List<User> flaggedAccounts = new ArrayList<>();
        List<User> frozenAccounts = new ArrayList<>();
        adminUserList.add(admin1);
        adminUserList.add(admin2);
        AdminManager am = new AdminManager(adminUserList, flaggedAccounts, frozenAccounts);
        assertEquals(am.getAllAdmins(), adminUserList);
    }

    @Test
    void validAdmin() {
        AdminUser admin1 = new AdminUser("annie", "pwd123");
        AdminUser admin2 = new AdminUser("casey", "pwd123");
        List<AdminUser> adminUserList = new ArrayList<>();
        List<User> flaggedAccounts = new ArrayList<>();
        List<User> frozenAccounts = new ArrayList<>();
        adminUserList.add(admin1);
        adminUserList.add(admin2);
        AdminManager am = new AdminManager(adminUserList, flaggedAccounts, frozenAccounts);
        assertTrue(am.validAdmin("casey", "pwd123"));
        assertTrue(am.validAdmin("annie", "pwd123"));
        assertFalse(am.validAdmin("casey", "pwd456"));
        assertFalse(am.validAdmin("casey123", "pwd123"));
    }



}

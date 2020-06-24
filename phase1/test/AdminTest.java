import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class AdminTest {

    @Test
    void testAdmin() { AdminUser admin1 = new AdminUser("master", "pwd123");
    }

    @Test
    void getUsername() {
        AdminUser admin1 = new AdminUser("master", "pwd123");
        assertEquals(admin1.getUsername(), "master");
    }

    @Test
    void getPassword() {
        AdminUser admin1 = new AdminUser("master", "pwd123");
        assertEquals(admin1.getPassword(), "pwd123");
    }

    @Test
    void testToString() {
        AdminUser admin1 = new AdminUser("master", "pwd123");
        assertEquals(admin1.toString(), "master" + admin1.getAdminId());
    }
}

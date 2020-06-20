import org.junit.*;

import static org.junit.Assert.assertEquals;

public class UserTest {

    // the User constructor
    @Test
    public void testUser() {
        User casey = new User("caseyh", "pwd", "caseyh@email.com");
    }

    // test User methods
    @Test
    public void testUserMethods() {
        User casey = new User("caseyh", "pwd", "caseyh@email.com");
        assertEquals("caseyh", casey.getUsername());
    }

}

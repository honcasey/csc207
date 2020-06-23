import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @org.junit.jupiter.api.Test
    void testItem() {
        Item item = new Item("Hamlet");
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        Item item = new Item("Hamlet");

        assertEquals(item.getName(), "Hamlet");
    }

    @org.junit.jupiter.api.Test
    void getId() {
    }

    @org.junit.jupiter.api.Test
    void setName() {
    }

    @org.junit.jupiter.api.Test
    void getName() {
    }

    @org.junit.jupiter.api.Test
    void setDescription() {
    }

    @org.junit.jupiter.api.Test
    void getDescription() {
    }
}
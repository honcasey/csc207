import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @org.junit.jupiter.api.Test
    void testItem() {
        Item item = new Item("Hamlet");
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        Item item = new Item("Hamlet");
        assertEquals(item.toString(), "Hamlet");
    }

    @org.junit.jupiter.api.Test
    void setName() {
        Item item = new Item("Hamlet");
        item.setName("Hamlet v2");
        assertEquals(item.getName(), "Hamlet v2");
    }

    @org.junit.jupiter.api.Test
    void setDescription() {
        Item item = new Item("Hamlet");
        item.setDescription("A book");
        assertEquals(item.getDescription(), "A book");
        item.setDescription("A good book!!!");
        assertEquals(item.getDescription(), "A good book!!!");
    }
}
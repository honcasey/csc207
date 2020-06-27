import java.time.LocalDate;
import java.util.HashMap;

public class TransactionOneWayPerm extends Transaction{
    /**
     * This class represents a meetup between 2 Users where there is a permanent one way transaction happening.
     * Note: user 1 owns the item and the second user takes the item.
     *
     * Class Variables:
     *
     * userEditable: This is a mapping of all the information that the user can change. This contains capitalized
     * strings mapping to the information they change.
     *
     * -- userEditable Variables --
     * Item. (will be stored in the hashmap)
     * NOTE: getters and setters for certain information still remain camel-case.
     */

    private HashMap<String,Object> userEditable;

    /**
     * Constructor for TransactionOneWayPerm. The way the constructor is implemented assumes that User2 takes the
     * item from User1.
     * The Item's path is: User1 --> User2
     * @param User1 the first user.
     * @param User2 the second user.
     * @param item this is the item possessed by the lender of the one way transaction which will be assumed to be
     *             user1.
     * @param firstMeeting the first meeting for the deal.(and the only one because it is permanent.)
     */
    //Constructor with no return time given (default is a month (31 days))
    public TransactionOneWayPerm(User User1, User User2, Item item, Meeting firstMeeting){
        super(User1,User2, firstMeeting);
        this.userEditable.put("Item",item);
    }


    /**
     * Getter for item in the transaction.
     */
    //Constructor with a return time manually inputted
    public Item getItem(){
        return (Item) this.userEditable.get("Item");
    }

    /**
     * Setter for item in the transaction.
     */
    public void setItem(Item item1) {
        this.userEditable.put("Item",item1);
    }

    @Override
    public boolean isOneWay() {
        return true;
    }

    @Override
    public boolean isPerm(){return true;}

    /**
     * THE IMPLEMENTATION OF THIS METHOD NEEDS TO BE DONE.
     * @param Field this is the detail of the transaction you want to change.
     *              (the values it can take on are listed above:)
     * @param NewVal this is the new value of the detail you want changed.
     * @return this returns true iff the transaction detail was found and changed successfully.
     */
    @Override
    protected boolean userChangeFirstMeetingByString(String Field, Object NewVal){
        return(false);
    }


}


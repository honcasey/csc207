/**
 * This class represents a meetup between 2 Users where there is a two way borrowing transaction happening.
 * Note: user 1 originally had item 1 and user 2 originally had item 2.
 */

public class TransactionTwowayTemp extends Transaction{
    String returnMeeting;
    public Item item1;
    public Item item2;
    protected int return_time = 31;

    /**
     * Constructor for TransactionTwowayTemp class. This constructor is the default if return time is given.
     * @param User1 the first user.
     * @param User2 the second user.
     * @param Item1 the item possessed originally by User1.
     * @param Item2 the item possessed originally by User2
     *
     */
    //Constructor with no return time given (default is a month (31 days))
    public TransactionTwowayTemp(User User1, User User2,Item Item1,Item Item2){
        super(User1,User2);
        this.item1 = Item1;
        this.item2 = Item2;
    }
    /**
     * Constructor for TransactionTwowayTemp class. This constructor is the default if return time is given.
     * @param User1 the first user.
     * @param User2 the second user.
     * @param Item1 the item possessed originally by User1.
     * @param Item2 the item possessed originally by User2
     * @param return_time this is the time in days before they meet up
     */
    //Constructor with a return time manually inputted
    public TransactionTwowayTemp(User User1, User User2,Item Item1,Item Item2,int return_time){
        super(User1,User2);
        this.item1 = Item1;
        this.item2 = Item2;
        this.return_time = return_time;
    }
    public Item getItem1(){
        return this.item1;
    }

    public void setItem1(Item item1) {
        this.item1 = item1;
    }
}

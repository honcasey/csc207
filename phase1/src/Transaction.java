
import java.time.LocalDate;

/**
 * This abstract class represents a meetup between 2 Users.
 *
 */
public abstract class Transaction {
    public String status;
    protected String firstMeetingLocation;
    protected int id;
    protected static int numTransactions = 0;
    protected static int maxEdit = 3;
    protected int currentEdit = 0;
    protected User user1;
    protected User user2;
    protected LocalDate firstMeetingTime = LocalDate.now();

    /**
     * Constructor of abstract class Transaction.
     * @param user_1 one of the users involved in the transactions.
     * @param user_2 one of the users involved in the transactions.
     */
    public Transaction(User user_1, User user_2,String firstMeetingLocation){
        status = "pending";
        id = numTransactions + 1;
        numTransactions = numTransactions + 1;
        this.user1 = user_1;
        this.user2 = user_2;
        this.firstMeetingLocation = firstMeetingLocation;
    }

    /**
     * Setter for status. This will be called by use case classes.
     */

    public void setStatus(String newStatus){
        status = newStatus;
    }

    /**
     * Getter for status. This will be called by use case classes.
     */
    public String getStatus(){
        return status;
    }

    /**
     * Setter for the first meeting location. This will be called by use case classes.
     */
    public void setFirstMeetingLocation(String location){
        firstMeetingLocation = location;
    }

    /**
     * Getter for the first meeting location. This will be called by use case classes.
     */

    public String getFirstMeetingLocation(){
        return firstMeetingLocation;
    }

    /**
     * Setter for maxEdit. This will be called by use case classes.
     */

    public void setMaxEdit(int max){
        maxEdit = max;
    }

    /**
     * getter for maxEdit. This will be called by use case classes.
     */

    public int getMaxEdit(){
        return maxEdit;
    }

    /**
     * setter for user1. This will be called by use case classes.
     */

    public void setUser1(User user1) {
        this.user1 = user1;
    }
    /**
     * getter for user1. This will be called by use case classes.
     */

    public User getUser1(){
        return this.user1;
    }

    /**
     * setter for user1. This will be called by use case classes.
     */

    public void setUser2(User user2) {
        this.user2 = user2;
    }
    /**
     * getter for user2. This will be called by use case classes.
     */

    public User getUser2(){
        return this.user2;
    }

    /**
     * getter for meeting time. This will be called by use case classes.
     */
    public LocalDate getfirstmeetingTime() {
        return firstMeetingTime;
    }

    /**
     * setter for meeting time. This will be called by use case classes.
     */

    public void setfirstMeetingTime(LocalDate meetingTime) {
        this.firstMeetingTime = meetingTime;
    }
}

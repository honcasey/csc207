
import java.time.LocalDate;
import java.util.UUID;

/**
 * This abstract class represents a meetup between 2 Users.
 *
 */
public abstract class Transaction {
    public String status;
    protected String firstMeetingLocation;
    protected UUID id = UUID.randomUUID();
    protected User user1;
    protected User user2;
    protected Meeting firstMeeting;

    /**
     * Constructor of abstract class Transaction.
     * @param user_1 one of the users involved in the transactions.
     * @param user_2 one of the users involved in the transactions.
     */
    public Transaction(User user_1, User user_2, Meeting firstMeeting){
        status = "pending";
        this.user1 = user_1;
        this.user2 = user_2;
        this.firstMeeting = firstMeeting;
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
    public Meeting getfirstMeeting() {
        return firstMeeting;
    }

    /**
     * setter for meeting time. This will be called by use case classes.
     */

    public void setfirstMeeting(Meeting meeting) {
        this.firstMeeting = meeting;
    }

}

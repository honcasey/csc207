package Transactions;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;


public class Meeting implements Serializable {
    /**
     * This class represents a meeting among 2 users.
     * Variables:
     *
     * user1approved: Keeps track of whether the user has confirmed the details of the meeting.
     *
     * user2approved: explained above.
     *
     * numEdits: this stores the number of times that both users have made an edit to the program (check phase 1
     * if you disagree.) example: user1 and user2 need to both edit this meeting in order for numedits to increase to 1
     * from 0.
     *
     * maxNumEdits: this stores the maximum number of edits that can be done by both users before the transaction is
     * cancelled.
     *
     * userEditable: This is a mapping of all the information that the user can change. This contains capitalized
     * strings mapping to the information they change.
     *
     * -- userEditable Variables --
     * Time, Date, Location. (will be stored in the hashmap)
     * NOTE: getters and setters for certain information still remain camel-case.
     */
    private boolean user1approved = false;
    private boolean user2approved = true;
    private int numEditsUser1 = 0;
    private int numEditsUser2 = 0;
    private int maxNumEdits = 3;
    private String location;
    private LocalDate meetingDate;
    private LocalTime meetingTime;

    /**
     * The constructor for the class Transactions.Meeting.
     * @param location location to be stored in usereditable hashmap.
     * @param meetingDate the dateof the meetup as a  LocalDate object.
     * @param meetingTime The time of the meetup as a localTime object.
     */

    public Meeting(String location, LocalTime meetingTime, LocalDate meetingDate){
        this.location = location;
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
    }

    /**
     * Getter for if user1 has approved meeting details
     * @return returns true iff user has approved meeting details.
     */
    public boolean getUser1approved(){return user1approved;}

    /**
     * Getter for if user2 has approved meeting details
     * @return returns true iff user2 has approved meeting details
     */

    public boolean getUser2approved(){return user2approved;}

    /**
     * Getter for number of edits for this particular meeting by User1.
     * @return returns the number of times when user1 has edited a meeting.
     */
    public int getNumEditsUser1(){return numEditsUser1;}

    /**
     * Getter for number of edits for this particular meeting by User2.
     * @return returns the number of times when user2 has edited a meeting.
     */
    public int getNumEditsUser2(){return numEditsUser2;}


    /**
     * This is a getter for location.
     * @return this returns the value of location for the meeting.
     */

    public String getLocation(){
        return(this.location);
    }

    /**
     * This is a getter for the time of the meeting.
     * @return this returns an instance for the time of the meeting.
     */
    public LocalTime getTime(){
        return(this.meetingTime);
    }

    /**
     * This method returns a string representation of the meeting time.
     * @return this returns a string representation of the meeting time.
     */
    public String DisplayMeetingTime(){
        return(this.meetingTime.toString());
    }

    /**
     * This is a getter for the date of the meeting.
     * @return this returns the instance of the date for the meeting.
     */
    public LocalDate getDate(){
        return(this.meetingDate);
    }

    /**
     * Setter for the location variable in the meeting class.
     * @param newLocation the new location.
     */
    public void setLocation(String newLocation){
        this.location = newLocation;
    }

    /**
     * This is a setter for the date of the transaction.
     * @param NewDate this is an instance of the new date object.
     */
    public void setDate(LocalDate NewDate) {
        this.meetingDate =  NewDate;
    }

    /**
     * This is a setter for the time of the transaction.
     * @param NewTime the hour of the time to be set.
     */

    public void setTime(LocalTime NewTime){
        this.meetingTime = NewTime;
    }

    /**
     * This is setter for user1 status on meeting details.
     * @param bool new confirmation status.
     */
    public void setUser1approved(Boolean bool){this.user1approved = bool;}

    /**
     * this is a setter for user2 status on meeting details.
     * @param bool new confirmation status.
     */
    public void setUser2approved(Boolean bool){this.user2approved = bool;}

    /**
     * this method is called when user1 edits the meeting, his number of edit increase by one
     */
    public void user1edits(){this.numEditsUser1 ++;}

    /**
     * this method is called when user2 edits the meeting, his number of edit increase by one
     */
    public void user2edits(){this.numEditsUser2 ++;}

    /**
     * Getter for number of maxNumeditsUser1 for this particular meeting.
     * @return returns the number of times both users are allowed to edit a meeting.
     */
    public int getMaxNumEdits() {
        return maxNumEdits;
    }

    /**
     * this is the setter for the maxNumEdits variable defined in the beggining of the documentation for this class.
     * @param num new value for numedits variable.
     */
    public void setMaxNumEdits(int num){
        maxNumEdits = num;
    }

    @Override
    public String toString(){
        return("Transactions.Meeting is at "+this.getLocation()+ "at this time:" +this.getTime().toString()+ " on this day:" +
                this.getDate().toString());
    }
}


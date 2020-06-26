
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;


public class Meeting {
    /**
     * This class represents a meeting among 2 users.
     * Variables:
     *
     * user1approved: Keeps track of whether the user has confirmed the details of the meeting.
     *
     * user2approved: explained above.
     *
     * numedits: this stores the number of times that both users have made an edit to the program (check phase 1
     * if you disagree.) example: user1 and user2 need to both edit this meeting in order for numedits to increase to 1
     * from 0.
     *
     * userEditable: This is a mapping of all the information that the user can change. This contains capitalized
     * strings mapping to the information they change.
     *
     * -- userEditable Variables --
     * Time, Date, Location. (will be stored in the hashmap)
     * NOTE: getters and setters for certain information still remain camel-case.
     */
    private Boolean user1approved = Boolean.FALSE;
    private Boolean user2approved = Boolean.TRUE;
    private int numEdits = 0;
    private HashMap<String,Object> userEditable = new HashMap<>();

    /**
     * The constructor for the class Meeting.
     * @param location location to be stored in usereditable hashmap.
     * @param year The year for the date of the meeting.
     * @param month the month for the date of the meeting
     * @param dayOfMonth the day of the month for the meeting.
     * @param hour the hour for the meeting.
     * @param minutes the minute for the meeting.
     */

    Meeting(String location, int year, int month, int dayOfMonth, int hour, int minutes){
        this.userEditable.put("Location",location);
        this.userEditable.put("Time",LocalTime.of(hour, minutes));
        this.userEditable.put("Date", LocalDate.of(year,month,dayOfMonth));
    }

    /**
     * Getter for if user1 has approved meeting details
     * @return returns true iff user has approved meeting details.
     */
    public Boolean getUser1approved(){return user1approved;}

    /**
     * Getter for if user2 has approved meeting details
     * @return returns true iff user2 has approved meeting details
     */

    public Boolean getUser2approved(){return user2approved;}

    /**
     * Getter for number of edits for this particular meeting.
     * @return returns the number of times both users have edited a meeting.
     */
    public int getNumEdits(){return numEdits;}

    /**
     * This is a getter for location.
     * @return this returns the value of location for the meeting.
     */

    public String getLocation(){
        return (String) this.userEditable.get("Location");
    }

    /**
     * This is a getter for the time of the meeting.
     * @return this returns an instance for the time of the meeting.
     */
    public LocalTime getTime(){
        return (LocalTime) this.userEditable.get("Time");
    }

    /**
     * This is a getter for the date of the meeting.
     * @return this returns the instance of the date for the meeting.
     */
    public LocalDate getDate(){
        return (LocalDate) this.userEditable.get("Date");
    }


    /**
     * This method is a helper method that lets usecase classes edit user-editable information.
     * DO NOT USE THIS AS A GETTER(if you have any tips on how to make this so only certain classes can access this
     * that would be great.)
     * @param FieldString The field that needs to be changed.
     * @param NewVal the new value corresponding the field above.
     * @return returns true iff method finds information in hashmap and changes it successfully.
     */
    protected Boolean userChangeByString(String FieldString, Object NewVal){
        if(this.userEditable.containsKey(FieldString)) {
            this.userEditable.put(FieldString, NewVal);
            return(true);
        }
        else{
            return(false);
        }
    }

    /**
     * This is a setter for changing a new location. This u
     * @param newLocation the new location.
     */

    public void setLocation(String newLocation){
        this.userEditable.put("Location",newLocation);
    }


    /**
     * This is a setter for the date of the transaction.
     * @param year year of the date to be set.
     * @param month month of the date to be set.
     * @param dayOfMonth day of the month to be set.
     */
    public void setDate(int year, int month, int dayOfMonth) {
        this.userEditable.put("Date", LocalDate.of(year, month, dayOfMonth));
    }

    /**
     * This is a setter for the time of the transaction.
     * @param hour the hour of the time to be set.
     * @param minutes the minute of the time to be set.
     */

    public void setTime(int hour, int minutes){
        this.userEditable.put("Time",LocalTime.of(hour, minutes));
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
     * this is the setter for the numEdits variable defined in the beggining of the documentation for this class.
     * @param num new value for numedits variable.
     */
    public void setNumEdits(int num){this.numEdits = num;}

}


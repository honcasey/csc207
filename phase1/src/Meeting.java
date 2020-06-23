
import java.time.LocalDate;
import java.time.LocalTime;
public class Meeting {
    String location;
    LocalDate date;
    LocalTime time;
    Boolean user1approved = Boolean.FALSE;
    Boolean user2approved = Boolean.TRUE;


    Meeting(String location, int year, int month, int dayOfMonth, int hour, int minutes){
        this.location = location;
        this.date = LocalDate.of(year, month, dayOfMonth);
        this.time = LocalTime.of(hour, minutes);

    }

    LocalDate getDate(){return date;}
    LocalTime getTime(){return time;}
    String getLocation(){return location;}
    
    void setLocation(String newLocation){location = newLocation;}
    void setDate(int year, int month, int dayOfMonth){this.date = LocalDate.of(year, month, dayOfMonth);}
    void setTime(int hour, int minutes){this.time = LocalTime.of(hour, minutes);}
}


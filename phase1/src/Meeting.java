
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
    Boolean getUser1approved(){return user1approved;}
    Boolean getUser2approved(){return user2approved;}

    void setLocation(String newLocation){location = newLocation;}
    void setDate(int year, int month, int dayOfMonth){this.date = LocalDate.of(year, month, dayOfMonth);}
    void setTime(int hour, int minutes){this.time = LocalTime.of(hour, minutes);}
    void setUser1approved(Boolean bool){user1approved = bool;}
    void setUser2approved(Boolean bool){user2approved = bool;}
}


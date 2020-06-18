public class Transaction {
    String status;
    String firstMeetingLocation;
    int id;
    static int numTransactions = 0;
    int maxLocationEdit = 3;
    int numLocationEdit = 0;

    void Transaction(){
        status = "pending";
        id = numTransactions + 1;
        numTransactions = numTransactions + 1;
    }

    void setStatus(String newStatus){
        status = newStatus;
    }

    String getStatus(){
        return status;
    }

    void setFirstMeetingLocation(String location){
        firstMeetingLocation = location;
    }

    String getFirstMeetingLocation(){
        return firstMeetingLocation;
    }

    void setMaxLocationEdit(int max){
        maxLocationEdit = max;
    }

    int getMaxLocationEdit(){
        return maxLocationEdit;
    }
    int getNumLocationEdit(){
        return numLocationEdit;
    }



}

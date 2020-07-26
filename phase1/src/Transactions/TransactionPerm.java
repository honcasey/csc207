package Transactions;

import java.util.*;

/**
 * <h1>TransactionTwoWayPerm</h1>
 * This class represents a meetup between 2 Users where there is a two way permanent transaction happening.
 * Note: user 1 originally had item 1 and user 2 originally had item 2.
 */

public class TransactionPerm extends Transaction {


    /**
     *
     * @param userToItems
     * @param FirstMeeting
     */
    //Constructor with no return time given (default is a month (31 days))
    public TransactionPerm(TreeMap<UUID,List<UUID>> userToItems, Meeting FirstMeeting){
        super(userToItems,FirstMeeting);
    }

    @Override
    public boolean isPerm() {
        return true;
    }

    @Override
    public List<Meeting> getTransactionMeetings(){
        List<Meeting> MeetingReturnList = new ArrayList<>();
        MeetingReturnList.add(this.getFirstMeeting());
        return(MeetingReturnList);
    }
}
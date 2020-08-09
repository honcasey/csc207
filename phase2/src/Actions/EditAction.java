package Actions;

import Transactions.Meeting;
import Transactions.Transaction;
import Users.TradingUser;


/**
 * Represents an "edit" action, which includes editing a Meeting's details.
 */
public class EditAction extends Action {
    private Transaction transaction;
    private int whichMeeting;
    private Meeting previousMeeting;
    private Meeting newMeeting;

    public EditAction(TradingUser user, Transaction transaction,
                      int whichMeeting, Meeting previousMeeting, Meeting newMeeting) {
        super(user);
        this.transaction = transaction;
        this.whichMeeting = whichMeeting;
        this.newMeeting = newMeeting;
        this.previousMeeting = previousMeeting;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public int getWhichMeeting() {
        return whichMeeting;
    }

    public Meeting getNewMeeting() {
        return newMeeting;
    }

    public Meeting getPreviousMeeting() {
        return previousMeeting;
    }

    public String toString() {
        return getUser() + "edited the meeting from" + previousMeeting + "to" + newMeeting;
    }
}

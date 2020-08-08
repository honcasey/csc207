package Actions;

import Transactions.Meeting;
import Transactions.Transaction;
import Users.TradingUser;


/**
 * Represents an "edit" action, which includes editing a Meeting's details.
 */
public class EditAction extends Action {
    private Transaction transaction;
    private Meeting previousMeeting;
    private Meeting newMeeting;

    public EditAction(TradingUser user,Transaction transaction, Meeting previousMeeting, Meeting newMeeting) {
        super(user);
        this.transaction = transaction;
        this.newMeeting = newMeeting;
        this.previousMeeting = previousMeeting;
    }

    public Meeting getNewMeeting() {
        return newMeeting;
    }

    public Meeting getPreviousMeeting() {
        return previousMeeting;
    }
}

package Actions;

import Transactions.Meeting;
import Transactions.Transaction;
import Users.TradingUser;

import java.io.Serializable;
import java.util.UUID;


/**
 * Represents an "edit" action, which includes editing a Meeting's details.
 */
public class EditAction extends Action implements Serializable {
    private Transaction transaction;
    private int whichMeeting;
    private Meeting previousMeeting;
    private Meeting newMeeting;

    public EditAction(UUID userId, Transaction transaction,
                      int whichMeeting, Meeting previousMeeting, Meeting newMeeting) {
        super(userId);
        this.transaction = transaction;
        this.whichMeeting = whichMeeting;
        this.newMeeting = newMeeting;
        this.previousMeeting = previousMeeting;
    }

    /**
     * Returns the Transaction of the editable Action
     * @return Transaction
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * Returns which meeting is currently being edited
     * @return int representing the meeting in question
     */
    public int getWhichMeeting() {
        return whichMeeting;
    }

    /**
     * Returns the previous Meeting
     * @return desired previous meeting
     */

    public Meeting getPreviousMeeting() {
        return previousMeeting;
    }

    /**
     * A string representation of this action.
     * @return a string representation of what transaction was edited from and to.
     */
    public String toString() {
        return getUser() + " changed " + previousMeeting.toString() + "\n" + " to " + newMeeting.toString();
    }

    /**
     * Returns if an action is an EditAction
     * @return true
     */
    @Override
    public boolean isEditAction() {
        return true;
    }

    /**
     * Returns if an action is an AddOrDeleteAction
     * @return false
     */
    @Override
    public boolean isAddorDeleteAction() {
        return false;
    }
}

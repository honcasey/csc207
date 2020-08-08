package Actions;

import Transactions.Meeting;
import Users.TradingUser;

import java.util.UUID;

/**
 * Represents an "edit" action, which includes editing a Meeting's details.
 */
public class EditAction extends Action {
    private Meeting previousMeeting;
    private Meeting newMeeting;

    public EditAction(TradingUser user, Meeting previousMeeting, Meeting newMeeting) {
        super(user);
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

package Actions;

import Users.TradingUser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

/**
 * Manages and stores all undoable actions in the system.
 */
public class ActionManager {
    private LinkedHashMap<UUID, List<Action>> allActions;

    public ActionManager(LinkedHashMap<UUID, List<Action>> allActions) {
        this.allActions = allActions;
    }

    /**
     * Getter for the LinkedHashMap of all actions (keys) mapped to the Action's UUID (values).
     * @return LinkedHashMap of all actions and their UUIDs.
     */
    public LinkedHashMap<UUID, List<Action>> getAllActions() {
        return allActions;
    }

    /**
     * Appends an Action to the list of the specified User's actions.
     * @param newAction new Action object
     * @param user the specified TradingUser
     */
    public void addAction(TradingUser user, Action newAction) {
        allActions.get(user.getUserId()).add(newAction);
    }

//    /**
//     * Returns the Action object with given Action ID.
//     * @param actionId UUID of desired Action
//     * @return Action object
//     */
//    public Action getAction(TradingUser user, UUID actionId) {
//        return allActions.get(actionId);
//    }

    /**
     * Filter list of all actions by a specific user.
     */
    public List<Action> getActionsByUser(TradingUser user) {
        return allActions.get(user.getUserId());
    }


}

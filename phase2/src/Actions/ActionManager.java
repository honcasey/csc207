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
    private LinkedHashMap<UUID, Action> allActions;

    public ActionManager(LinkedHashMap<UUID, Action> allActions) {
        this.allActions = allActions;
    }

    /**
     * Getter for the LinkedHashMap of all actions (keys) mapped to the Action's UUID (values).
     * @return LinkedHashMap of all actions and their UUIDs.
     */
    public LinkedHashMap<UUID, Action> getAllActions() {
        return allActions;
    }

    /**
     * Appends an Action to the list of all actions.
     * @param newAction new Action object
     */
    public void addAction(Action newAction) {
        allActions.put(newAction.getId(), newAction);
    }

    /**
     * Returns the Action object with given Action ID.
     * @param actionId UUID of desired Action
     * @return Action object
     */
    public Action getAction(UUID actionId) {
        return allActions.get(actionId);
    }

    /**
     * Filter list of all actions by a specific user.
     */
    public List<Action> getActionsByUser(TradingUser user) {
        List<Action> actions = new ArrayList<>();
        for (Action action : allActions.values()) {
            if (action.getUser().equals(user)) {
                actions.add(action);
            }
        }
        return actions;
    }


}

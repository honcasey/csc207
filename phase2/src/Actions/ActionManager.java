package Actions;

import java.util.LinkedHashMap;
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
     * Getter for the LinkedHashMap of all actions (values) mapped to their UUID (keys).
     * @return LinkedHashMap of all actions (value) and their UUIDs (key).
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
}

package Actions;

import Transactions.Transaction;
import Users.TradingUser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

/**
 * Manages and stores all undoable actions in the system.
 */
public class ActionManager {
    private LinkedHashMap<TradingUser, List<Action>> allActions;

    public ActionManager(LinkedHashMap<TradingUser, List<Action>> allActions) {
        this.allActions = allActions;
    }

    /**
     * Getter for the LinkedHashMap of all actions (keys) mapped to the Action's UUID (values).
     * @return LinkedHashMap of all actions and their UUIDs.
     */
    public LinkedHashMap<TradingUser, List<Action>> getAllActions() {
        return allActions;
    }

    /**
     * Appends an Action to the list of the specified User's actions.
     * @param newAction new Action object
     * @param user the specified TradingUser
     */
    public void addAction(TradingUser user, Action newAction) {
        if (!allActions.containsKey(user)) {
            List<Action> listActions = new ArrayList<>();
            listActions.add(newAction);
            allActions.put(user, listActions);
        }
        else {
            allActions.get(user).add(newAction);
        }
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
        if (allActions.keySet().contains(user)) {
            return allActions.get(user.getUserId());
        }
        return null; // if this TradingUser hasn't made any actions yet
    }

    public List<EditAction> getEditActionsByUser(TradingUser user) {
        List<EditAction> lst = new ArrayList<>();
        for (Action action : getActionsByUser(user)) {
            if (action.isEditAction()) {
                lst.add((EditAction) action);
            }
        }
        return lst;
    }

    public void clearPreviousEditActions(TradingUser user, Transaction transaction) {
        for (EditAction action : getEditActionsByUser(user)) {
            if (action.getTransaction().getId().equals(transaction.getId())) {
                allActions.get(user).remove(action);
            }
        }
    }

}

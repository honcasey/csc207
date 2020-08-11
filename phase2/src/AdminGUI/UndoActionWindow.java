package AdminGUI;

import Actions.Action;
import Actions.ActionManager;
import Actions.AddOrDeleteAction;
import Actions.EditAction;
import Admins.AdminMenuController;
import Exceptions.InvalidTradingUserException;
import Items.Item;
import Popups.PopUpWindow;
import Users.TradingUser;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UndoActionWindow {
    private AdminMenuController amc;
    private ActionManager am;
    private TradingUser selectedUser;
    private Action selectedAction;

    public UndoActionWindow(AdminMenuController amc) {
        this.amc = amc;
        am = amc.getAcm();
    }

    public void display() {
        JFrame frame = new JFrame("Undo Actions");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // closes the current frame but doesn't terminate the app

        frame.setSize(new Dimension(700, 500));
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        DefaultListModel<String> users = new DefaultListModel<>(); // list of all users who have made undoable actions
        ArrayList<TradingUser> listUsers = new ArrayList<>();
        for (TradingUser user : am.getAllActions().keySet()) {
            users.addElement(user.getUsername());
            listUsers.add(user);
        }

        JList<String> allUsers = new JList<>(users);
        allUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        allUsers.setSelectedIndex(0);
        allUsers.addListSelectionListener(e -> {
            selectedUser = listUsers.get(allUsers.getSelectedIndex());
            helper(selectedUser);
        });

        panel.add(allUsers);

        frame.getContentPane().add(panel);
        frame.setVisible(true);

    }

    private void helper(TradingUser selectedUser) {
        JFrame frame2 = new JFrame("Undo Options");
        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultListModel<String> actions = new DefaultListModel<>(); // list of all actions by the selected user
        ArrayList<Action> listActions = new ArrayList<>();
        if (am.getActionsByUser(selectedUser) == null) {
            PopUpWindow pw = new PopUpWindow("No users have made any undoable actions yet!");
            pw.display();
        }
        else {
            for (Action action : am.getActionsByUser(selectedUser)) {
                actions.addElement(action.toString());
                listActions.add(action);
            }
            JList<String> usersActions = new JList<>(actions);
            usersActions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            usersActions.setSelectedIndex(0);
            usersActions.addListSelectionListener(e -> {
                selectedAction = listActions.get(usersActions.getSelectedIndex());
            });


            JButton undo = new JButton("Undo Action");
            undo.setBounds(600, 300, 100, 50);
            undo.addActionListener(e -> {
                if (selectedAction.isAddorDeleteAction()) {
                    amc.undoAddOrDeleteAction((AddOrDeleteAction) selectedAction); // idk how to not have to cast this
                } else if (selectedAction.isEditAction()) {
                    amc.undoEditAction((EditAction) selectedAction);
                }
            });

            JPanel panel = new JPanel();
            panel.add(usersActions);
            panel.add(undo);
            frame2.add(panel);
            frame2.setVisible(true);
        }

    }
}
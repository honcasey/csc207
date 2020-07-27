package Users;

import Transactions.CurrentTransactionManager;
import Transactions.Statuses;
import Transactions.Transaction;
import Users.UserMenuController;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class ViewActiveTransactionsWindow {
    private final UserMenuController umc;
    private String transactionDetails;

    public ViewActiveTransactionsWindow(UserMenuController umc) {
        this.umc = umc;
    }

    public void display() {
        List<Transaction> allTransactions = umc.currentTransactionList();

        JFrame frame = new JFrame("Active Transactions");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setSize(new Dimension(700, 500));
        frame.setLocationRelativeTo(null);

        DefaultListModel<String> transactionList = new DefaultListModel<>();
        for (Transaction transaction : allTransactions) {
            transactionList.addElement(transaction.toString());
        }

        JList trans = new JList(transactionList);
        trans.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        trans.setSelectedIndex(0);
        trans.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                JList trans1 = (JList)e.getSource();
                transactionDetails = allTransactions.get(trans1.getSelectedIndex()).toString();
            }
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(trans);

        Panel leftPanel = new Panel();
        leftPanel.add(scrollPane);

        JTextArea desc = new JTextArea(transactionDetails);

        Panel rightPanel = new Panel();
        rightPanel.add(desc);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setBounds(150, 100, 400, 200);

        frame.getContentPane().add(splitPane);

        JButton options = new JButton("Options");
        options.setBounds(600,100,100,50);
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String transactionStatus = allTransactions.get(trans.getSelectedIndex()).getStatus();
                switch (transactionStatus) {
                    case Statuses.PENDING:
                        pendingTransactionWindow();
                        break;
                    case Statuses.TRADED:
                        tradedTransactionWindow();
                        break;
                    case Statuses.CONFIRMED:
                        confirmedTransactionWindow();
                        break;
                }
            }
        });

        Panel bottomPanel = new Panel();
        bottomPanel.add(options);
        frame.add(bottomPanel);

    }

    public void pendingTransactionWindow() {

    }

    public void tradedTransactionWindow() {

    }

    public void confirmedTransactionWindow() {

    }
}

import Admins.AdminMenuController;

import javax.swing.*;
import java.awt.*;

public class AdminUserMenu {
    private final AdminMenuController amc;
    private final JButton button1 = new JButton("button1");
    private final JButton button2 = new JButton("button2");
    private final JButton button3 = new JButton("button3");
    private final JButton button4 = new JButton("button4");
    private final JButton button5 = new JButton("button5");
    private final JButton button6 = new JButton("button6");


    public AdminUserMenu(AdminMenuController amc) {
        this.amc = amc;
    }

    public void display() {
        // create the frame
        JFrame frame = new JFrame("AdminMenu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set the frame's size and centre it
        frame.setSize(new Dimension(500, 250));
        frame.setLocationRelativeTo(null);

        // layout the fields and button on the frame
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        // display the window
        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // add button's to panel
        button1.setBounds(50, 50, 100, 50);
        panel.add(button1);

        button2.setBounds(200, 50, 100, 50);
        panel.add(button2);

        button3.setBounds(350, 50, 100, 50);
        panel.add(button3);

        button4.setBounds(50, 150, 100, 50);
        panel.add(button4);

        button5.setBounds(200, 150, 100, 50);
        panel.add(button5);

        button6.setBounds(350, 150, 100, 50);
        panel.add(button6);
    }

}

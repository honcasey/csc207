package DemoUserGUI;

import Initialization.PopUpWindow;
import Items.Item;
import Users.UserMenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DemoUserRequestAddItemsWindow {

        private JPanel mainPanel = new JPanel();
        private final JButton requestButton = new JButton("Request Item");
        private final JLabel itemNameLabel = new JLabel("Item Name:");
        private final JTextField userItemNameText = new JTextField(20);
        private final JLabel itemDescriptionLabel = new JLabel("Item Description:");
        private final JTextField userItemDescription = new JTextField(100);

        public DemoUserRequestAddItemsWindow() {
        }

        public void display() {
            JFrame frame = new JFrame("Request To Add an Item");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(new Dimension(700, 500));
            frame.setLocationRelativeTo(null);

            frame.add(mainPanel);
            placeComponents(mainPanel);
            frame.setVisible(true);
            requestButton.addActionListener(e -> {
                        Item requestedItem = new Item(userItemNameText.getText());
                        requestedItem.setDescription(userItemDescription.getText());
                        new PopUpWindow("Try this feature out with the full version of the program. Upgrade today!").display();
                    }
            );

        }

        private void placeComponents(JPanel panel) {
            panel.setLayout(null);

            // add username label to panel
            itemNameLabel.setBounds(10, 20, 80, 25);
            panel.add(itemNameLabel);

            // add username field to panel
            userItemNameText.setBounds(100, 20, 165, 25);
            panel.add(userItemNameText);

            // add password label to panel
            itemDescriptionLabel.setBounds(10, 50, 80, 25);
            panel.add(itemDescriptionLabel);

            // add password field to panel
            userItemDescription.setBounds(100, 50, 165, 100);
            panel.add(userItemDescription);

            // add login button to panel
            requestButton.setBounds(10, 180, 160, 25);
            panel.add(requestButton);

        }

    }


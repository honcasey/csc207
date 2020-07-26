package Admins;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Admins.AdminMenuController;

public class CheckPendingItemsWindow {
    private final JLabel title = new JLabel("Pending Items");

    private final AdminMenuController amc;

    public CheckPendingItemsWindow(AdminMenuController amc) {
        this.amc = amc;
    }

}

package com.Lisa;

import javax.swing.*;
import java.awt.*;

/**
 * Created by lisa on 5/6/15. Adapted from Clara James
 * https://github.com/minneapolis-edu/TabbedJFrame
 */

public class TabsGUI extends JFrame {
    private JPanel rootPanel;
    private JTabbedPane tabbedPane;

    public TabsGUI() {
        setContentPane(rootPanel);

        tabbedPane = new JTabbedPane();
        rootPanel.add(tabbedPane);
        tabbedPane.add("Sell Album", new SellGUI().getPanel());
        tabbedPane.add("Acquire Album", new AcquireGUI().getPanel());
        tabbedPane.add("Consignor Accounts", new AccountsGUI().getPanel());
        tabbedPane.add("Manage Consignors", new ConsignorsGUI().getPanel());
        tabbedPane.add("Manage Inventory", new InventoryGUI().getPanel());

        setSize(new Dimension(750, 500));
        setVisible(true);
        pack();
    }
}

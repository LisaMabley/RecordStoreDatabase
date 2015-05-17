package com.Lisa;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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

        final SellGUI sellGUI = new SellGUI();
        final AcquireGUI acquireGUI = new AcquireGUI();
        final AccountsGUI accountsGUI = new AccountsGUI();
        final ConsignorsGUI consignorsGUI = new ConsignorsGUI();
        final InventoryGUI inventoryGUI = new InventoryGUI();

        tabbedPane.add("Sell Album", sellGUI.getPanel());
        tabbedPane.add("Acquire Album", acquireGUI.getPanel());
        tabbedPane.add("Consignor Accounts", accountsGUI.getPanel());
        tabbedPane.add("Manage Consignors", consignorsGUI.getPanel());
        tabbedPane.add("Manage Inventory", inventoryGUI.getPanel());

        // Adapted from: http://stackoverflow.com/questions/6799731/jtabbedpane-changelistener
        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                switch (tabbedPane.getSelectedIndex()) {
                    case 0:
                        sellGUI.resetSearchFields();
                        break;
                    case 1:
                        acquireGUI.resetBuyAlbumFields();
                        break;
                    case 2:
                        accountsGUI.reset();
                        break;
                    case 3:
                        consignorsGUI.reset();
                        break;
                    case 4:
                        inventoryGUI.reset();
                        break;
                }
            }
        });

        setSize(new Dimension(750, 700));
        setVisible(true);
        pack();
    }
}

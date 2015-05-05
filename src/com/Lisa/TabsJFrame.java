package com.Lisa;

import javax.swing.*;
import java.awt.*;

/**
 * Created by lisa on 5/2/15.
 */
public class TabsJFrame extends JFrame {
    private JTabbedPane tabbedPaneGUI;
    private SellGUI sellgui = new SellGUI();

    TabsJFrame() {
        setContentPane(tabbedPaneGUI);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(new Dimension(550, 550));

        tabbedPaneGUI.add("Sell Album", sellgui);
    }
}

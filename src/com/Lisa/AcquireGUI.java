package com.Lisa;

import javax.swing.*;

/**
 * Created by lisa on 5/7/15.
 */
public class AcquireGUI {
    private JPanel acquireAlbumPanel;
    private JTextField artistTextField;
    private JTextField titleTextField;
    private JComboBox consignorCombobox;
    private JComboBox sizeCombobox;
    private JComboBox conditionCombobox;
    private JTextField priceTextField;
    private JButton checkInventoryButton;
    private JTextArea inventoryTextArea;
    private JButton buyAlbumButton;
    private JButton declineButton;

    public JPanel getPanel() {
        return acquireAlbumPanel;
    }

}

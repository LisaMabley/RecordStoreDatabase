package com.Lisa;

import javax.swing.*;

/**
 * Created by lisa on 5/6/15.
 */
public class ConsignorsGUI extends JPanel {

    private JPanel consignorsPanel;
    private JButton payOtherAmountButton;
    private JButton payInFullButton;
    private JList consignorJList;
    private JSpinner payAmountSpinner;
    private JTextArea consignorAlbumsTextArea;
    private JTextArea textArea1;
    private JComboBox searchPurposeComboBox;

    // Constructor
    public ConsignorsGUI() {

        // Set options for purpose
        final String[] purposeOptions = {"Find Consignors Owed More Than $10", "Find Consignors with Unsold Albums"};

        searchPurposeComboBox.addItem(purposeOptions[0]);
        searchPurposeComboBox.addItem(purposeOptions[1]);
        searchPurposeComboBox.setSelectedItem(purposeOptions[0]);
    }

    public JPanel getPanel() {
        return consignorsPanel;
    }
}

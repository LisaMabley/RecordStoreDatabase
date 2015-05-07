package com.Lisa;

import javax.swing.*;

/**
 * Created by lisa on 5/6/15.
 */
public class AccountsGUI {
    private JPanel accountPanel;
    private JList consignorsAlbumsJList;
    private JLabel amountOwedLabel;
    private JTextArea recentPaymentsTextArea;
    private JTextField consignorNameTextField;
    private JTextField consignorEmailTextField;
    private JTextField consignorPhoneTextField;
    private JButton addNewConsignorButton;
    private JButton editSelectedConsignorButton;
    private JComboBox consignorNamesComboBox;
    private JButton findAlbumsButton;
    private JComboBox albumStatusComboBox;

    public JPanel getPanel() {
        return accountPanel;
    }
}

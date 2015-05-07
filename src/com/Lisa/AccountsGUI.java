package com.Lisa;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

/**
 * Created by lisa on 5/6/15.
 */
public class AccountsGUI {
    private JPanel accountPanel;
    private JList<Album> consignorsAlbumsJList;
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

    protected static DefaultComboBoxModel<Consignor> consignorComboBoxModel = new DefaultComboBoxModel<Consignor>();

    public AccountsGUI() {
        // CONSIGNOR ACCOUNTS
        // Set options for album status comboBox
        final String store = "In Store";
        final String bargain = "In Bargain Bin";
        final String sold = "Sold";
        final String donated = "Donated";

        albumStatusComboBox.addItem(store);
        albumStatusComboBox.addItem(bargain);
        albumStatusComboBox.addItem(sold);
        albumStatusComboBox.addItem(donated);
        albumStatusComboBox.setSelectedItem(store);

        // Set model for consignor combobox
        consignorNamesComboBox.setModel(consignorComboBoxModel);

        // Populate model
        final ArrayList<Consignor> consignorArrayList = RecordStoreController.requestConsignors();
        for (Consignor consignor : consignorArrayList) {
            AccountsGUI.consignorComboBoxModel.addElement(consignor);
        }

        // Set default
        consignorNamesComboBox.setSelectedItem(null);

        consignorNamesComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Consignor selectedConsignor = (Consignor) consignorNamesComboBox.getSelectedItem();
                    consignorNameTextField.setText(selectedConsignor.name);
                    consignorEmailTextField.setText(selectedConsignor.email);
                    consignorPhoneTextField.setText(selectedConsignor.phoneNumber);

                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    consignorNameTextField.setText("");
                    consignorEmailTextField.setText("");
                    consignorPhoneTextField.setText("");
                }
            }
        });

        addNewConsignorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            // TODO Input validation!!!
            String name = consignorNameTextField.getText();
            boolean duplicate = false;

//            for (Consignor consignor : consignorArrayList) {
//                if (consignor.name.equalsIgnoreCase(name)) {
//                    // Popup: name is already in database! Continue, or cancel
//
//                    String[] dialogOptions = {"Cancel", "Continue"};
//                    int n = JOptionPane.showOptionDialog(frame,
//                            "Would you like green eggs and ham?",
//                            "A Silly Question",
//                            JOptionPane.YES_NO_OPTION,
//                            JOptionPane.WARNING_MESSAGE,
//                            null,     //do not use a custom Icon
//                            dialogOptions,  //the titles of buttons
//                            dialogOptions[1]); //default button title
//
//                    duplicate = true;
//                }
//            }

            if (!duplicate) {
                String email = consignorEmailTextField.getText();
                String phone = consignorPhoneTextField.getText();
                RecordStoreController.requestAddConsignor(name, email, phone);
            }
            }
        });
    }

    public JPanel getPanel() {
        return accountPanel;
    }
}

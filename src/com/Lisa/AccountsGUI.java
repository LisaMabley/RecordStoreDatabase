package com.Lisa;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by lisa on 5/6/15.
 */

public class AccountsGUI extends JPanel {
    private JPanel accountPanel;
    private JList<ConsignorAlbum> consignorsAlbumsJList;
    private JLabel amountOwedLabel;
    private JTextField consignorNameTextField;
    private JTextField consignorEmailTextField;
    private JTextField consignorPhoneTextField;
    private JButton addNewConsignorButton;
    private JButton editSelectedConsignorButton;
    private JComboBox<Consignor> consignorNamesComboBox;
    private JButton deleteSelectedConsignorButton;
    private JTextArea addEditAndDeleteTextArea;
    private JList<Payment> paymentsJList;
    private JButton returnSelectedAlbumToButton;
    private JButton payFullAmountOwedButton;

    protected static ArrayList<Consignor> consignorArrayList = new ArrayList<Consignor>();
    protected static DefaultComboBoxModel<Consignor> consignorComboBoxModel = new DefaultComboBoxModel<Consignor>();
    private static DefaultListModel<ConsignorAlbum> consignorAlbumListModel = new DefaultListModel<ConsignorAlbum>();

    // Constructor
    public AccountsGUI() {

        // Create list models for consignor album list
        consignorsAlbumsJList.setModel(consignorAlbumListModel);
        consignorsAlbumsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Set model for consignor combobox
        consignorNamesComboBox.setModel(consignorComboBoxModel);
        refreshConsignorList();

        // Set default selection
        consignorNamesComboBox.setSelectedItem(consignorComboBoxModel.getElementAt(0));

        consignorNamesComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Consignor consignorSelected = (Consignor) consignorNamesComboBox.getSelectedItem();
                if (e.getStateChange() == ItemEvent.SELECTED && !consignorSelected.name.equalsIgnoreCase("none selected")) {
                    Consignor selectedConsignor = (Consignor) consignorNamesComboBox.getSelectedItem();
                    consignorNameTextField.setText(selectedConsignor.name);
                    consignorEmailTextField.setText(selectedConsignor.email);
                    consignorPhoneTextField.setText(selectedConsignor.phoneNumber);
                    getConsignorAlbumDetails(selectedConsignor.consignorId);
                    amountOwedLabel.setText("Amount owed: $" + selectedConsignor.amountOwed);

                } else {
                    // No consignor selected
                    consignorNameTextField.setText("");
                    consignorEmailTextField.setText("");
                    consignorPhoneTextField.setText("");
                    consignorAlbumListModel.removeAllElements();
                }
            }
        });

        addNewConsignorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameInput = consignorNameTextField.getText();
                boolean duplicate = false;

                for (Consignor consignor : consignorArrayList) {
                    if (consignor.name.equalsIgnoreCase(nameInput)) {
                        // If name entered matches an existing entry in consignor database
                        // Display warning dialog box

                        String[] dialogOptions = {"Continue", "Cancel"};
                        int n = JOptionPane.showOptionDialog(null,
                                "That name is already in our consignor database, \nwith the following contact info: \n" + consignor.getDetails() +
                                        "\nWould you like to continue to add a new \nconsignor account, or cancel and edit the existing account?",
                                "Posible Duplicate",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE,
                                null,
                                dialogOptions,
                                dialogOptions[1]);

                        if (n == 1) {
                            duplicate = true;
                        }
                    }
                }

                if (!duplicate) {
                    String email = consignorEmailTextField.getText();
                    String phone = consignorPhoneTextField.getText();
                    RecordStoreController.requestAddConsignor(nameInput, email, phone);
                    refreshConsignorList();
                }
            }
        });

        editSelectedConsignorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        deleteSelectedConsignorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Consignor consignorToRemove = (Consignor) consignorNamesComboBox.getSelectedItem();
                RecordStoreController.requestRemoveConsignor(consignorToRemove);
                refreshConsignorList();
            }
        });

        returnSelectedAlbumToButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!consignorsAlbumsJList.isSelectionEmpty()) {

                    Album selectedAlbum = consignorsAlbumsJList.getSelectedValue();
                    if (selectedAlbum.status == Album.STATUS_STORE || selectedAlbum.status == Album.STATUS_BARGAIN_BIN) {
                        RecordStoreController.requestUpdateAlbumStatus(selectedAlbum, Album.STATUS_RETURNED_TO_CONSIGNOR);
                    }
                }
            }
        });

        // TODO implement edit consignor info button functionality
        // TODO Display payments in JList
    }

    private void refreshConsignorList() {
        // Populate comboBox model
        consignorArrayList = RecordStoreController.requestConsignors();
        consignorComboBoxModel.removeAllElements();
        Consignor nullValueObject = new Consignor(-1, "none selected", "", "", (float)0.0);
        consignorComboBoxModel.addElement(nullValueObject);

        for (Consignor consignor : consignorArrayList) {
            consignorComboBoxModel.addElement(consignor);
        }
    }

    private void getConsignorAlbumDetails(int consignorId) {
        // TODO figure out why the scrollpane does not scroll vertically to display all results

        // Populate JList model
        consignorAlbumListModel.removeAllElements();
        ArrayList<ConsignorAlbum> consignorsAlbums = RecordStoreController.requestAllConsignorsAlbums(consignorId);

        for (ConsignorAlbum album : consignorsAlbums) {
            consignorAlbumListModel.addElement(album);
        }
    }

    public JPanel getPanel() {
        return accountPanel;
    }
}

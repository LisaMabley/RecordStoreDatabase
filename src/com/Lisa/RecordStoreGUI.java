package com.Lisa;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by lisa on 4/26/15.
 */

public class RecordStoreGUI extends JFrame {
    private JButton buyAlbumButton;
    private JButton checkInventoryButton;
    private JTextField artistTextField;
    private JTextField titleTextField;
    private JComboBox consignorCombobox;
    private JComboBox sizeCombobox;
    private JComboBox conditionCombobox;
    private JTextField priceTextField;
    private JPanel buyPanel;
    private JTabbedPane guiTabbedPane;
    private JButton quitButton;
    private JTextArea inventoryTextArea;
    private JButton declineButton;
    private JButton searchButton;
    private JButton sellAlbumButton;
    private JButton cancelButton;
    private JList<Album> albumSearchResultsJList;
    private JComboBox searchByCombobox;
    private JTextField searchTextField;
    private JTextArea searchResultTextArea;

    DefaultListModel<Album> searchResultsListModel = new DefaultListModel<Album>();
    DefaultComboBoxModel<Consignor> consignorComboBoxModel = new DefaultComboBoxModel<Consignor>();

    public static final int ARTIST_FIELD = 1;
    public static final int TITLE_FIELD = 2;

    RecordStoreGUI() {
        setContentPane(guiTabbedPane);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(new Dimension(550, 550));

        // SELL/REMOVE ALBUM PANEL
        // Set options for search by comboBox
        final String art = "Artist Name";
        final String tit = "Album Title";
        searchByCombobox.addItem(art);
        searchByCombobox.addItem(tit);
        searchByCombobox.setSelectedItem(tit);

        // Create list models for search
        searchResultsListModel = new DefaultListModel<Album>();
        albumSearchResultsJList.setModel(searchResultsListModel);
        albumSearchResultsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fieldToSearch = (searchByCombobox.getSelectedItem().equals(art)) ? ARTIST_FIELD : TITLE_FIELD;
                ArrayList<Album> searchResult = RecordStoreController.requestSearchInventory(searchTextField.getText(), fieldToSearch);

                if (searchResult.isEmpty()) {
                    searchResultTextArea.setText("No matching albums in inventory.");

                } else {
                    for (Album album : searchResult) {
                        RecordStoreGUI.this.searchResultsListModel.addElement(album);
                    }
                }
            }
        });

        albumSearchResultsJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Album selectedAlbum = albumSearchResultsJList.getSelectedValue();
                searchResultTextArea.setText(selectedAlbum.getDetailString());
            }
        });

        searchTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                resetSearchFields();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetSearchFields();
            }
        });

        sellAlbumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Album selectedAlbum = albumSearchResultsJList.getSelectedValue();
                RecordStoreController.requestUpdateAlbumStatus(selectedAlbum, Album.SOLD);
            }
        });

        // BUY/ADD ALBUM PANEL
        // TODO move each panel into its own class & file
        // Set options for size comboBox
        final String seven = "7 Inch";
        final String ten = "10 Inch";
        final String twelve = "12 Inch";
        final String lp = "LP";
        final String lpSet = "2-LP Set";
        final String boxSet = "Boxed Set";

        sizeCombobox.addItem(seven);
        sizeCombobox.addItem(ten);
        sizeCombobox.addItem(twelve);
        sizeCombobox.addItem(lp);
        sizeCombobox.addItem(lpSet);
        sizeCombobox.addItem(boxSet);
        sizeCombobox.setSelectedItem(null);

        // Set options for condition comboBox
        final String poor = "Poor";
        final String fair = "Fair";
        final String good = "Good";
        final String veryGood = "Very Good";
        final String nearMint = "Near Mint";
        final String mint = "Mint";

        conditionCombobox.addItem(poor);
        conditionCombobox.addItem(fair);
        conditionCombobox.addItem(good);
        conditionCombobox.addItem(veryGood);
        conditionCombobox.addItem(nearMint);
        conditionCombobox.addItem(mint);
        conditionCombobox.setSelectedItem(null);

        // Populate consignor comboBox
        consignorCombobox.setModel(consignorComboBoxModel);
        ArrayList<Consignor> consignorArrayList = DataModel.getConsignors();

        for (Consignor consignor : consignorArrayList) {
            consignorComboBoxModel.addElement(consignor);
        }
        consignorCombobox.setSelectedItem(null);

        checkInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: What if one of these fields is blank?
                String name = artistTextField.getText();
                String title = titleTextField.getText();

                StringBuilder stringBuilder = new StringBuilder();
                int numStoreCopies = RecordStoreController.requestInventoryCheck(name, title, Album.STORE);
                String inventorySearchResultString = "Copies in store: " + numStoreCopies + "\n";
                stringBuilder.append(inventorySearchResultString);

                int numBargainCopies = RecordStoreController.requestInventoryCheck(name, title, Album.BARGAIN_BIN);
                String bargainSearchResultString = "Copies in bargain bin: " + numBargainCopies + "\n";
                stringBuilder.append(bargainSearchResultString);

                int numSoldCopies = RecordStoreController.requestInventoryCheck(name, title, Album.SOLD);
                String soldCopiesSearchResultString = "Copies sold in past 60 days: " + numSoldCopies + "\n";
                stringBuilder.append(soldCopiesSearchResultString);

                int numDonatedCopies = RecordStoreController.requestInventoryCheck(name, title, Album.DONATED);
                String donatedCopiesSearchResultString = "Copies donated in past 60 days: " + numDonatedCopies + "\n";
                stringBuilder.append(donatedCopiesSearchResultString);

                inventoryTextArea.setText(stringBuilder.toString());
            }
        });

        declineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
            }
        });

        buyAlbumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // TODO: Input validation. Any prohibited characters?
                String name = artistTextField.getText();
                String title = titleTextField.getText();

                // TODO Why doesn't this recognize the consignor as a Consignor object?
//                Consignor consignor = consignorComboBoxModel.getSelectedItem();
//                int consignorId = consignor.getId();
                int consignorId = 3;

                int size;
                String sizeString = sizeCombobox.getSelectedItem().toString();
                if (sizeString.equalsIgnoreCase(seven)) {
                    size = Album.SEVEN_INCH;
                } else if (sizeString.equalsIgnoreCase(ten)) {
                    size = Album.TEN_INCH;
                } else if (sizeString.equalsIgnoreCase(twelve)) {
                    size = Album.TWELVE_INCH;
                } else if (sizeString.equalsIgnoreCase(lp)) {
                    size = Album.LP;
                } else if (sizeString.equalsIgnoreCase(lpSet)) {
                    size = Album.TWO_LP_SET;
                } else if (sizeString.equalsIgnoreCase(boxSet)) {
                    size = Album.BOX_SET;
                } else {
                    size = Album.LP;
                }

                int condition;
                if (sizeString.equalsIgnoreCase(poor)) {
                    condition = Album.POOR;
                } else if (sizeString.equalsIgnoreCase(fair)) {
                    condition = Album.FAIR;
                } else if (sizeString.equalsIgnoreCase(good)) {
                    condition = Album.GOOD;
                } else if (sizeString.equalsIgnoreCase(veryGood)) {
                    condition = Album.VERY_GOOD;
                } else if (sizeString.equalsIgnoreCase(nearMint)) {
                    condition = Album.NEAR_MINT;
                } else if (sizeString.equalsIgnoreCase(mint)) {
                    condition = Album.MINT;
                } else {
                    condition = Album.GOOD;
                }

                // TODO: Input validation
                float price = Float.parseFloat(priceTextField.getText());

                // TODO: Null values allowed? Or are all fields required?
                Album newAlbum = new Album(consignorId, name, title, size, condition, price);
                RecordStoreController.requestAddAlbum(newAlbum);
                resetFields();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataModel.closeDbConnections();
                System.exit(0);
            }
        });
    }

    private void resetFields() {
        artistTextField.setText("");
        titleTextField.setText("");
        consignorCombobox.setSelectedItem(null);
        sizeCombobox.setSelectedItem(null);
        conditionCombobox.setSelectedItem(null);
        priceTextField.setText("");
        inventoryTextArea.setText("");
    }

    private void resetSearchFields() {
        searchTextField.setText("");
        searchResultTextArea.setText("");

        if (!searchResultsListModel.isEmpty() && searchResultsListModel != null) {
            // TODO Test and fix bugs that cause crashes here
            searchResultsListModel.removeAllElements();
        }
    }
}

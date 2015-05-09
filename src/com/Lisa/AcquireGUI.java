package com.Lisa;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by lisa on 5/7/15.
 */

public class AcquireGUI extends JPanel {
    private JPanel acquireAlbumPanel;
    private JTextField artistTextField;
    private JTextField titleTextField;
    private JComboBox<Consignor> consignorCombobox;
    private JComboBox<String> sizeCombobox;
    private JComboBox<String> conditionCombobox;
    private JTextField priceTextField;
    private JButton checkInventoryButton;
    private JTextArea inventoryTextArea;
    private JButton buyAlbumButton;
    private JButton declineButton;

    public AcquireGUI() {
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
        consignorCombobox.setModel(AccountsGUI.consignorComboBoxModel);
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
                resetBuyAlbumFields();
            }
        });

        buyAlbumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // TODO: Input validation. Any prohibited characters?
                String name = artistTextField.getText();
                String title = titleTextField.getText();

                Consignor consignor = (Consignor) AccountsGUI.consignorComboBoxModel.getSelectedItem();
                int consignorId = consignor.getId();

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
                resetBuyAlbumFields();
            }
        });
    }

    private void resetBuyAlbumFields() {
        artistTextField.setText("");
        titleTextField.setText("");
        consignorCombobox.setSelectedItem(null);
        sizeCombobox.setSelectedItem(null);
        conditionCombobox.setSelectedItem(null);
        priceTextField.setText("");
        inventoryTextArea.setText("");
    }

    public JPanel getPanel() {
        return acquireAlbumPanel;
    }

}

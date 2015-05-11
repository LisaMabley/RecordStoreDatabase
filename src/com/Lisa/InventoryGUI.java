package com.Lisa;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by lisa on 5/6/15.
 */

public class InventoryGUI extends JPanel {
    private JList<Album> albumAgingJList;
    private JButton findAlbumsOver37Button;
    private JButton markAllButton;
    private JButton markSelectedButton;
    private JButton findAlbumsOver13Button;
    private JTextArea albumAgingTextArea;
    private JPanel inventoryPanel;
    private JScrollPane inventoryScrollpane;
    private JTextArea searchStoreInventoryForTextArea;
    private JTextArea searchBargainBinForTextArea;

    DefaultListModel<Album> albumAgingListModel = new DefaultListModel<Album>();
    ArrayList<Album> albumAgingArrayList = new ArrayList<Album>();

    // Indicates which album aging period is
    // being requested in Manage Inventory panel
    public static final int THIRTY_SEVEN_DAYS = 1;
    public static final int THIRTEEN_MONTHS = 2;

    public InventoryGUI() {

        // Create list models for inventory search
        albumAgingJList.setModel(albumAgingListModel);
        albumAgingJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        findAlbumsOver37Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Change 37 days to 42 (since we're only notifying once a week, not every day)
                InventoryGUI.this.albumAgingListModel.removeAllElements();
                albumAgingArrayList.clear();
                albumAgingArrayList = RecordStoreController.requestAlbumsOfAge(THIRTY_SEVEN_DAYS);

                if (albumAgingArrayList.isEmpty()) {
                    albumAgingTextArea.setText("No albums found.");

                } else {
                    albumAgingTextArea.setText("Which albums would you like to move to the bargain bin?");
                    for (Album album : albumAgingArrayList) {
                        InventoryGUI.this.albumAgingListModel.addElement(album);
                    }
                }
            }
        });

        findAlbumsOver13Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InventoryGUI.this.albumAgingListModel.removeAllElements();
                albumAgingArrayList.clear();
                albumAgingArrayList = RecordStoreController.requestAlbumsOfAge(THIRTEEN_MONTHS);

                if (albumAgingArrayList.isEmpty()) {
                    albumAgingTextArea.setText("No albums found.");

                } else {
                    albumAgingTextArea.setText("Which albums would you like to donate?");
                    for (Album album : albumAgingArrayList) {
                        InventoryGUI.this.albumAgingListModel.addElement(album);
                    }
                }
            }
        });

        markSelectedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Album selectedAlbum = (Album) albumAgingJList.getSelectedValue();
                albumAgingListModel.removeElement(selectedAlbum);

                if (selectedAlbum.status == Album.STATUS_STORE) {
                    RecordStoreController.requestUpdateAlbumStatus(selectedAlbum, Album.STATUS_BARGAIN_BIN);
//                    RecordStoreController.requestUpdateAlbumPrice(selectedAlbum, 1);
//                    selectedAlbum.moveToBargainBin();

                } else if (selectedAlbum.status == Album.STATUS_BARGAIN_BIN) {
                    RecordStoreController.requestUpdateAlbumStatus(selectedAlbum, Album.STATUS_DONATED);
//                    RecordStoreController.requestUpdateAlbumPrice(selectedAlbum, 0);
//                    selectedAlbum.setDonated();
                }
            }
        });

        markAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (albumAgingTextArea.getText().contains("bargain bin")) {
                    for (Album album : albumAgingArrayList) {
//                        album.moveToBargainBin();
                        RecordStoreController.requestUpdateAlbumStatus(album, Album.STATUS_BARGAIN_BIN);
//                        RecordStoreController.requestUpdateAlbumPrice(album, 1);
                        albumAgingListModel.removeElement(album);
                    }

                } else if (albumAgingTextArea.getText().contains("donate")) {
                    for (Album album : albumAgingArrayList) {
//                        album.setDonated();
                        RecordStoreController.requestUpdateAlbumStatus(album, Album.STATUS_DONATED);
//                        RecordStoreController.requestUpdateAlbumPrice(album, 0);
                        albumAgingListModel.removeElement(album);
                    }
                }
            }
        });
    }

    public void reset() {
        albumAgingListModel.removeAllElements();
        albumAgingTextArea.setText("");
    }

    public JPanel getPanel() {
        return inventoryPanel;
    }
}

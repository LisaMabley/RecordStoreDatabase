package com.Lisa;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusAdapter;
import java.util.ArrayList;

/**
 * Created by lisa on 5/7/15.
 */

public class SellGUI extends JPanel {
    private JPanel sellPanel;
    private JButton quitButton;
    private JButton sellAlbumButton;
    private JButton cancelButton;
    private JList<Album> albumSearchResultsJList;
    private JTextArea searchResultTextArea;
    private JComboBox<String> searchByCombobox;
    private JTextField searchTextField;
    private JButton searchButton;
    private JTextArea searchStoreInventoryByTextArea;

    private static DefaultListModel<Album> searchResultsListModel = new DefaultListModel<Album>();

    // Indicates whether searching artist or title
    public static final int ARTIST_FIELD = 1;
    public static final int TITLE_FIELD = 2;

    // Constructor
    public SellGUI() {
        // Set options for search by comboBox
        final String art = "Artist Name";
        final String tit = "Album Title";
        searchByCombobox.addItem(art);
        searchByCombobox.addItem(tit);
        searchByCombobox.setSelectedItem(tit);

        // Create list models for search
        albumSearchResultsJList.setModel(searchResultsListModel);
        albumSearchResultsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO ignore case
                SellGUI.this.searchResultsListModel.removeAllElements();
                int fieldToSearch = (searchByCombobox.getSelectedItem().equals(art)) ? ARTIST_FIELD : TITLE_FIELD;
                ArrayList<Album> searchResult = RecordStoreController.requestSearchInventory(searchTextField.getText(), fieldToSearch);

                if (searchResult.isEmpty()) {
                    searchResultTextArea.setText("No matching albums in inventory.");

                } else {
                    for (Album album : searchResult) {
                        SellGUI.this.searchResultsListModel.addElement(album);
                    }
                }
            }
        });

        albumSearchResultsJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!albumSearchResultsJList.isSelectionEmpty()) {
                    Album selectedAlbum = albumSearchResultsJList.getSelectedValue();
                    searchResultTextArea.setText(selectedAlbum.getAlbumDetailsForBuyers());
                }
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
                // TODO do nothing if no album is selected
                searchResultTextArea.setText("");
                Album selectedAlbum = albumSearchResultsJList.getSelectedValue();
                RecordStoreController.requestUpdateAlbumStatus(selectedAlbum, Album.STATUS_SOLD);
                searchResultsListModel.removeElement(selectedAlbum);
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RecordStoreController.quitProgram();
            }
        });
    }

    public void resetSearchFields() {
        searchTextField.setText("");
        searchResultTextArea.setText("");

        if (!searchResultsListModel.isEmpty()) {
            searchResultsListModel.removeAllElements();
        }
    }

    public JPanel getPanel() {
        return sellPanel;
    }
}

package com.Lisa;

import java.util.ArrayList;

public class RecordStoreController {

    static DataModel model ;

    public static void main(String[] args) {

        model = new DataModel();
        RecordStoreGUI gui = new RecordStoreGUI();
    }

    public static int requestInventoryCheck(String artist, String title, int status) {
        return DataModel.getNumCopiesInInventory(artist, title, status);
    }

    public static void requestAddAlbum(Album album) {
        DataModel.addAlbum(album);
    }

    public static ArrayList<Album> requestSearchInventory(String searchString, int searchField) {
        ArrayList<Album> results = DataModel.searchInventoryForAlbums(searchString, searchField);
        return results;
    }

    public static void requestUpdateAlbumStatus(Album albumToUpdate, int newStatus) {
        if (newStatus == Album.SOLD) {
            albumToUpdate.setSoldDate();
        }
        DataModel.updateAlbumStatus(albumToUpdate, newStatus);
    }
}

package com.Lisa;

public class RecordStoreController {

    static DataModel model ;

    public static void main(String[] args) {

        model = new DataModel();
        RecordStoreGUI gui = new RecordStoreGUI();
    }

    public static int requestInventoryCheck(String artist, String title, int status) {
        return DataModel.checkInventoryForAlbum(artist, title, status);
    }

    public static void requestAddAlbum(Album album) {
        DataModel.addAlbum(album);
    }
}

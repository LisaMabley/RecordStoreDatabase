package com.Lisa;

public class RecordStoreController {

    static DataModel model ;

    public static void main(String[] args) {

        model = new DataModel();
        RecordStoreGUI gui = new RecordStoreGUI();
    }

    public static void requestAddAlbum(Album album) {
        DataModel.addAlbum(album);
    }
}

package com.Lisa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RecordStoreController {

    static DataModel model ;

    public static void main(String[] args) {

        model = new DataModel();
//        RecordStoreGUI gui = new RecordStoreGUI();
        TabsGUI tabs = new TabsGUI();
    }

    public static ArrayList<Consignor> requestConsignors() {
        return DataModel.getConsignors();
    }

    public static int requestInventoryCheck(String artist, String title, int status) {
        return DataModel.getNumCopiesInInventory(artist, title, status);
    }

    public static void requestAddAlbum(Album album) {
        DataModel.addAlbum(album);
    }

    public static ArrayList<Album> requestSearchInventory(String searchString, int searchField) {
        return DataModel.searchInventoryForAlbums(searchString, searchField);
    }

    public static void requestAddConsignor(String name, String email, String phone) {
        DataModel.addConsignor(name, email, phone);
    }

    public static void requestUpdateAlbumStatus(Album albumToUpdate, int newStatus) {
        if (newStatus == Album.SOLD) {
            System.out.println("New status: " + newStatus);
            albumToUpdate.setSoldDate();
        }
        DataModel.updateAlbumStatus(albumToUpdate, newStatus);
    }

    public static void requestUpdateAlbumPrice(Album albumToUpdate, int newPrice) {
        DataModel.updateAlbumPrice(albumToUpdate, newPrice);
    }

    public static ArrayList<Album> requestAlbumsOfAge(int ageToFind) {

        int findAlbumsOfStatus = 0;
        // First read about Calendar class at URL below
        // http://stackoverflow.com/questions/6439946/java-date-problems-finding-the-date-x-days-ago
        Calendar calendar = Calendar.getInstance();
        if (ageToFind == RecordStoreGUI.THIRTY_SEVEN_DAYS) {
            calendar.add(Calendar.DAY_OF_MONTH, -37);
            findAlbumsOfStatus = Album.STORE;
        } else if (ageToFind == RecordStoreGUI.THIRTEEN_MONTHS) {
            calendar.add(Calendar.YEAR, -1);
            findAlbumsOfStatus = Album.BARGAIN_BIN;
        }
        Date utilDate = calendar.getTime();
        java.sql.Date findAlbumsConsignedBefore = new java.sql.Date(utilDate.getTime());

        return DataModel.findAlbumsOfAge(findAlbumsConsignedBefore, findAlbumsOfStatus);
    }
}

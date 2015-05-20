package com.Lisa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.NumberFormat;

public class RecordStoreController {

    static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");
    static NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
    static DataModel model ;

    public static void main(String[] args) {

        model = new DataModel();
        TabsGUI tabs = new TabsGUI();
    }

    // ALBUM METHODS

    public static int requestInventoryCheck(String artist, String title, int status) {
        return DataModel.getNumCopiesInInventory(artist, title, status);
    }

    public static void requestAddAlbum(Album album) {
        DataModel.addAlbum(album);
    }

    public static ArrayList<Album> requestSearchInventory(String searchString, int searchField) {
        return DataModel.searchInventoryForAlbums(searchString, searchField);
    }

    public static void requestUpdateAlbumStatus(Album albumToUpdate, int newStatus) {
        if (newStatus == Album.STATUS_SOLD) {
            albumToUpdate.setSoldDate();
        }
        DataModel.updateAlbumStatus(albumToUpdate, newStatus, albumToUpdate.dateSold);
    }

    public static ArrayList<Album> requestAlbumsOfAge(int ageToFind) {

        int findAlbumsOfStatus = 0;
        // First read about Calendar class at URL below
        // http://stackoverflow.com/questions/6439946/java-date-problems-finding-the-date-x-days-ago
        Calendar calendar = Calendar.getInstance();
        if (ageToFind == InventoryGUI.THIRTY_SEVEN_DAYS) {
            calendar.add(Calendar.DAY_OF_MONTH, -37);
            findAlbumsOfStatus = Album.STATUS_STORE;
        } else if (ageToFind == InventoryGUI.THIRTEEN_MONTHS) {
            calendar.add(Calendar.YEAR, -1);
            findAlbumsOfStatus = Album.STATUS_BARGAIN_BIN;
        }
        Date utilDate = calendar.getTime();
        java.sql.Date findAlbumsConsignedBefore = new java.sql.Date(utilDate.getTime());

        return DataModel.findAlbumsOfAge(findAlbumsConsignedBefore, findAlbumsOfStatus);
    }

    public static ArrayList<ConsignorAlbum> requestAllConsignorsAlbums(int consignorId) {
        return DataModel.findAllAlbumsFromConsignor(consignorId);
    }

    public static ArrayList<ConsignorAlbum> requestConsignorsUnsoldAlbums(int consignorId) {
        return DataModel.findUnsoldAlbumsFromConsignor(consignorId);
    }

    // CONSIGNOR METHODS

    public static ArrayList<Consignor> requestAllConsignors() {
        return DataModel.getAllConsignors();
    }

    public static ArrayList<Consignor> requestConsignorsToNotify() {
        return DataModel.getConsignorsToNotify();
    }

    public static ArrayList<Consignor> requestConsignorsToPay() {
        return DataModel.getConsignorsToPay();
    }

    public static void requestAddConsignor(String name, String email, String phone) {
        DataModel.addConsignor(name, email, phone);
    }

    public static void requestRemoveConsignor(Consignor consignorToRemove) {
        DataModel.removeConsignor(consignorToRemove);
    }

    public static ArrayList<Payment> requestAllConsignorsPayments(int consignorId) {
        return DataModel.findAllPaymentsToConsignor(consignorId);
    }

    public static void requestPayConsignorInFull(Consignor consignorToPay) {
        Payment newPayment = new Payment(consignorToPay.consignorId, consignorToPay.amountOwed);
        DataModel.recordPayment(newPayment);
    }

    public static void quitProgram() {
        DataModel.closeDbConnections();
        System.exit(0);
    }
}

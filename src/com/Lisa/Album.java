package com.Lisa;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lisa on 4/21/15.
 */

public class Album {

    protected int albumId;
    protected int consignorId;
    protected String artist;
    protected String title;
    protected int size;
    protected int condition;
    protected float price;
    protected java.sql.Date dateConsigned;
    protected int status;
    protected java.sql.Date dateSold;

    // Size constants
    protected static final int SEVEN_INCH = 1;
    protected static final int TEN_INCH = 2;
    protected static final int TWELVE_INCH = 3;
    protected static final int LP = 4;
    protected static final int TWO_LP_SET = 5;
    protected static final int BOX_SET = 6;

    // Condition constants
    protected static final int POOR = 1;
    protected static final int FAIR = 2;
    protected static final int GOOD = 3;
    protected static final int VERY_GOOD = 4;
    protected static final int NEAR_MINT = 5;
    protected static final int MINT = 6;

    // Status constants
    protected static final int STORE = 1;
    protected static final int BARGAIN_BIN = 2;
    protected static final int SOLD = 3;
    protected static final int DONATED = 4;
    protected static final int RETURNED_TO_CONSIGNOR = 5;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");

    Album(int consignorId, String artistName, String albumTitle, int size, int condition, float price) {
        this.consignorId = consignorId;
        this.artist = artistName;
        this.title = albumTitle;
        this.size = size;
        this.condition = condition;
        this.price = price;
        java.util.Date utilDate = new java.util.Date();
        this.dateConsigned = new java.sql.Date(utilDate.getTime());
        this.status = STORE;
    }

    Album(int id, int consignor, String artistName, String albumTitle, int size, int condition, float price, int status) {
        this.albumId = id;
        this.consignorId = consignor;
        this.artist = artistName;
        this.title = albumTitle;
        this.size = size;
        this.condition = condition;
        this.price = price;
        java.util.Date utilDate = new java.util.Date();
        this.dateConsigned = new java.sql.Date(utilDate.getTime());
        this.status = status;
    }

    Album(int id, int consignor, String artistName, String albumTitle, int size, int condition, float price, java.util.Date dateConsigned, int status, java.util.Date dateSold) {
        this.albumId = id;
        this.consignorId = consignor;
        this.artist = artistName;
        this.title = albumTitle;
        this.size = size;
        this.condition = condition;
        this.price = price;
        this.dateConsigned = (java.sql.Date) dateConsigned;
        this.status = status;
        this.dateSold = (java.sql.Date) dateSold;
    }

    public void setSoldDate() {
        java.util.Date utilDate = new java.util.Date();
        this.dateSold = new java.sql.Date(utilDate.getTime());
        System.out.println("Sold date: " + this.dateSold);
    }

    public void moveToBargainBin() {
        this.status = BARGAIN_BIN;
        this.price = 1;
    }

    public void setDonated() {
        this.status = DONATED;
        this.price = 0;
    }

//    public void setReturnedToConsignor() {
//        this.status = RETURNED_TO_CONSIGNOR;
//        this.price = 0;
//    }

    private String getDateStatusWillChange() {

        String dateStatusWillChange = "";
        Calendar calendar = Calendar.getInstance();
        java.util.Date utilDate = this.dateConsigned;
        System.out.println(utilDate);
        calendar.setTime(utilDate);
        dateFormat.setCalendar(calendar);

        switch (this.status) {
            case 1:
                calendar.add(Calendar.DAY_OF_MONTH, 37);
                return dateFormat.format(calendar.getTime());

            case 2:
                calendar.add(Calendar.YEAR, 1);
                return dateFormat.format(calendar.getTime());

            default:
                return this.toString();
        }
    }

    public String getDetailsForBuyers() {
        StringBuilder stringBuilder = new StringBuilder();
        String titleDetail = "Title: " + this.title + "\n";
        stringBuilder.append(titleDetail);

        String artistDetail = "Artist: " + this.artist + "\n";
        stringBuilder.append(artistDetail);

        String sizeDetail = "Size: " + this.getSizeString() + "\n";
        stringBuilder.append(sizeDetail);

        String conditionDetail = "Condition: " + this.getConditionString() + "\n";
        stringBuilder.append(conditionDetail);

        String priceDetail = "Price: $" + this.price + "\n";
        stringBuilder.append(priceDetail);

        String statusDetail = "Location: " + this.getStatusString() + "\n";
        stringBuilder.append(statusDetail);

        return stringBuilder.toString();
    }

    public String getDetailsForConsignor() {
        switch (this.status) {
            case 1:
                return this.albumId + ". " + this.title + ", to Bargain Bin on " + this.getDateStatusWillChange();

            case 2:
                return this.albumId + ". " + this.title + ", will be donated on " + this.getDateStatusWillChange();

            case 3:
                return this.albumId + ". " + this.title + ", sold on " + dateFormat.format(this.dateSold);

            case 4:
                return this.albumId + ". " + this.title + ", donated to charity";

            case 5:
                return this.albumId + ". " + this.title + ", returned to you";

            default:
                return this.albumId + ". " + this.toString();
        }
    }

    @Override
    public String toString() {
        return this.title + " by " + this.artist;
    }

    // String getters for constants

    protected String getSizeString() {
        switch (this.size) {
            case SEVEN_INCH:
                return "Seven Inch";

            case TEN_INCH:
                return "Ten Inch";

            case TWELVE_INCH:
                return "Twelve Inch";

            case LP:
                return "LP";

            case TWO_LP_SET:
                return "Two LP Set";

            case BOX_SET:
                return "Boxed Set";

            default:
                return "";
        }
    }

    protected String getConditionString() {
        switch (this.condition) {
            case POOR:
                return "Poor";

            case FAIR:
                return "Fair";

            case GOOD:
                return "Good";

            case VERY_GOOD:
                return "Very Good";

            case NEAR_MINT:
                return "Near Mint";

            case MINT:
                return "Mint";

            default:
                return "";
        }
    }

    protected String getStatusString() {
        switch (this.status) {
            case STORE:
                return "Store";

            case BARGAIN_BIN:
                return "Bargain Bin";

            case SOLD:
                return "Sold";

            case DONATED:
                return "Donated";

            default:
                return "";
        }
    }
}

package com.Lisa;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    protected static final int SIZE_SEVEN_INCH = 1;
    protected static final int SIZE_TEN_INCH = 2;
    protected static final int SIZE_TWELVE_INCH = 3;
    protected static final int SIZE_LP = 4;
    protected static final int SIZE_TWO_LP_SET = 5;
    protected static final int SIZE_BOX_SET = 6;

    // Condition constants
    protected static final int CONDITION_POOR = 1;
    protected static final int CONDITION_FAIR = 2;
    protected static final int CONDITION_GOOD = 3;
    protected static final int CONDITION_VERY_GOOD = 4;
    protected static final int CONDITION_NEAR_MINT = 5;
    protected static final int CONDITION_MINT = 6;

    // Status constants
    protected static final int STATUS_STORE = 1;
    protected static final int STATUS_BARGAIN_BIN = 2;
    protected static final int STATUS_SOLD = 3;
    protected static final int STATUS_DONATED = 4;
    protected static final int STATUS_RETURNED_TO_CONSIGNOR = 5;

    protected SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");

    Album(int consignorId, String artistName, String albumTitle, int size, int condition, float price) {
        this.consignorId = consignorId;
        this.artist = artistName;
        this.title = albumTitle;
        this.size = size;
        this.condition = condition;
        this.price = price;
        java.util.Date utilDate = new java.util.Date();
        this.dateConsigned = new java.sql.Date(utilDate.getTime());
        this.status = STATUS_STORE;
    }

//    Album(int id, int consignor, String artistName, String albumTitle, int size, int condition, float price, int status) {
//        this.albumId = id;
//        this.consignorId = consignor;
//        this.artist = artistName;
//        this.title = albumTitle;
//        this.size = size;
//        this.condition = condition;
//        this.price = price;
//        java.util.Date utilDate = new java.util.Date();
//        this.dateConsigned = new java.sql.Date(utilDate.getTime());
//        this.status = status;
//    }

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

//    public void moveToBargainBin() {
//        this.status = STATUS_BARGAIN_BIN;
//        this.price = 1;
//    }
//
//    public void setDonated() {
//        this.status = STATUS_DONATED;
//        this.price = 0;
//    }

//    public void setReturnedToConsignor() {
//        this.status = STATUS_RETURNED_TO_CONSIGNOR;
//        this.price = 0;
//    }


//    public static java.sql.Date albumsConsignedBeforeThisDateGoToBargainBinToday() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DAY_OF_MONTH, -37);
//        return new java.sql.Date(calendar.getTime());
//    }

    @Override
    public String toString() {
        return this.title + " by " + this.artist;
    }

    public String getAlbumDetailsForBuyers() {
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

    // String getters for constants

    protected String getSizeString() {
        switch (this.size) {
            case SIZE_SEVEN_INCH:
                return "Seven Inch";

            case SIZE_TEN_INCH:
                return "Ten Inch";

            case SIZE_TWELVE_INCH:
                return "Twelve Inch";

            case SIZE_LP:
                return "LP";

            case SIZE_TWO_LP_SET:
                return "Two LP Set";

            case SIZE_BOX_SET:
                return "Boxed Set";

            default:
                return "";
        }
    }

    protected String getConditionString() {
        switch (this.condition) {
            case CONDITION_POOR:
                return "Poor";

            case CONDITION_FAIR:
                return "Fair";

            case CONDITION_GOOD:
                return "Good";

            case CONDITION_VERY_GOOD:
                return "Very Good";

            case CONDITION_NEAR_MINT:
                return "Near Mint";

            case CONDITION_MINT:
                return "Mint";

            default:
                return "";
        }
    }

    protected String getStatusString() {
        switch (this.status) {
            case STATUS_STORE:
                return "Store";

            case STATUS_BARGAIN_BIN:
                return "Bargain Bin";

            case STATUS_SOLD:
                return "Sold";

            case STATUS_DONATED:
                return "Donated";

            default:
                return "";
        }
    }

}

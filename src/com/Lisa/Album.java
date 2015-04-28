package com.Lisa;
import java.sql.Date;

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
    protected java.sql.Date dateOwnerNotifiedAlbumUnsold;

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

    Album(String artistName, String albumTitle, int consignorId, int size, int condition, float price) {
        this.artist = artistName;
        this.title = albumTitle;
        this.consignorId = consignorId;
        this.size = size;
        this.condition = condition;
        this.price = price;
        java.util.Date utilDate = new java.util.Date();
        this.dateConsigned = new java.sql.Date(utilDate.getTime());
        this.status = STORE;
    }

    @Override
    public String toString() {
        return this.title + " by " + this.artist;
    }

}

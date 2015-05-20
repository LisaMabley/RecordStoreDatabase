package com.Lisa;

import java.util.Calendar;

/**
 * Created by lisa on 5/10/15.
 */

public class ConsignorAlbum extends Album {

    public ConsignorAlbum(Album album) {
        super(album.albumId, album.consignorId, album.artist, album.title, album.size, album.condition, album.price, album.dateConsigned, album.status, album.dateSold);
    }

    @Override
    public String toString() {
        switch (this.status) {
            case 1:
                return this.title + ", to Bargain Bin on " + this.getDateStatusWillChange();

            case 2:
                return this.title + ", will be donated on " + this.getDateStatusWillChange();

            case 3:
                return this.title + ", sold on " + RecordStoreController.dateFormat.format(this.dateSold);

            case 4:
                return this.title + ", donated to charity";

            case 5:
                return this.title + ", returned to you";

            default:
                return this.title + " by " + this.artist;
        }
    }

    private String getDateStatusWillChange() {

        Calendar calendar = Calendar.getInstance();
        java.util.Date utilDate = this.dateConsigned;
        calendar.setTime(utilDate);
        RecordStoreController.dateFormat.setCalendar(calendar);

        switch (this.status) {
            case 1:
                calendar.add(Calendar.DAY_OF_MONTH, 37);
                return RecordStoreController.dateFormat.format(calendar.getTime());

            case 2:
                calendar.add(Calendar.YEAR, 1);
                return RecordStoreController.dateFormat.format(calendar.getTime());

            default:
                return "";
        }
    }
}

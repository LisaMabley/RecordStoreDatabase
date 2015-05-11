package com.Lisa;

import java.util.Date;

/**
 * Created by lisa on 4/21/15.
 */

public class Payment {
    protected int paymentId;
    protected int consignorId;
    protected java.sql.Date date;
    protected float amount;

    public Payment(int consignorId, float amount) {
        this.consignorId = consignorId;
        this.amount = amount;
        java.util.Date utilDate = new java.util.Date();
        this.date = new java.sql.Date(utilDate.getTime());
    }

    public Payment(int consignorId, java.sql.Date date, float amount) {
        this.consignorId = consignorId;
        this.amount = amount;
        this.date = date;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return this.amount + ", " + this.date;
    }
}

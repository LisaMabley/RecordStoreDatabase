package com.Lisa;

/**
 * Created by lisa on 4/21/15.
 */

public class Consignor {
    protected int consignorId;
    protected String name;
    protected String email;
    protected String phoneNumber;
    protected float amountOwed;

    protected static final int FINDGROUP_ALL_CONSIGNORS = 1;
    protected static final int FINDGROUP_UNSOLD_ALBUMS = 2;
    protected static final int FINDGROUP_OWED_TEN = 3;

    public Consignor(int id, String name, String phone, String email, Float amountOwed) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phone;
        this.consignorId = id;
        if (amountOwed == null) {
            this.amountOwed = 0;
        } else {
            this.amountOwed = amountOwed;
        }
    }

    public int getId() {
        return this.consignorId;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getDetails() {
        return this.name + ": " + this.email + ", " + this.phoneNumber;
    }
}

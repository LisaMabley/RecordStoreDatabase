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

    public Consignor(String name, String phone, String email, int id) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phone;
        this.consignorId = id;
    }

    public int getId() {
        return this.consignorId;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

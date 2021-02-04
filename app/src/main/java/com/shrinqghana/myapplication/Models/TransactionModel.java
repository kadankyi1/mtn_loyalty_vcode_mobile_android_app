package com.shrinqghana.myapplication.Models;

public class TransactionModel {

    private static final long serialVersionUID = 1L;
    private long rowId;
    private int transaction_id;
    private String merchant_name;
    private String transaction_date;
    private int transaction_amt;
    private int transaction_type;
    private String code;




    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public long getRowId() {
        return rowId;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    public int getTransaction_amt() {
        return transaction_amt;
    }

    public void setTransaction_amt(int transaction_amt) {
        this.transaction_amt = transaction_amt;
    }

    public int getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(int transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

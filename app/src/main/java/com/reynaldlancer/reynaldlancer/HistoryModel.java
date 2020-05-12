package com.reynaldlancer.reynaldlancer;

public class HistoryModel {
    String amount, date, status;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HistoryModel(String amount, String date, String status) {
        this.amount = amount;
        this.date = date;
        this.status = status;
    }
}

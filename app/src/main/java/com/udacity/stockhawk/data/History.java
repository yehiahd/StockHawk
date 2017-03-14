package com.udacity.stockhawk.data;

/**
 * Created by yehia on 10/03/17.
 */

public class History {

    private  Long date;
    private double price;

    public History(Long date, double price) {
        this.date = date;
        this.price = price;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

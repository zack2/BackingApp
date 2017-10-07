package com.nanodegree.udacy.backingapp.model;

/**
 * Created by root on 27/09/2017.
 */

public class WidgetItem {

    public String quantity;
    public String measure;
    public String ingredient;

    public WidgetItem(String quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }
}

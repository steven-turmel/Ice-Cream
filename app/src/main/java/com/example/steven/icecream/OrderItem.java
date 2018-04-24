package com.example.steven.icecream;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class OrderItem implements Serializable {
    private DateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm aaa");
    private String[] toppings;
    private double orderTotal;
    private double taxPaid;
    private double subTotal;
    private String size;
    private int fudge; //cannot be > 3;
    private Date orderDate;
    private String flavor;

    public OrderItem(String[] toppings, double price, int fudge, String size, String flavor) {
        this.fudge = fudge;
        this.size = size;
        subTotal = price;
        taxPaid = 0.07 * price;
        orderTotal = subTotal + taxPaid;
        this.toppings = toppings;
        this.flavor = flavor;
        orderDate = new Date();
    }

    @Override
    public String toString() {
        String temp = "Order: " + size + " " + flavor + " cone\n" +
                "Order placed: " + df.format(orderDate) + "\n" +
                "Toppings: " + getToppings() + "\n" +
                "Sub-total: " + "$" + String.format("%.2f",subTotal) + "\n" +
                "Tax paid: " + "$" + String.format("%.2f",taxPaid) + "\n" +
                "Total Cost: " + "$" + String.format("%.2f",orderTotal);
        return temp;
    }

    private String getToppings() {
        String temp = "";
        if (toppings.length == 0 && fudge == 0)
            return "none";
        else {
            for (int i = 0; i < toppings.length; i++) {
                temp += toppings[i] + ", ";
            }
        }
        temp += fudge + " oz. fudge";
        return temp;
    }
}

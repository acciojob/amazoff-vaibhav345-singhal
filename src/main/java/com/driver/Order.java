package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    private String originalTime;

    public Order(String id, String deliveryTime) {
        this.id = id;
        this.originalTime = deliveryTime;
        int hh = Integer.parseInt(deliveryTime.split(":")[0]) * 60;
        int mm = Integer.parseInt(deliveryTime.split(":")[1]);
        this.deliveryTime = hh + mm;
        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public String getOriginalTime() {
        return this.originalTime;
    }
}

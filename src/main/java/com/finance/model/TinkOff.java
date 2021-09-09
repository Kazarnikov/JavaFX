package com.finance.model;

public class TinkOff {
    private final String name;
    private int sum;
    private final double quantity = 1.0;
    private final int paymentType = 4;
    private final int productType = 1;
    private final int nds = 1;
    private int price;

    public TinkOff(String name, int sum) {
        this.name = name;
        this.sum = sum;
        this.price = sum;

    }

    public String getName() {
        return name;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
        this.price = sum;
    }

    @Override
    public String toString() {
        return "{\"name\": \"" + name + '\"' +
                ", \"price\": " + price +
                ", \"sum\": " + sum +
                ", \"quantity\": " + quantity +
                ", \"paymentType\": " + paymentType +
                ", \"productType\": " + productType +
                ", \"nds\": " + nds  + "}";
    }
}

package com.finance.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

public class TinkOff {
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty quantity = new SimpleStringProperty("1.0");
    private final StringProperty paymentType = new SimpleStringProperty("4");
    private final StringProperty productType = new SimpleStringProperty("1");
    private final StringProperty nds = new SimpleStringProperty("1");
    private final StringProperty sum = new SimpleStringProperty();
    private final StringProperty price = new SimpleStringProperty();
    private /*final*/ LocalDateTime dateTime;
    private final StringProperty category = new SimpleStringProperty();

    public TinkOff(String name, String sum, LocalDateTime dateTime, String category) {
        this.name.set(name);
        this.sum.set(sum);
        this.price.set(sum);
        this.category.set(category);
        this.dateTime = dateTime;
    }

    public TinkOff(String name, String sum) {
        this.name.set(name);
        this.sum.set(sum);
        this.price.set(sum);
        this.category.set(name);
        this.dateTime = LocalDateTime.now();
    }


    public TinkOff() {
        this.dateTime = LocalDateTime.now();
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getQuantity() {
        return quantity.get();
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }

    public StringProperty quantityProperty() {
        return quantity;
    }

    public String getPaymentType() {
        return paymentType.get();
    }

    public void setPaymentType(String paymentType) {
        this.paymentType.set(paymentType);
    }

    public StringProperty paymentTypeProperty() {
        return paymentType;
    }

    public String getProductType() {
        return productType.get();
    }

    public void setProductType(String productType) {
        this.productType.set(productType);
    }

    public StringProperty productTypeProperty() {
        return productType;
    }

    public String getNds() {
        return nds.get();
    }

    public void setNds(String nds) {
        this.nds.set(nds);
    }

    public StringProperty ndsProperty() {
        return nds;
    }

    public String getSum() {
        return sum.get();
    }

    public void setSum(String sum) {
        this.sum.set(sum);
        this.price.set(sum);
    }

    public StringProperty sumProperty() {
        return sum;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getCategory() {
        return category.get();
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public StringProperty categoryProperty() {
        return category;
    }

    @Override
    public String toString() {
        return "{\"name\": \"" + name + '\"' +
                ", \"price\": " + price +
                ", \"sum\": " + sum +
                ", \"quantity\": " + quantity +
                ", \"paymentType\": " + paymentType +
                ", \"productType\": " + productType +
                ", \"nds\": " + nds + "}";
    }
}

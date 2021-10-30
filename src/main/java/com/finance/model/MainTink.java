package com.finance.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public class MainTink {
    private final StringProperty name = new SimpleStringProperty();
    private final LongProperty dateTime = new SimpleLongProperty();
    private final StringProperty retailPlace = new SimpleStringProperty();
    private final ListProperty<Transaction> items = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final LongProperty operationType = new SimpleLongProperty(1);
    private final LongProperty cashTotalSum = new SimpleLongProperty(0);
    private final LongProperty code = new SimpleLongProperty(3);
    private final LongProperty fiscalDocumentFormatVer = new SimpleLongProperty(2);
    private final LongProperty taxationType = new SimpleLongProperty(1);
    private final LocalDateTime localDateTime;
    private final DoubleProperty totalSum = new SimpleDoubleProperty();
    private final DoubleProperty ecashTotalSum = new SimpleDoubleProperty();

    public MainTink(String name, String retailPlace, long dateTime, LocalDateTime localDateTime) {
        this.name.set(name);
        this.retailPlace.set(retailPlace);
        this.dateTime.set(dateTime);
        this.localDateTime = localDateTime;
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

    public long getDateTime() {
        return dateTime.get();
    }

    public void setDateTime(long dateTime) {
        this.dateTime.set(dateTime);
    }

    public LongProperty dateTimeProperty() {
        return dateTime;
    }

    public String getRetailPlace() {
        return retailPlace.get();
    }

    public void setRetailPlace(String retailPlace) {
        this.retailPlace.set(retailPlace);
    }

    public StringProperty retailPlaceProperty() {
        return retailPlace;
    }

    public ObservableList<Transaction> getItems() {
        return items.get();
    }

    public void setItems(ObservableList<Transaction> items) {
        this.items.set(items);
    }

    public void addItems(Transaction items) {
//        this.items.add(FXCollections.observableArrayList(items));
        this.items.add(items);
    }


    public ListProperty<Transaction> itemsProperty() {
        return items;
    }

    public long getOperationType() {
        return operationType.get();
    }

    public void setOperationType(long operationType) {
        this.operationType.set(operationType);
    }

    public LongProperty operationTypeProperty() {
        return operationType;
    }

    public long getCashTotalSum() {
        return cashTotalSum.get();
    }

    public void setCashTotalSum(long cashTotalSum) {
        this.cashTotalSum.set(cashTotalSum);
    }

    public LongProperty cashTotalSumProperty() {
        return cashTotalSum;
    }

    public long getCode() {
        return code.get();
    }

    public void setCode(long code) {
        this.code.set(code);
    }

    public LongProperty codeProperty() {
        return code;
    }

    public long getFiscalDocumentFormatVer() {
        return fiscalDocumentFormatVer.get();
    }

    public void setFiscalDocumentFormatVer(long fiscalDocumentFormatVer) {
        this.fiscalDocumentFormatVer.set(fiscalDocumentFormatVer);
    }

    public LongProperty fiscalDocumentFormatVerProperty() {
        return fiscalDocumentFormatVer;
    }

    public long getTaxationType() {
        return taxationType.get();
    }

    public void setTaxationType(long taxationType) {
        this.taxationType.set(taxationType);
    }

    public LongProperty taxationTypeProperty() {
        return taxationType;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public double getTotalSum() {
        return totalSum.get();
    }

    public void setTotalSum(double totalSum) {
        this.totalSum.set(totalSum);
        this.ecashTotalSum.set(totalSum);
    }

    public DoubleProperty totalSumProperty() {
        return totalSum;
    }

    @Override
    public String toString() {
        return "{" + "\"user\": \"" + name + '\"' +
                ", \"operationType\": " + operationType +
                ", \"totalSum\": " + totalSum +
                ", \"cashTotalSum\": " + cashTotalSum +
                ", \"ecashTotalSum\": " + ecashTotalSum +
                ", \"items\": " + items +
                ", \"code\": " + code +
                ", \"fiscalDocumentFormatVer\": " + fiscalDocumentFormatVer +
                ", \"retailPlace\": \"" + retailPlace + '\"' +
                ", \"dateTime\": " + dateTime +
                ", \"taxationType\": " + taxationType +
                ", \"localDateTime\": \"" + localDateTime + '\"' +
                '}';
    }
}

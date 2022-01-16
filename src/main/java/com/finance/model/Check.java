package com.finance.model;

import com.finance.utils.FormatUtil;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
@SuppressWarnings("unused")
public class Check {
    private final StringProperty                    user                        =       new SimpleStringProperty();
    private final LongProperty                      operationType               =       new SimpleLongProperty(1);
    private final LongProperty                      totalSum                    =       new SimpleLongProperty();
    private final LongProperty                      cashTotalSum                =       new SimpleLongProperty(0);
    private final ListProperty<Transaction>         items                       =       new SimpleListProperty<>(FXCollections.observableArrayList());
    private final LongProperty                      code                        =       new SimpleLongProperty(3);
    private final LongProperty                      fiscalDocumentFormatVer     =       new SimpleLongProperty(2);
    private final StringProperty                    retailPlace                 =       new SimpleStringProperty();
    private final LongProperty                      dateTime                    =       new SimpleLongProperty();
    private final LongProperty                      taxationType                =       new SimpleLongProperty(1);
    private final ObjectProperty<LocalDateTime>     localDateTime               =       new SimpleObjectProperty<>();

    public Check(Transaction transaction) {
        this.user.set(transaction.getDescription());
        this.retailPlace.set(transaction.getCategory());
        this.dateTime.set(FormatUtil.timestamp(transaction.getDateOperation()) );
        this.localDateTime.set(transaction.getDateOperation());
        this.items.add(transaction);
    }

    public String getName() {
        return user.get();
    }

    public void setName(String name) {
        this.user.set(name);
    }

    public StringProperty nameProperty() {
        return user;
    }

    public Long getDateTime() {
        return dateTime.get();
    }

    public void setDateTime(Long dateTime) {
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
        this.items.add(items);
    }


    public ListProperty<Transaction> itemsProperty() {
        return items;
    }

    public Long getOperationType() {
        return operationType.get();
    }

    public void setOperationType(Long operationType) {
        this.operationType.set(operationType);
    }

    public LongProperty operationTypeProperty() {
        return operationType;
    }

    public Long getCashTotalSum() {
        return cashTotalSum.get();
    }

    public void setCashTotalSum(Long cashTotalSum) {
        this.cashTotalSum.set(cashTotalSum);
    }

    public LongProperty cashTotalSumProperty() {
        return cashTotalSum;
    }

    public Long getCode() {
        return code.get();
    }

    public void setCode(Long code) {
        this.code.set(code);
    }

    public LongProperty codeProperty() {
        return code;
    }

    public Long getFiscalDocumentFormatVer() {
        return fiscalDocumentFormatVer.get();
    }

    public void setFiscalDocumentFormatVer(Long fiscalDocumentFormatVer) {
        this.fiscalDocumentFormatVer.set(fiscalDocumentFormatVer);
    }

    public LongProperty fiscalDocumentFormatVerProperty() {
        return fiscalDocumentFormatVer;
    }

    public Long getTaxationType() {
        return taxationType.get();
    }

    public void setTaxationType(Long taxationType) {
        this.taxationType.set(taxationType);
    }

    public LongProperty taxationTypeProperty() {
        return taxationType;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime.get();
    }

    public Long getTotalSum() {
        return totalSum.get();
    }

    public void setTotalSum(Long totalSum) {
        this.totalSum.set(totalSum);
    }

    public LongProperty totalSumProperty() {
        return totalSum;
    }

    @Override
    public String toString() {
        return "{" + "\"user\": \"" + getName() + '\"' +
                ", \"operationType\": " + getOperationType() +
                ", \"totalSum\": " + getTotalSum() +
                ", \"cashTotalSum\": " + getCashTotalSum() +
                ", \"ecashTotalSum\": " + getTotalSum() +
                ", \"items\": " + getItems() +
                ", \"code\": " + getCode() +
                ", \"fiscalDocumentFormatVer\": " + getFiscalDocumentFormatVer() +
                ", \"retailPlace\": \"" + getRetailPlace() + '\"' +
                ", \"dateTime\": " + getDateTime() +
                ", \"taxationType\": " + getTaxationType() +
                ", \"localDateTime\": \"" + getLocalDateTime() + '\"' +
                '}';
    }
}

package com.finance.model;

import com.finance.utils.FormatUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

@SuppressWarnings("unused")
public class TinkOff {
    //json check
    private final StringProperty description = new SimpleStringProperty(); //name
    private final StringProperty quantity = new SimpleStringProperty("1.0");
    private final StringProperty paymentType = new SimpleStringProperty("4");
    private final StringProperty productType = new SimpleStringProperty("1");
    private final StringProperty nds = new SimpleStringProperty("1");
    private final StringProperty price = new SimpleStringProperty();

//    private final StringConverter<LocalDateTime> dateTime = new StringConverter<>() {
//        @Override
//        public String toString(LocalDateTime date) {
//            return date == null ? null : FormatUtil.dateTimeFormatter().format(date);
//        }
//
//        @Override
//        public LocalDateTime fromString(String string) {
//            return string == null || string.isEmpty() ? null : LocalDateTime.parse(string, FormatUtil.dateTimeFormatter());
//        }
//    };
//      private LocalDateTime dateTime;
    //csv
    private final StringProperty dateOperation = new SimpleStringProperty();
    private final StringProperty datePayment = new SimpleStringProperty();
    private final StringProperty numberCard = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty transactionAmount = new SimpleStringProperty();
    private final StringProperty transactionCurrency = new SimpleStringProperty();
    private final StringProperty paymentAmount = new SimpleStringProperty();
    private final StringProperty paymentCurrency = new SimpleStringProperty();
    private final StringProperty cashback = new SimpleStringProperty();
    private final StringProperty category = new SimpleStringProperty();
    private final StringProperty mcc = new SimpleStringProperty();
    private final StringProperty bonuses = new SimpleStringProperty();
    private final StringProperty roundingInvestment = new SimpleStringProperty();
    private final StringProperty operationAmountRounding = new SimpleStringProperty();

    //invest moneybox
    public TinkOff(String description, String price) {
        this.description.set(description);
        this.datePayment.set(FormatUtil.nowLocalDate());
        this.dateOperation.set(FormatUtil.nowLocalDateTime());
        this.price.set(price);
        this.category.set(description);
        this.status.set("OK");
        this.transactionAmount.set("0.00");
        this.transactionCurrency.set("RUB");
        this.paymentAmount.set("0.00");
        this.paymentCurrency.set("RUB");
        this.bonuses.set("0.00");
        this.roundingInvestment.set("0.00");
        this.operationAmountRounding.set("0.00");
    }

    //All construct
    public TinkOff(String dateOperation,
                   String datePayment,
                   String numberCard,
                   String status,
                   String transactionAmount,
                   String transactionCurrency,
                   String paymentAmount,
                   String paymentCurrency,
                   String cashback,
                   String category,
                   String mcc,
                   String description,
                   String bonuses,
                   String roundingInvestment,
                   String operationAmountRounding,
                   String sum) {

        this.dateOperation.set(dateOperation);
        this.datePayment.set(datePayment);
        this.numberCard.set(numberCard);
        this.status.set(status);
        this.transactionAmount.set(transactionAmount);
        this.transactionCurrency.set(transactionCurrency);
        this.paymentAmount.set(paymentAmount);
        this.paymentCurrency.set(paymentCurrency);
        this.cashback.set(cashback);
        this.category.set(category);
        this.mcc.set(mcc);
        this.description.set(description);
        this.bonuses.set(bonuses);
        this.roundingInvestment.set(roundingInvestment);
        this.operationAmountRounding.set(operationAmountRounding);
        this.price.set(sum);
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

    public String getPrice() {
        return price.get();
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public StringProperty priceProperty() {
        return price;
    }

    public String getDateOperationString() {
        return dateOperation.get();
    }

    public LocalDateTime getDateOperation() {
        return FormatUtil.fromString(dateOperation.get());
    }

    public void setDateOperation(LocalDateTime dateOperation) {
        this.dateOperation.set(FormatUtil.dateTimeFormatter().format(dateOperation));
    }

    public void setDateOperation(String dateOperation) {
        this.dateOperation.set(dateOperation);
    }

    public StringProperty dateOperationProperty() {
        return dateOperation;
    }

    public String getDatePayment() {
        return datePayment.get();
    }

    public void setDatePayment(String datePayment) {
        this.datePayment.set(datePayment);
    }

    public StringProperty datePaymentProperty() {
        return datePayment;
    }

    public String getNumberCard() {
        return numberCard.get();
    }

    public void setNumberCard(String numberCard) {
        this.numberCard.set(numberCard);
    }

    public StringProperty numberCardProperty() {
        return numberCard;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public StringProperty statusProperty() {
        return status;
    }

    public String getTransactionAmount() {
        return transactionAmount.get();
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount.set(transactionAmount);
    }

    public StringProperty transactionAmountProperty() {
        return transactionAmount;
    }

    public String getTransactionCurrency() {
        return transactionCurrency.get();
    }

    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency.set(transactionCurrency);
    }

    public StringProperty transactionCurrencyProperty() {
        return transactionCurrency;
    }

    public String getPaymentAmount() {
        return paymentAmount.get();
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount.set(paymentAmount);
    }

    public StringProperty paymentAmountProperty() {
        return paymentAmount;
    }

    public String getPaymentCurrency() {
        return paymentCurrency.get();
    }

    public void setPaymentCurrency(String paymentCurrency) {
        this.paymentCurrency.set(paymentCurrency);
    }

    public StringProperty paymentCurrencyProperty() {
        return paymentCurrency;
    }

    public String getCashback() {
        return cashback.get();
    }

    public void setCashback(String cashback) {
        this.cashback.set(cashback);
    }

    public StringProperty cashbackProperty() {
        return cashback;
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

    public String getMcc() {
        return mcc.get();
    }

    public void setMcc(String mcc) {
        this.mcc.set(mcc);
    }

    public StringProperty mccProperty() {
        return mcc;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getBonuses() {
        return bonuses.get();
    }

    public void setBonuses(String bonuses) {
        this.bonuses.set(bonuses);
    }

    public StringProperty bonusesProperty() {
        return bonuses;
    }

    public String getRoundingInvestment() {
        return roundingInvestment.get();
    }

    public void setRoundingInvestment(String roundingInvestment) {
        this.roundingInvestment.set(roundingInvestment);
    }

    public StringProperty roundingInvestmentProperty() {
        return roundingInvestment;
    }

    public String getOperationAmountRounding() {
        return operationAmountRounding.get();
    }

    public void setOperationAmountRounding(String operationAmountRounding) {
        this.operationAmountRounding.set(operationAmountRounding);
    }

    public StringProperty operationAmountRoundingProperty() {
        return operationAmountRounding;
    }

    @Override
    public String toString() {
        return "{\"name\": \"" + description + '\"' +
                ", \"price\": " + price +
                ", \"sum\": " + price +
                ", \"quantity\": " + quantity +
                ", \"paymentType\": " + paymentType +
                ", \"productType\": " + productType +
                ", \"nds\": " + nds + "}";
    }
}

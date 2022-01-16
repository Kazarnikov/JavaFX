package com.finance.model;

import javafx.beans.property.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SuppressWarnings("unused")
public class Transaction {
    //json check
//  private final StringProperty                        description                 =       new SimpleStringProperty();
    private final DoubleProperty                        quantity                    =       new SimpleDoubleProperty(1.0);
    private final DoubleProperty                        paymentType                 =       new SimpleDoubleProperty(4);
    private final DoubleProperty                        productType                 =       new SimpleDoubleProperty(1);
    private final DoubleProperty                        nds                         =       new SimpleDoubleProperty(1);
    //csv
    private final ObjectProperty    <LocalDateTime>     dateOperation               =       new SimpleObjectProperty<>();
    private final ObjectProperty    <LocalDate>         datePayment                 =       new SimpleObjectProperty<>();
    private final StringProperty                        numberCard                  =       new SimpleStringProperty();
    private final StringProperty                        status                      =       new SimpleStringProperty();
    private final ObjectProperty    <BigDecimal>        transactionAmount           =       new SimpleObjectProperty<>();
    private final StringProperty                        transactionCurrency         =       new SimpleStringProperty();
    private final ObjectProperty    <BigDecimal>        paymentAmount               =       new SimpleObjectProperty<>();
    private final StringProperty                        paymentCurrency             =       new SimpleStringProperty();
    private final ObjectProperty    <BigDecimal>        cashback                    =       new SimpleObjectProperty<>();
    private final StringProperty                        category                    =       new SimpleStringProperty();
    private final StringProperty                        mcc                         =       new SimpleStringProperty();
    private final StringProperty                        description                 =       new SimpleStringProperty();
    private final ObjectProperty    <BigDecimal>        bonuses                     =       new SimpleObjectProperty<>();
    private final ObjectProperty    <BigDecimal>        roundingInvestment          =       new SimpleObjectProperty<>();
    private final ObjectProperty    <BigDecimal>        operationAmountRounding     =       new SimpleObjectProperty<>();

//    //invest moneybox
//    public Transaction(String description, BigDecimal transactionAmount) {
//        this.dateOperation.set(LocalDateTime.now());
//        this.datePayment.set(LocalDate.now());
//        this.numberCard.set("");
//        this.status.set("OK");
//        this.transactionAmount.set(transactionAmount.negate());
//        this.transactionCurrency.set("RUB");
//        this.paymentAmount.set(transactionAmount.negate());
//        this.paymentCurrency.set("RUB");
//        this.cashback.set(BigDecimal.ZERO);
//        this.category.set(description.split("\s")[0]);
//        this.mcc.set("");
//        this.description.set(description);
//        this.bonuses.set(BigDecimal.ZERO.setScale(2));
//        this.roundingInvestment.set(BigDecimal.ZERO.setScale(2));
//        this.operationAmountRounding.set(transactionAmount);
//    }

    //All construct
    public Transaction(LocalDateTime dateOperation,
                       LocalDate datePayment,
                       String numberCard,
                       String status,
                       BigDecimal transactionAmount,
                       String transactionCurrency,
                       BigDecimal paymentAmount,
                       String paymentCurrency,
                       BigDecimal cashback,
                       String category,
                       String mcc,
                       String description,
                       BigDecimal bonuses,
                       BigDecimal roundingInvestment,
                       BigDecimal operationAmountRounding) {

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
    }

//    public static Transaction.Builder builder() {
//        return new Transaction.Builder();
//    }

    public Double getQuantity() {
        return quantity.get();
    }

    public void setQuantity(Double quantity) {
        this.quantity.set(quantity);
    }

    public DoubleProperty quantityProperty() {
        return quantity;
    }

    public Double getPaymentType() {
        return paymentType.get();
    }

    public void setPaymentType(Double paymentType) {
        this.paymentType.set(paymentType);
    }

    public DoubleProperty paymentTypeProperty() {
        return paymentType;
    }

    public Double getProductType() {
        return productType.get();
    }

    public void setProductType(Double productType) {
        this.productType.set(productType);
    }

    public DoubleProperty productTypeProperty() {
        return productType;
    }

    public Double getNds() {
        return nds.get();
    }

    public void setNds(Double nds) {
        this.nds.set(nds);
    }

    public DoubleProperty ndsProperty() {
        return nds;
    }

    public LocalDateTime getDateOperation() {
        return dateOperation.get();
    }

    public void setDateOperation(LocalDateTime dateOperation) {
        this.dateOperation.set(dateOperation);
    }

    public ObjectProperty<LocalDateTime> dateOperationProperty() {
        return dateOperation;
    }

    public LocalDate getDatePayment() {
        return datePayment.get();
    }

    public void setDatePayment(LocalDate datePayment) {
        this.datePayment.set(datePayment);
    }

    public ObjectProperty<LocalDate> datePaymentProperty() {
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

    public BigDecimal getTransactionAmount() {
        return transactionAmount.get();
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount.set(transactionAmount);
    }

    public ObjectProperty<BigDecimal> transactionAmountProperty() {
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

    public BigDecimal getPaymentAmount() {
        return paymentAmount.get();
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount.set(paymentAmount);
    }

    public ObjectProperty<BigDecimal> paymentAmountProperty() {
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

    public BigDecimal getCashback() {
        return cashback.get();
    }

    public void setCashback(BigDecimal cashback) {
        this.cashback.set(cashback);
    }

    public ObjectProperty<BigDecimal> cashbackProperty() {
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

    public BigDecimal getBonuses() {
        return bonuses.get();
    }

    public void setBonuses(BigDecimal bonuses) {
        this.bonuses.set(bonuses);
    }

    public ObjectProperty<BigDecimal> bonusesProperty() {
        return bonuses;
    }

    public BigDecimal getRoundingInvestment() {
        return roundingInvestment.get();
    }

    public void setRoundingInvestment(BigDecimal roundingInvestment) {
        this.roundingInvestment.set(roundingInvestment);
    }

    public ObjectProperty<BigDecimal> roundingInvestmentProperty() {
        return roundingInvestment;
    }

    public BigDecimal getOperationAmountRounding() {
        return operationAmountRounding.get();
    }

    public void setOperationAmountRounding(BigDecimal operationAmountRounding) {
        this.operationAmountRounding.set(operationAmountRounding);
    }

    public ObjectProperty<BigDecimal> operationAmountRoundingProperty() {
        return operationAmountRounding;
    }

    @Override
    public String toString() {
        return "{\"name\": \"" + description + '\"' +
                ", \"price\": " + getTransactionAmount().doubleValue() * 100 +
                ", \"sum\": " + getTransactionAmount().doubleValue() * 100 +
                ", \"quantity\": " + getQuantity() +
                ", \"paymentType\": " + getPaymentType() +
                ", \"productType\": " + getProductType() +
                ", \"nds\": " + getNds() + "}";
    }

//    public static class Builder {
//        private final StringProperty description = new SimpleStringProperty();
//        private final StringProperty quantity = new SimpleStringProperty("1.0");
//        private final StringProperty paymentType = new SimpleStringProperty("4");
//        private final StringProperty productType = new SimpleStringProperty("1");
//        private final StringProperty nds = new SimpleStringProperty("1");
//        private final ObjectProperty<LocalDateTime> dateOperation = new SimpleObjectProperty<>();
//        private final ObjectProperty<LocalDate> datePayment = new SimpleObjectProperty<>();
//        private final StringProperty numberCard = new SimpleStringProperty();
//        private final StringProperty status = new SimpleStringProperty();
//        private final ObjectProperty<BigDecimal> transactionAmount = new SimpleObjectProperty<>();
//        private final StringProperty transactionCurrency = new SimpleStringProperty();
//        private final ObjectProperty<BigDecimal> paymentAmount = new SimpleObjectProperty<>();
//        private final StringProperty paymentCurrency = new SimpleStringProperty();
//        private final ObjectProperty<BigDecimal> cashback = new SimpleObjectProperty<>();
//        private final StringProperty category = new SimpleStringProperty();
//        private final StringProperty mcc = new SimpleStringProperty();
//        private final ObjectProperty<BigDecimal> bonuses = new SimpleObjectProperty<>();
//        private final ObjectProperty<BigDecimal> roundingInvestment = new SimpleObjectProperty<>();
//        private final ObjectProperty<BigDecimal> operationAmountRounding = new SimpleObjectProperty<>();
//
//        Builder() {
//        }
//
//        public Builder description(String description) {
//            this.description.set(description);
//            return this;
//        }
//
//        public Builder quantity(String quantity) {
//            this.quantity.set(quantity);
//            return this;
//        }
//
//        public Builder paymentType(String paymentType) {
//            this.paymentType.set(paymentType);
//            return this;
//        }
//
//        public Builder productType(String productType) {
//            this.productType.set(productType);
//            return this;
//        }
//
//        public Builder nds(String nds) {
//            this.nds.set(nds);
//            return this;
//        }
//
//        public Builder dateOperation(LocalDateTime dateOperation) {
//            this.dateOperation.set(dateOperation);
//            return this;
//        }
//
//        public Builder datePayment(LocalDate datePayment) {
//            this.datePayment.set(datePayment);
//            return this;
//        }
//
//        public Builder numberCard(String numberCard) {
//            this.numberCard.set(numberCard);
//            return this;
//        }
//
//        public Builder status(String status) {
//            this.status.set(status);
//            return this;
//        }
//
//        public Builder transactionAmount(BigDecimal transactionAmount) {
//            this.transactionAmount.set(transactionAmount);
//            return this;
//        }
//
//        public Builder transactionCurrency(String transactionCurrency) {
//            this.transactionCurrency.set(transactionCurrency);
//            return this;
//        }
//
//        public Builder paymentAmount(BigDecimal paymentAmount) {
//            this.paymentAmount.set(paymentAmount);
//            return this;
//        }
//
//        public Builder paymentCurrency(String paymentCurrency) {
//            this.paymentCurrency.set(paymentCurrency);
//            return this;
//        }
//
//        public Builder cashback(BigDecimal cashback) {
//            this.cashback.set(cashback);
//            return this;
//        }
//
//        public Builder category(String category) {
//            this.category.set(category);
//            return this;
//        }
//
//        public Builder mcc(String mcc) {
//            this.mcc.set(mcc);
//            return this;
//        }
//
//        public Builder bonuses(BigDecimal bonuses) {
//            this.bonuses.set(bonuses);
//            return this;
//        }
//
//        public Builder roundingInvestment(BigDecimal roundingInvestment) {
//            this.roundingInvestment.set(roundingInvestment);
//            return this;
//        }
//
//        public Builder operationAmountRounding(BigDecimal operationAmountRounding) {
//            this.operationAmountRounding.set(operationAmountRounding);
//            return this;
//        }
//
//        public Transaction build() {
//            return new Transaction(dateOperation.get(),
//                    datePayment.get(),
//                    numberCard.get(),
//                    status.get(),
//                    transactionAmount.get(),
//                    transactionCurrency.get(),
//                    paymentAmount.get(),
//                    paymentCurrency.get(),
//                    cashback.get(),
//                    category.get(),
//                    mcc.get(),
//                    description.get(),
//                    bonuses.get(),
//                    roundingInvestment.get(),
//                    operationAmountRounding.get());
//        }
//    }
}

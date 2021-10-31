package com.finance.convert;

public enum Structure {
    DATE_OPERATION("Дата операции", -1),                            //01 "15.10.2021 14:22:19"
    DATE_PAYMENT("Дата платежа", -1),                               //02 "15.10.2021"
    NUMBER_CARD("Номер карты", -1),                                 //03 "*2815"
    STATUS("Статус", -1),                                           //04 "OK"
    TRANSACTION_AMOUNT("Сумма операции", -1),                       //05 "-70.00"
    TRANSACTION_CURRENCY("Валюта операции", -1),                    //06 "RUB"
    PAYMENT_AMOUNT("Сумма платежа", -1),                            //07 "-70.00"
    PAYMENT_CURRENCY("Валюта платежа", -1),                         //08 "RUB"
    CASHBACK("Кэшбэк", -1),                                         //09 "2"
    CATEGORY("Категория", -1),                                      //10 "Рестораны"
    MCC("MCC", -1),                                                 //11 "5812"
    DESCRIPTION("Описание", -1),                                    //12 "Bez Xleba"
    BONUSES("Бонусы (включая кэшбэк)", -1),                         //13 "2.00"
    ROUNDING_INVESTMENT("Округление на инвесткопилку", -1),         //14 "29.88"
    OPERATION_AMOUNT_ROUNDING("Сумма операции с округлением", -1),  //15 "299.88"
//--------------------------------------//
    TRANSFER_CARD("Перевод с карты"),

    STATUS_OK("OK"),
    STATUS_FAILED("FAILED"),
    INVESTKOPILKA("Инвесткопилка"),

    CURRENCY_RUB("RUB");

    private final String value;
    private Integer index;

    Structure(String value) {
        this.value = value;
    }

    Structure(String value, Integer index) {
        this.value = value;
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
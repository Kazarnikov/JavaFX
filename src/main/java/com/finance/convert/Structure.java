package com.finance.convert;

enum Structure {
    DATE_OPERATION("Дата операции", -1),                            //01
    DATE_PAYMENT("Дата платежа", -1),                               //02
    NUMBER_CARD("Номер карты", -1),                                 //03
    STATUS("Статус", -1),                                           //04
    TRANSACTION_AMOUNT("Сумма операции", -1),                       //05
    TRANSACTION_CURRENCY("Валюта операции", -1),                    //06
    PAYMENT_AMOUNT("Сумма платежа", -1),                            //07
    PAYMENT_CURRENCY("Валюта платежа", -1),                         //08
    CASHBACK("Кэшбэк", -1),                                         //09
    CATEGORY("Категория", -1),                                      //10
    MCC("MCC", -1),                                                 //11
    DESCRIPTION("Описание", -1),                                    //12
    BONUSES("Бонусы (включая кэшбэк)", -1),                         //13
    ROUNDING_INVESTMENT("Округление на инвесткопилку", -1),         //14
    OPERATION_AMOUNT_ROUNDING("Сумма операции с округлением", -1);  //15

    private final String value;
    private Integer index;

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
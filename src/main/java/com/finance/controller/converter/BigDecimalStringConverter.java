package com.finance.controller.converter;

import com.finance.utils.FormatUtil;
import javafx.util.StringConverter;

import java.math.BigDecimal;

public class BigDecimalStringConverter extends StringConverter<BigDecimal> {

    public BigDecimalStringConverter() {
//        super();
    }

    @Override
    public String toString(BigDecimal value) {
        if (value == null) {
            return "";
        }
        return value.toString();
    }

    @Override
    public BigDecimal fromString(String value) {
        if (!value.isBlank() && value.matches(FormatUtil.getRexSum())) {
            return new BigDecimal(value);
        }
        return null;
    }
}

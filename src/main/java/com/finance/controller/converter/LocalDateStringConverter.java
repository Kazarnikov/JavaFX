package com.finance.controller.converter;

import com.finance.utils.FormatUtil;
import javafx.util.StringConverter;

import java.time.LocalDate;

public class LocalDateStringConverter extends StringConverter<LocalDate> {
    public LocalDateStringConverter() {
//        super();
    }

    @Override
    public String toString(LocalDate value) {
        return value.toString();
    }

    @Override
    public LocalDate fromString(String value) {
        if (!value.isBlank() && value.matches(FormatUtil.getRexDate())) {
            return FormatUtil.fromStringLD(value);
        }
        return null;
    }
}

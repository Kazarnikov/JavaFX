package com.finance.controller.converter;

import com.finance.utils.FormatUtil;
import javafx.util.StringConverter;

import java.time.LocalDateTime;

public class LocalDateTimeStringConverter extends StringConverter<LocalDateTime> {
    public LocalDateTimeStringConverter() {
//        super();
    }

    @Override
    public String toString(LocalDateTime value) {
        return value.toString();
    }

    @Override
    public LocalDateTime fromString(String value) {
        if (!value.isBlank() && value.matches(FormatUtil.getRexDateTime())) {
            return FormatUtil.fromStringLDT(value);
        }
        return null;
    }
}

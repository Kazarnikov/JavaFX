package com.finance.enums;

import com.finance.model.Transaction;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.BiPredicate;

public class FilterSearch {

    private static final Map<Class<?>, BiPredicate<Transaction, Object[]>> filterMap = Map.of(
            String.class, (tinkOff, value) -> tinkOff.getDateOperation().getMonth().name().equals(value[0]),
            LocalDateTime.class, (tinkOff, value) -> {
                LocalDateTime dateTime = tinkOff.getDateOperation();
                return dateTime.isAfter((LocalDateTime) value[0])
                        && dateTime.isBefore((LocalDateTime) value[1]);
            });

    public static boolean filter(Transaction transaction, Object...searchValue) {
        return filterMap.get(searchValue[0].getClass()).test(transaction, searchValue);
    }
}

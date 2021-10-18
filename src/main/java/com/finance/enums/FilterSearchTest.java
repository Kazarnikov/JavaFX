package com.finance.enums;

import com.finance.model.TinkOff;

import java.util.List;

class FilterSearchTest {
    public static void main(String[] args) {
        List<TinkOff> tinkOffList = List.of(
                new TinkOff("Мыло", "100"),
                new TinkOff("Яблоко", "120"),
                new TinkOff("Арбуз", "150"),
                new TinkOff("Бумага", "125"),
                new TinkOff("Вишня", "140")
        );

        for (TinkOff tinkOff : tinkOffList) {
            boolean мыло = FilterSearch.filter(tinkOff, "OCTOBER");
            System.out.println(мыло);
        }
    }


}
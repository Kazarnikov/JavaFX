package com.finance.enums;

import com.finance.model.Transaction;

import java.util.List;

class FilterSearchTest {
    public static void main(String[] args) {
        List<Transaction> transactionList = List.of(
//                new TinkOff("Мыло", "100"),
//                new TinkOff("Яблоко", "120"),
//                new TinkOff("Арбуз", "150"),
//                new TinkOff("Бумага", "125"),
//                new TinkOff("Вишня", "140")
        );

        for (Transaction transaction : transactionList) {
            boolean мыло = FilterSearch.filter(transaction, "OCTOBER");
            System.out.println(мыло);
        }
    }


}
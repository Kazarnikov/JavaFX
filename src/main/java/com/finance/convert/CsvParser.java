package com.finance.convert;

import com.finance.model.Transaction;
import com.finance.storage.Storage;
import com.finance.utils.FormatUtil;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class CsvParser {
    private final static Set<Transaction> mapOff = new HashSet<>();

    public static void convert(List<File> files) throws IOException {
        clear();
        LinkedHashSet<String> strings = new LinkedHashSet<>();
        accumulator(strings, files);

        AtomicReference<BigDecimal> investCopilkaSum = new AtomicReference<>(BigDecimal.ZERO);
        strings.stream().skip(1) // заголовки
                .forEach(s -> {
                    String[] str = Arrays.stream(s.split(";"))
                            .map(e -> e.trim().equals("") ? "0" : e.trim().replaceAll("[\"]", "").replaceAll(",", "."))
                            .toArray(String[]::new);

//                    String strTimestamp = str[Structure.DATE_OPERATION.getIndex()].replaceAll("..:..:..", "12:00:00");
//                    long timestamp = timestamp(strTimestamp);
//                    LocalDateTime localDateTime = localDateTime(timestamp);

                    LocalDateTime dateOperation = LocalDateTime.parse(str[Structure.DATE_OPERATION.getIndex()], FormatUtil.dateTimeFormatter());                                               //Дата операции
                    LocalDate datePayment = str[Structure.DATE_PAYMENT.getIndex()].isBlank()
                            ? dateOperation.toLocalDate() : LocalDate.parse(str[Structure.DATE_PAYMENT.getIndex()], FormatUtil.dateFormatter());                                                //Дата платежа
                    String numberCard = str[Structure.NUMBER_CARD.getIndex()];                                                                                                                  //Номер карты
                    String status = str[Structure.STATUS.getIndex()];                                                                                                                           //Статус
                    BigDecimal transactionAmount = FormatUtil.bigDecimal(str[Structure.TRANSACTION_AMOUNT.getIndex()]);                                                                         //Сумма операции
                    String transactionCurrency = str[Structure.TRANSACTION_CURRENCY.getIndex()];                                                                                                //Валюта операции
                    BigDecimal paymentAmount = FormatUtil.bigDecimal(str[Structure.PAYMENT_AMOUNT.getIndex()]);                                                                                 //Сумма платежа
                    String paymentCurrency = str[Structure.PAYMENT_CURRENCY.getIndex()];                                                                                                        //Валюта платежа
                    BigDecimal cashback = FormatUtil.bigDecimal(str[Structure.CASHBACK.getIndex()]);                                                                                            //Кэшбэк
                    String category = str[Structure.CATEGORY.getIndex()];                                                                                                                       //Категория
                    String mcc = str[Structure.MCC.getIndex()];                                                                                                                                 //MCC
                    String description = str[Structure.DESCRIPTION.getIndex()];                                                                                                                 //Описание
                    BigDecimal bonuses = FormatUtil.bigDecimal(str[Structure.BONUSES.getIndex()]);                                                                                              //Бонусы (включая кэшбэк)
                    BigDecimal roundingInvestment = FormatUtil.bigDecimal(str[Structure.ROUNDING_INVESTMENT.getIndex()]);                                                                       //Округление на инвесткопилку
                    BigDecimal operationAmountRounding = FormatUtil.bigDecimal(str[Structure.OPERATION_AMOUNT_ROUNDING.getIndex()]);                                                            //Сумма операции с округлением
                    investCopilkaSum.updateAndGet(bigDecimal ->  bigDecimal.add(roundingInvestment));

                    if (!"6012".equals(mcc)) {
                    Transaction transaction = new Transaction(
                            dateOperation,
                            datePayment,
                            numberCard,
                            status,
                            transactionAmount,
                            transactionCurrency,
                            paymentAmount,
                            paymentCurrency,
                            cashback,
                            category,
                            mcc,
                            description,
                            bonuses,
                            roundingInvestment,
                            operationAmountRounding);
                    mapOff.add(transaction);
                    }
                });
        Transaction transaction = new Transaction("Инвесткопилка сумма", investCopilkaSum.get());
        mapOff.add(transaction);
        Storage.setList(mapOff);
    }

    private static void accumulator(Set<String> strings, List<File> files) {
        files.forEach(file -> {
                    try {
                        Files.readAllLines(file.toPath(), Charset.forName("Windows-1251"))
                                .stream()
                                .map(String::trim)
                                .filter(((Predicate<String>) String::isEmpty).negate())
                                .forEach(strings::add);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
        setIndexStructure(strings);
    }

    private static void setIndexStructure(Set<String> strings) {
        String[] split = strings.iterator().next().replaceAll("\"", "").split(";");
        for (int i = 0; i < split.length; i++) {
            FF:
            for (Structure structure : Structure.values()) {
                if (split[i].equals(structure.getValue())) {
                    structure.setIndex(i);
                    break FF;
                }
            }
        }
    }

    private static void clear() {
        mapOff.clear();
        Storage.getList().clear();
    }
}
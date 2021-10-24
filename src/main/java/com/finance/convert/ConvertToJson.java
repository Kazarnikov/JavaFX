package com.finance.convert;

import com.finance.model.TinkOff;
import com.finance.storage.StorageOff;
import com.finance.utils.FormatUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

import static com.finance.utils.FormatUtil.numberToString;

public class ConvertToJson {
    private final static Set<TinkOff> mapOff = new HashSet<>();

    public static void convert(List<File> files) throws IOException {
        clear();
        LinkedHashSet<String> strings = new LinkedHashSet<>();
        accumulator(strings, files);

        AtomicReference<Double> investCopilkaSum = new AtomicReference<>(0.0);
        strings.stream().skip(1) // заголовки
                .forEach(s -> {
                    String[] str = Arrays.stream(s.split(";"))
                            .map(e -> e.trim().equals("") ? "0" : e.trim().replaceAll("[\"]", "").replaceAll(",", "."))
                            .toArray(String[]::new);

//                    String strTimestamp = str[Structure.DATE_OPERATION.getIndex()].replaceAll("..:..:..", "12:00:00");
//                    long timestamp = timestamp(strTimestamp);
//                    LocalDateTime localDateTime = localDateTime(timestamp);

                    String dateOperation = str[Structure.DATE_OPERATION.getIndex()];                                                //Дата операции
                    String datePayment = str[Structure.DATE_PAYMENT.getIndex()];                                                    //Дата платежа
                    String numberCard = str[Structure.NUMBER_CARD.getIndex()];                                                      //Номер карты
                    String status = str[Structure.STATUS.getIndex()];                                                               //Статус
                    Double transactionAmount = Double.parseDouble(str[Structure.TRANSACTION_AMOUNT.getIndex()]);                    //Сумма операции
                    String transactionCurrency = str[Structure.TRANSACTION_CURRENCY.getIndex()];                                    //Валюта операции
                    Double paymentAmount = Double.parseDouble(str[Structure.PAYMENT_AMOUNT.getIndex()]);                            //Сумма платежа
                    String paymentCurrency = str[Structure.PAYMENT_CURRENCY.getIndex()];                                            //Валюта платежа
                    String cashback = str[Structure.CASHBACK.getIndex()];                                                           //Кэшбэк
                    String category = str[Structure.CATEGORY.getIndex()];                                                           //Категория
                    String mcc = str[Structure.MCC.getIndex()];                                                                     //MCC
                    String description = str[Structure.DESCRIPTION.getIndex()];                                                     //Описание
                    Double bonuses = Double.parseDouble(str[Structure.BONUSES.getIndex()]);                                         //Бонусы (включая кэшбэк)
                    Double roundingInvestment = Double.parseDouble(str[Structure.ROUNDING_INVESTMENT.getIndex()]);                  //Округление на инвесткопилку
                    Double operationAmountRounding = Double.parseDouble(str[Structure.OPERATION_AMOUNT_ROUNDING.getIndex()]);       //Сумма операции с округлением
                    investCopilkaSum.updateAndGet(v -> v + roundingInvestment);

//                    if (!("6012".equals(mcc) || "FAILED".equals(status))) {
                        String sum = paymentAmount < 0 ? "-" + operationAmountRounding : "+" + operationAmountRounding;
                        datePayment = FormatUtil.isNonEmpty(datePayment) ? datePayment : dateOperation.substring(0, 10);
                        TinkOff tinkOff = new TinkOff(
                                dateOperation,
                                datePayment,
                                numberCard,
                                status,
                                FormatUtil.numberToString(transactionAmount),
                                transactionCurrency,
                                FormatUtil.numberToString(paymentAmount),
                                paymentCurrency,
                                cashback,
                                category,
                                mcc,
                                description,
                                FormatUtil.numberToString(bonuses),
                                FormatUtil.numberToString(roundingInvestment),
                                FormatUtil.numberToString(operationAmountRounding),
                                FormatUtil.stringToString(sum));
                        mapOff.add(tinkOff);
//                    }
                });

        TinkOff tinkOff = new TinkOff("Инвесткопилка", numberToString(investCopilkaSum.get()));
        mapOff.add(tinkOff);
        StorageOff.setList(mapOff);
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
        StorageOff.getList().clear();
    }
}
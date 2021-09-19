package com.finance.convert;

import com.finance.model.TinkOff;
import com.finance.storage.StorageOff;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;

import static com.finance.utils.FormatUtil.numberToString;

public class ConvertToJsonOff {
    private final static ArrayList<TinkOff> mapOff = new ArrayList<>();

    public static void convert(File file) throws IOException {
        mapOff.clear();
        StorageOff.getList().clear();
        List<String> strings = Files.readAllLines(file.toPath(),
                Charset.forName("Windows-1251"));

        AtomicReference<Double> investCopilkaSum = new AtomicReference<>(0.0);

        strings.stream()
                .skip(1) // заголовки
                .forEach(s -> {
                    String[] str = Arrays.stream(s.split(";"))
                            .map(e -> e.trim().equals("") ? "0" : e.trim().replaceAll("[\"]", "").replaceAll(",", "."))
                            .toArray(String[]::new);

                    String strDate = str[0].replaceAll("..:..:..", "12:00:00");
                    long timestamp = timestamp(strDate);
                    LocalDateTime localDateTime = localDateTime(timestamp);

                    String dateOperation = str[0];                                              //Дата операции
                    String datePayment = str[1];                                                //Дата платежа
                    String numberCard = str[2];                                                 //Номер карты
                    String status = str[3];                                                     //Статус
                    Double transactionAmount = Double.parseDouble(str[4]);                      //Сумма операции
                    String transactionCurrency = str[5];                                        //Валюта операции
                    Double sumPrice = Double.parseDouble(str[6]);                               //Сумма платежа
                    String paymentCurrency = str[7];                                            //Валюта платежа
                    String cashback = str[8];                                                   //Кэшбэк
                    String retailPlace = str[9];                                                //Категория
                    String mcc = str[10];                                                       //MCC
                    String user = str[11];                                                      //Описание
                    String userConcat = user + (numberCard.equals("*4749") ? " Вера" : "");     //user
                    Double cash = Double.parseDouble(str[12]);                                  //Бонусы (включая кэшбэк)
                    Double investCopilka = Double.parseDouble(str[13]);                         //Округление на инвесткопилку
                    Double operationAmountRounding = Double.parseDouble(str[14]);               //Сумма операции с округлением
                    investCopilkaSum.updateAndGet(v -> v + investCopilka);

                    if (!"6012".equals(mcc)) {
                        String sum = sumPrice < 0 ? "-" + operationAmountRounding : numberToString(operationAmountRounding);
                        TinkOff tinkOff = new TinkOff(userConcat, sum, localDateTime, retailPlace);
                        mapOff.add(tinkOff);

                    }

                });
        TinkOff tinkOff = new TinkOff("Инвесткопилка", numberToString(investCopilkaSum.get()));
        mapOff.add(tinkOff);
        StorageOff.setList(mapOff);
    }

    private static LocalDateTime localDateTime(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp * 1000),
                TimeZone.getDefault().toZoneId());
    }

    private static Long timestamp(String args) {
        DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return Timestamp.valueOf(LocalDateTime.from(formatDateTime.parse(args))).getTime() / 1000;
    }
}

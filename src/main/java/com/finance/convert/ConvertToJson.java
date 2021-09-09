package com.finance.convert;

import com.finance.model.MainTink;

import com.finance.model.TinkOff;

import java.io.File;
import java.io.FileWriter;
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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ConvertToJson {
    private static ArrayList<MainTink> map = new ArrayList<>();

    public static ArrayList<MainTink> convert(File file) throws IOException {
        map.clear();
        List<String> strings = Files.readAllLines(file.toPath(),
                Charset.forName("Windows-1251"));

        AtomicInteger investCopilkaSum = new AtomicInteger();

        strings.stream()
                .skip(1) // заголовки
                .forEach(s -> {
                    String[] str = Arrays.stream(s.split(";"))
                            .map(e -> e.trim().equals("") ? "0" : e.trim().replaceAll("[\",-]", ""))
                            .toArray(String[]::new);

                    String strDate = str[0].replaceAll("..:..:..", "12:00:00");
                    long timestamp = timestamp(strDate);
                    LocalDateTime localDateTime = localDateTime(timestamp);
                    String numberCard = str[2];
                    int sumPrice = Integer.parseInt(str[6]); //summa
                    int cash = Integer.parseInt(str[12]);
                    String retailPlace = str[9]; //category
                    String mcc = str[10]; //category
                    String user = str[11] + (numberCard.equals("*4749") ? " Вера" : ""); //user
                    int investCopilka = Integer.parseInt(str[13]);
                    investCopilkaSum.addAndGet(investCopilka);

                    if (!"6012".equals(mcc)) {
                        boolean dataFalse = map.stream().anyMatch(e -> e.getDateTime() == timestamp);

                        if (!dataFalse) {
                            MainTink mainTink = new MainTink(str[11], retailPlace, timestamp, localDateTime);
                            mainTink.setItems(new TinkOff(user, sumPrice));
                            map.add(mainTink);
                        } else {
                            map.forEach(e -> {
                                if (e.getDateTime() == timestamp) {
                                    AtomicBoolean bool = new AtomicBoolean(true);
                                    e.getItems().forEach(i -> {
                                        if (i.getName().equals(user)) {
                                            i.setSum(i.getSum() + sumPrice);
                                            bool.set(false);
                                        }
                                    });
                                    if (bool.get()) {
                                        e.setItems(new TinkOff(user, sumPrice));
                                    }
                                }
                            });
                        }
                    }
                });

        long timestamp = map.get(map.size() - 1).getDateTime() + 1;
        MainTink mainTink = new MainTink("Копилка", "Копилка", timestamp, localDateTime(timestamp));
        mainTink.setItems(new TinkOff("Инвесткопилка", investCopilkaSum.get()));
        mainTink.setTotalSum(investCopilkaSum.get());
        map.add(mainTink);

        map.forEach(e ->
                e.setTotalSum(e.getItems().stream().mapToInt(TinkOff::getSum).sum()));
        return map;
    }

    public static LocalDateTime localDateTime(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp * 1000),
                TimeZone.getDefault().toZoneId());
    }

    public static Long timestamp(String args) {
        DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return Timestamp.valueOf(LocalDateTime.from(formatDateTime.parse(args))).getTime() / 1000;
    }

    public static int writer() {
        File filePath = new File("date");
        int i = 0;
        filePath.mkdirs();
        for (MainTink mainTink : map) {
            try (FileWriter writer = new FileWriter("date/" + mainTink.getDateTime() + ".json", false)) {
                writer.write(mainTink.toString());
                writer.flush();
                ++i;
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return i;
    }

    public static void print() {
        System.out.println(map.size());
        System.out.println(map.stream().mapToLong(MainTink::getTotalSum).sum() / 100.00);
        map.forEach(e -> System.out.println(e.getUser() + " " + e.getTotalSum() / 100.00));
    }
}

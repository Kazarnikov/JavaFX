package com.finance.handler;


import com.finance.convert.CsvParser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MyFileChooser {
    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("Выбрать CSV");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("Все файлы", "*.*"));
    }

    public static void openFile() {
        AtomicReference<List<File>> file = new AtomicReference<>();
        final FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        file.set(Arrays.asList(new File("/media/D/Ubunta/Загрузки/fx/Finance/src/main/resources/csv/1.csv")));
        //выбор одного файла
//        file.set(fileChooser.showOpenDialog(stage));
        //выбор несколько файлов
//        file.set(fileChooser.showOpenMultipleDialog(stage));
        if (file.get() != null) {
            try {
                CsvParser.convert(file.get());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
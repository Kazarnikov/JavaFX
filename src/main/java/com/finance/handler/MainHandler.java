package com.finance.handler;


import com.finance.convert.ConvertToJson;
import com.finance.convert.ConvertToJsonOff;
import com.finance.model.MainTink;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MainHandler {

    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("Выбрать CSV");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("Все файлы", "*.*"));
    }

    public static void openFile(Stage stage) {
        AtomicReference<File> file = new AtomicReference<>();
        final FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        file.set(new File("/home/sf/Рабочий стол/1.csv"));
//        file.set(fileChooser.showOpenDialog(stage));
//        file.set(fileChooser.showOpenMultipleDialog(stage));
        if (file.get() != null) {
            try {
//                ConvertToJson.convert(file.get());
                ConvertToJsonOff.convert(file.get());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
//        return null;
    }

    public static void start(Stage stage) {


        //  final TableView<MainTink> tblItems = new TableView<>();
//        tblItems.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        tblItems.setPlaceholder(new Label("Нет данных"));
//        VBox.setVgrow(tblItems, Priority.ALWAYS);

//        final TableColumn<MainTink, String> name = new TableColumn<>("Имя");
//        final TableColumn<MainTink, String> summa = new TableColumn<>("Сумма");
//        final TableColumn<MainTink, Float> retailPlace = new TableColumn<>("Категория");
//        final TableColumn<MainTink, Boolean> localDateTime = new TableColumn<>("Дата");
//
//        name.setCellValueFactory(new PropertyValueFactory<>("user"));
//        summa.setCellValueFactory(new PropertyValueFactory<>("totalSum"));
//        retailPlace.setCellValueFactory(new PropertyValueFactory<>("retailPlace"));
//        localDateTime.setCellValueFactory(new PropertyValueFactory<>("localDateTime"));

//        tblItems.getColumns().addAll(name, summa, retailPlace, localDateTime);

//        TableView.TableViewSelectionModel<MainTink> selectionModel = tblItems.getSelectionModel();
//        selectionModel.setSelectionMode(SelectionMode.SINGLE);
//        ObservableList<MainTink> selectedItems = selectionModel.getSelectedItems();
//        final Stage[] stage1 = {null};
//
//        tblItems.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
//            @Override
//            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
//                if (mouseEvent.getClickCount() == 2 && !selectedItems.isEmpty()) {
//                    System.out.println(selectedItems);
//                    if (stage1[0] == null) {
//                        stage1[0] = newWindow();
//                        System.out.println("new");
//                    } else {
//                        stage1[0].showAndWait();
//
//                        System.out.println("update");
//                    }
//                }
//            }
//        });


//        Button selectFile = new Button("Добавить файл");
//        Button convertFiles = new Button("Преобразовать");
//        Label nameLabel = new Label();

//        showFiles.disableProperty().bind(
//                tblItems.tableMenuButtonVisibleProperty()
//        );
//
//        convertFiles.disableProperty().bind(
//                tblItems.getSelectionModel().selectedItemProperty().isNull()
//        );


        //выбор одного файла

//
//        convertFiles.setOnAction(e -> {
//            if (file.get() != null) {
//                nameLabel.setText("Сохранено файлов: " + ConvertToJson.writer());
////                ConvertToJson.print();
//            } else {
//                tblItems.setPlaceholder(new Label("Добавьте файл"));
//            }
//        });

//
//        MenuBar menuBar = new MenuBar();
//        Menu menu1 = new Menu("Файл");
//        MenuItem menuItem11 = new MenuItem("Item 1");
//        MenuItem menuItem12 = new MenuItem("Item 2");
//
//        Menu menu2 = new Menu("Вооот");
//        MenuItem menuItem21 = new MenuItem("Item 1");
//        MenuItem menuItem22 = new MenuItem("Item 2");
//
//        menu1.getItems().addAll(menuItem11, menuItem12);
//        menu2.getItems().addAll(menuItem21, menuItem22);
//
//        menuBar.getMenus().addAll(menu1, menu2);
//
//        VBox menuBox = new VBox(menuBar);
//
//        HBox buttonBox = new HBox(selectFile, convertFiles, nameLabel);
//        buttonBox.setSpacing(10);
//
//        VBox vbox = new VBox(menuBox, tblItems, buttonBox);
//        vbox.setPadding(new Insets(0, 8, 0, 8));
//        vbox.setMargin(buttonBox, new Insets(8, 8, 8, 8));
//
//        stage.setTitle("TableSelectApp");
//        stage.setScene(new Scene(vbox));
//        stage.setHeight(376);
//        stage.setWidth(667);
//        stage.show();
    }

    public static Stage newWindow() {
        final Label secondLabel = new Label();
        final StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondLabel);
        final Scene secondScene = new Scene(secondaryLayout, 230, 100);
        final Stage newWindow = new Stage();
        newWindow.setTitle("Second Stage");
        newWindow.setScene(secondScene);
        newWindow.show();
        return newWindow;
    }
}
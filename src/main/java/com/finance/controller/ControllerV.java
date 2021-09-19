//package com.finance.controller;
//
//import com.finance.MainApplication;
//import com.finance.handler.MainHandler;
//import com.finance.model.MainTink;
//import com.finance.model.TinkOff;
//import com.finance.storage.StorageMain;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.fxml.FXML;
//import javafx.scene.chart.PieChart;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.control.cell.TextFieldTableCell;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.AnchorPane;
//
//import java.net.URL;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//import java.util.ResourceBundle;
//import java.util.stream.Collectors;
//
//public class ControllerV {
//    private boolean isTabMain = false;
//    private boolean isTabDiagramma = false;
//
//
//    @FXML
//    private ResourceBundle resources;
//
//    @FXML
//    private URL location;
//
//    @FXML
//    private AnchorPane anchorPane;
//
//    @FXML
//    private TabPane tabPanel;
//
//    @FXML
//    private Tab tabMain;
//
//    @FXML
//    private AnchorPane anchorPaneTabMain;
//
//    @FXML
//    private TableView<MainTink> tableView;
//
//    @FXML
//    private TableColumn<MainTink, String> name;
//
//    @FXML
//    private TableColumn<MainTink, LocalDateTime> localDateTime;
//
//    @FXML
//    private TableColumn<MainTink, String> retailPlace;
//
//    @FXML
//    private TableColumn<MainTink, Double> totalSum;
//
//    @FXML
//    private TableColumn<MainTink, Long> dateTime;
//
//    @FXML
//    private TableColumn<MainTink, List<TinkOff>> items;
//
//    @FXML
//    private Tab tabDiagramma;
//
//    @FXML
//    private AnchorPane anchorPaneTabDiagramma;
//
//    @FXML
//    private PieChart pieChartDiagramma;
//
//    @FXML
//    private Label captionDiagramma;
//
//    @FXML
//    private Button selectFile;
//
//    @FXML
//    private Button update;
//
//    @FXML
//    private Button save;
//
//    @FXML
//    private Label label;
//
//    @FXML
//    void onSave(MouseEvent event) {
//        System.err.println(pieChartDiagramma.getData().size());
//    }
//
//
//    @FXML
//    void onSelectFile(MouseEvent event) {
//        if (isTabMain) {
//            tabPanel.getTabs().add(tabMain);
//            tabPanel.getSelectionModel().select(tabMain);
//            isTabMain = false;
//        }
//        List<MainTink> list = MainHandler.openFile(MainApplication.getPrimaryStage());
//        if (list != null) {
//            ObservableList<MainTink> observableList = FXCollections.observableArrayList(list);
//            tableView.getItems().clear();
//            tableView.getItems().addAll(observableList);
//            label.setText("Всего файлов: " + observableList.size());
//        }
//        update.setDisable(tableView.getItems().isEmpty());
//        save.setDisable(tableView.getItems().isEmpty());
//    }
//
//    @FXML
//    void onUpdate(MouseEvent event) {
//        if (isTabDiagramma) {
//            tabPanel.getTabs().add(tabDiagramma);
//            isTabDiagramma = false;
//        }
//        tabPanel.getSelectionModel().select(tabDiagramma);
//        pieChartDiagramma.getData().clear();
//        pieChartDiagramma.setLabelsVisible(false);
//
//        //        map.stream().map(MainTink::getItems).mapToInt(List::size).sum();
////        map.stream().map(MainTink::getItems).map(List::size).collect(Collectors.toList());
//
//        List<MainTink> list = getLest();
//        List<MainTink> list1 = new ArrayList<>();
//        double sumPercent = 0.0;
//        List<ObservableList<TinkOff>> collect1 = list.stream()
//                .map(MainTink::getItems)
//                .collect(Collectors.toList());
//        //forEach();mapToDouble(TinkOff::getSum).sum();
////        {
////            boolean i = true;
////            for (MainTink mainTink : list1) {
////                if (e.getName().equals(mainTink.getName())) {
////                    mainTink.setTotalSum(mainTink.getTotalSum() + e.getTotalSum());
////                    i = false;
////                }
////            }
////            if (i) {
////                list1.add(e);
////            }
////        }).mapToDouble(MainTink::getTotalSum).sum();
//
//        List<PieChart.Data> collect = list1.stream()
//                .sorted(Comparator.comparing(MainTink::getTotalSum).reversed())
//                .map(e -> new PieChart.Data(e.getName() + " - " + e.getTotalSum() / 100 + " руб.",
//                        (e.getTotalSum() / sumPercent * 100)))
//                .collect(Collectors.toList());
//
//
//        pieChartDiagramma.getData().addAll(collect);
//        pieChartDiagramma.getData().forEach(data -> {
//            data.getNode().addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent e) {
////                    captionDiagramma.setTranslateX(e.getSceneX());
////                    captionDiagramma.setTranslateY(e.getSceneY());
//                    captionDiagramma.setText(
//                            String.format("%1$s %2$.2f", data.getName().split("-")[0], data.getPieValue()) + "%");
//                }
//            });
//        });
//    }
//
//    @FXML
//    void onSelectCategoryDiagramma(MouseEvent event) {
////
//    }
//
//    @FXML
//    void onSelectCategoryMainTable(MouseEvent event) {
//
//    }
//
//
//    @FXML
//    void initialize() {
//        tabMain.setOnClosed(event -> {
//            isTabMain = true;
//        });
//        tabDiagramma.setOnClosed(event -> {
//            isTabDiagramma = true;
//        });
//
//        tabPanel.setTabDragPolicy(TabPane.TabDragPolicy.REORDER);
//        tableView.setPlaceholder(new Label("Нет данных"));
//
//        name.setCellValueFactory(new PropertyValueFactory<MainTink, String>("name"));
//        name.setCellFactory(TextFieldTableCell.<MainTink>forTableColumn());
//
//        totalSum.setCellValueFactory(new PropertyValueFactory<MainTink, Double>("totalSum"));
////        totalSum.setCellFactory(new Callback<TableColumn<MainTink, Double>, TableCell<MainTink, Double>>() {
////            @Override
////            public TableCell<MainTink, Double> call(TableColumn<MainTink, Double> param) {
////                return null;
////            }
////        });
//
//        retailPlace.setCellValueFactory(new PropertyValueFactory<MainTink, String>("retailPlace"));
//        retailPlace.setCellFactory(TextFieldTableCell.<MainTink>forTableColumn());
//
//        localDateTime.setCellValueFactory(new PropertyValueFactory<MainTink, LocalDateTime>("localDateTime"));
////        localDateTime.setCellFactory(new Callback<TableColumn<MainTink, LocalDateTime>, TableCell<MainTink, LocalDateTime>>() {
////            @Override
////            public TableCell<MainTink, LocalDateTime> call(TableColumn<MainTink, LocalDateTime> param) {
////                return null;
////            }
////        });
//
//        dateTime.setCellValueFactory(new PropertyValueFactory<MainTink, Long>("dateTime"));
////        dateTime.setCellFactory(new Callback<TableColumn<MainTink, Long>, TableCell<MainTink, Long>>() {
////            @Override
////            public TableCell<MainTink, Long> call(TableColumn<MainTink, Long> param) {
////                return null;
////            }
////        });
//
//        items.setCellValueFactory(new PropertyValueFactory<MainTink, List<TinkOff>>("items"));
////        items.setCellFactory(new Callback<TableColumn<MainTink, List<TinkOff>>, TableCell<MainTink, List<TinkOff>>>() {
////            @Override
////            public TableCell<MainTink, List<TinkOff>> call(TableColumn<MainTink, List<TinkOff>> param) {
////                return null;
////            }
////        });
//    }
//
//    private List<MainTink> getLest() {
//        return StorageMain.getList();
//    }
//
//    //=====EDIT===
//    @FXML
//    void onEditDate(ActionEvent event) {
//
//    }
//
//    @FXML
//    void onEditItems(ActionEvent event) {
//
//    }
//
//    @FXML
//    void onEditName(ActionEvent event) {
//        System.out.println("---");
//    }
//
//    @FXML
//    void onEditSum(ActionEvent event) {
//
//    }
//}

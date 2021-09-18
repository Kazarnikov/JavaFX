//package com.finance.controller;
//
//import com.finance.MainApplication;
//import com.finance.handler.MainHandler;
//import com.finance.model.MainTink;
//import com.finance.model.TinkOff;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.input.MouseEvent;
//
//import java.io.IOException;
//import java.net.URL;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.ResourceBundle;
//
//
//public class Controller {
//
//    @FXML
//    private ResourceBundle resources;
//
//    @FXML
//    private URL location;
//
//    @FXML
//    private TableView<MainTink> tableView;
//
//    @FXML
//    private TableColumn<MainTink, String> name;
//
//    @FXML
//    private TableColumn<MainTink, Long> totalSum;
//
//    @FXML
//    private TableColumn<MainTink, Long> dateTime;
//
//    @FXML
//    private TableColumn<MainTink, List<TinkOff>> items;
//
//    @FXML
//    private TableColumn<MainTink, String> retailPlace;
//
//    @FXML
//    private TableColumn<MainTink, LocalDateTime> localDateTime;
//
//    @FXML
//    private Button addButton;
//
//    @FXML
//    private Button selectButton;
//
//    @FXML
//    private Label label;
//
//    @FXML
//    private Button openPieChart;
//
//    @FXML
//    void onActionAddButton(ActionEvent event) {
//        List<MainTink> list = MainHandler.openFile(MainApplication.getPrimaryStage());
//        if (list != null) {
//            ObservableList<MainTink> observableList = FXCollections.observableArrayList(list);
//            tableView.getItems().addAll(observableList);
//            label.setText("Всего файлов: " + observableList.size());
//            selectButton.setDisable(false);
//            openPieChart.setDisable(false);
//        }
//    }
//
//    @FXML
//    void onActionSelectButton(ActionEvent event) {
//        ObservableList<MainTink> items = tableView.getItems();
//        System.out.println(items);
//    }
//
//    @FXML
//    void onMouseClicked(MouseEvent event) {
//        System.out.println(event.getSource());
//    }
//
//    @FXML
//    void onActionOpenPieChart(ActionEvent event) {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("diagramma-view.fxml"));
//            MainApplication.getPrimaryStage().setScene(new Scene(fxmlLoader.load()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @FXML
//    void initialize() {
//        openPieChart.setDisable(false);
//        tableView.setPlaceholder(new Label("Нет данных"));
//        name.setCellValueFactory(new PropertyValueFactory<MainTink, String>("name"));
//        totalSum.setCellValueFactory(new PropertyValueFactory<MainTink, Long>("totalSum"));
//        retailPlace.setCellValueFactory(new PropertyValueFactory<MainTink, String>("retailPlace"));
//        localDateTime.setCellValueFactory(new PropertyValueFactory<MainTink, LocalDateTime>("localDateTime"));
//        dateTime.setCellValueFactory(new PropertyValueFactory<MainTink, Long>("dateTime"));
//        items.setCellValueFactory(new PropertyValueFactory<MainTink, List<TinkOff>>("items"));
//        items.setVisible(false);
//        dateTime.setVisible(false);
//
//
//    }
//}

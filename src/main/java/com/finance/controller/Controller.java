package com.finance.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import com.finance.MainApplication;
import com.finance.handler.MainHandler;
import com.finance.model.MainTink;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<MainTink> tableView;

    @FXML
    private TableColumn<MainTink, String> user;

    @FXML
    private TableColumn<MainTink, Long> totalSum;

    @FXML
    private TableColumn<MainTink, String> retailPlace;

    @FXML
    private TableColumn<MainTink, LocalDateTime> localDateTime;

    @FXML
    private Button addButton;

    @FXML
    private Button selectButton;

    @FXML
    private Label label;

    @FXML
    void onActionAddButton(ActionEvent event) {

        List<MainTink> list = MainHandler.openFile(MainApplication.getPrimaryStage());
        if (list != null) {
            ObservableList<MainTink> observableList = FXCollections.observableArrayList(list);
            tableView.getItems().addAll(observableList);
            label.setText("Всего файлов: " + observableList.size());
            tableView.isEditable();
            tableView.isTableMenuButtonVisible();
            System.out.println("Чтение");
            selectButton.setDisable(false);
        }
    }

    @FXML
    void onActionSelectButton(ActionEvent event) {
        ObservableList<MainTink> items = tableView.getItems();
        System.out.println(items);
    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        System.out.println(event.getSource());
    }

    @FXML
    void initialize() {
        tableView.setPlaceholder(new Label("Нет данных"));
//        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        user.setCellValueFactory(new PropertyValueFactory<MainTink, String>("user"));
//        totalSum.setCellValueFactory(new PropertyValueFactory<MainTink, Long>("totalSum"));
//        retailPlace.setCellValueFactory(new PropertyValueFactory<MainTink, String>("retailPlace"));
//        localDateTime.setCellValueFactory(new PropertyValueFactory<MainTink, LocalDateTime>("localDateTime"));
    }
}

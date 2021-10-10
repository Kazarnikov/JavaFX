package com.finance.controller;

import com.finance.model.TinkOff;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class Controllers {
    ObservableList<TinkOff> observableList;
    boolean isTabMain = false;
    boolean isTabDiagramma = false;

    @FXML
    ResourceBundle resources;

    @FXML
    URL location;

    @FXML
    AnchorPane anchorPane;

    @FXML
    TabPane tabPanel;

    @FXML
    Tab tabMain;

    @FXML
    AnchorPane anchorPaneTabMain;

    @FXML
    TableView<TinkOff> tableView;

    @FXML
    TableColumn<TinkOff, String> name;

    @FXML
    TableColumn<TinkOff, LocalDateTime> dateTime;

    @FXML
    TableColumn<TinkOff, String> category;

    @FXML
    TableColumn<TinkOff, String> sum;

    @FXML
    Tab tabDiagramma;

    @FXML
    AnchorPane anchorPaneTabDiagramma;

    @FXML
    PieChart pieChartDiagramma;

    @FXML
    Label captionDiagramma;

    @FXML
    HBox month;

    @FXML
    ComboBox<String> comboBox;

    @FXML
    ListView<String> listView;

    @FXML
    Button selectFile;

    @FXML
    Button update;

    @FXML
    Button save;

    @FXML
    TextField searchTextField;

    @FXML
    Label label;
//
//    @FXML
//    void onEditCategory(ActionEvent event){};
//
//    @FXML
//    void onEditDate(ActionEvent event){};
//
//    @FXML
//    void onEditName(ActionEvent event){};
//
//    @FXML
//    void onEditSum(ActionEvent event){};
//
//    @FXML
//    void onSave(MouseEvent event){
//
//    };
//
//    @FXML
//    void onSearchText(KeyEvent event){};
//
//    @FXML
//    void onSelectCategoryDiagramma(MouseEvent event){};
//
//    @FXML
//    void onSelectCategoryMainTable(MouseEvent event){};
//
//    @FXML
//    void onSelectFile(MouseEvent event){};
//
//    @FXML
//    void onUpdate(MouseEvent event){};
//
//    @FXML
//    void initialize(){};
}
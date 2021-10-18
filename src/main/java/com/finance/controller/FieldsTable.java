package com.finance.controller;

import com.finance.model.TinkOff;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

@SuppressWarnings("unused")
public class FieldsTable {
    @FXML
    protected TableColumn<TinkOff, String> dateOperation;
    @FXML
    protected TableColumn<TinkOff, String> datePayment;
    @FXML
    protected TableColumn<TinkOff, String> numberCard;
    @FXML
    protected TableColumn<TinkOff, String> status;
    @FXML
    protected TableColumn<TinkOff, String> transactionAmount;
    @FXML
    protected TableColumn<TinkOff, String> transactionCurrency;
    @FXML
    protected TableColumn<TinkOff, String> paymentAmount;
    @FXML
    protected TableColumn<TinkOff, String> paymentCurrency;
    @FXML
    protected TableColumn<TinkOff, String> cashback;
    @FXML
    protected TableColumn<TinkOff, String> category;
    @FXML
    protected TableColumn<TinkOff, String> mcc;
    @FXML
    protected TableColumn<TinkOff, String> description;
    @FXML
    protected TableColumn<TinkOff, String> bonuses;
    @FXML
    protected TableColumn<TinkOff, String> roundingInvestment;
    @FXML
    protected TableColumn<TinkOff, String> operationAmountRounding;
    @FXML
    protected TableColumn<TinkOff, String> price;
//----------------------------------------------------
    @FXML
    protected ResourceBundle resources;
    @FXML
    protected URL location;
    @FXML
    protected AnchorPane anchorPane;
    @FXML
    protected TabPane tabPanel;
    @FXML
    protected Tab tabMain;
    @FXML
    protected AnchorPane anchorPaneTabMain;
    @FXML
    protected TableView<TinkOff> tableView;
    @FXML
    protected Tab tabDiagramma;
    @FXML
    protected AnchorPane anchorPaneTabDiagramma;
    @FXML
    protected PieChart pieChartDiagramma;
    @FXML
    protected Label captionDiagramma;
    @FXML
    protected Button selectFile;
    @FXML
    protected Button update;
    @FXML
    protected Button save;
    @FXML
    protected Label label;
    @FXML
    protected TextField searchTextField;
    @FXML
    protected HBox month;
    @FXML
    protected ComboBox<String> comboBox;
    @FXML
    protected ListView<String> listView;
    @FXML
    protected DatePicker datePikerDiagrammaFrom;
    @FXML
    protected DatePicker datePikerDiagrammaTo;
}

package com.finance.controller;

import com.finance.model.TinkOff;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

@SuppressWarnings("unused")
abstract class FieldsTable {
    protected final String ALL = "ALL";
    protected final Set<String> categorySearch = new HashSet<>();
    protected final Set<String> monthSearch = new HashSet<>();
    protected ObservableList<TinkOff> observableList = null;
    protected boolean isTabMain = false;
    protected boolean isTabDiagramma = false;
    protected boolean isTransferCard = false;

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
    protected CheckComboBox<String> monthBox;
    @FXML
    protected CheckComboBox<String> categoryBox;
    @FXML
    protected ListView<String> listView;
    @FXML
    protected DatePicker datePikerDiagrammaFrom;
    @FXML
    protected DatePicker datePikerDiagrammaTo;
    @FXML
    protected MenuBar menuBarMain;
    @FXML
    protected MenuItem menuSetting;
    @FXML
    protected MenuItem menuExit;

    //-----MENU -----//
    @FXML
    protected abstract void onMenuSetting(ActionEvent event);

    @FXML
    public abstract void onSelectDate(Event event);

    @FXML
    public abstract void onSave(MouseEvent event);

    @FXML
    public abstract void onSelectFile(MouseEvent event);

    @FXML
    public abstract void onUpdate(MouseEvent event);

    @FXML
    public abstract void onSelectCategoryDiagramma(MouseEvent event);

    @FXML
    public abstract void onSelectCategoryMainTable(MouseEvent event);

    @FXML
    public abstract void onSearchText(KeyEvent event);

    @FXML
    protected abstract void getList();

    @FXML
    protected abstract <T> void getDiagrammaItems(T... filter);

    @FXML
    protected abstract void onMouseClicked(MouseEvent event);

    public abstract void initialize();
}

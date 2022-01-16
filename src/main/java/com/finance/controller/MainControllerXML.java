package com.finance.controller;

import com.finance.model.Transaction;
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

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

@SuppressWarnings("unused")
abstract class MainControllerXML {
    protected final String ALL = "ALL";
    protected final Set<String> categorySearch = new HashSet<>();
    protected final Set<String> monthSearch = new HashSet<>();
    protected ObservableList<Transaction> observableList = null;
    protected boolean isTransferCard = false;

    @FXML
    protected TableColumn<Transaction, LocalDateTime> dateOperation;
    @FXML
    protected TableColumn<Transaction, LocalDate> datePayment;
    @FXML
    protected TableColumn<Transaction, String> numberCard;
    @FXML
    protected TableColumn<Transaction, String> status;
    @FXML
    protected TableColumn<Transaction, BigDecimal> transactionAmount;
    @FXML
    protected TableColumn<Transaction, String> transactionCurrency;
    @FXML
    protected TableColumn<Transaction, BigDecimal> paymentAmount;
    @FXML
    protected TableColumn<Transaction, String> paymentCurrency;
    @FXML
    protected TableColumn<Transaction, BigDecimal> cashback;
    @FXML
    protected TableColumn<Transaction, String> category;
    @FXML
    protected TableColumn<Transaction, String> mcc;
    @FXML
    protected TableColumn<Transaction, String> description;
    @FXML
    protected TableColumn<Transaction, BigDecimal> bonuses;
    @FXML
    protected TableColumn<Transaction, BigDecimal> roundingInvestment;
    @FXML
    protected TableColumn<Transaction, BigDecimal> operationAmountRounding;
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
    protected TableView<Transaction> tableView;
    @FXML
    protected Tab tabDiagram;
    @FXML
    protected AnchorPane anchorPaneTabDiagram;
    @FXML
    protected PieChart pieChartDiagram;
    @FXML
    protected Label captionDiagram;
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
    protected TableView<Map.Entry<String, BigDecimal>> listView;
    @FXML
    protected TableColumn<Map.Entry<String, BigDecimal>, String> name;
    @FXML
    protected TableColumn<Map.Entry<String, BigDecimal>, BigDecimal> sum;
    @FXML
    protected DatePicker datePikerDiagramFrom;
    @FXML
    protected DatePicker datePikerDiagramTo;
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
    public abstract void onSelectCategoryDiagram(MouseEvent event);
    @FXML
    public abstract void onSelectCategoryMainTable(MouseEvent event);
    @FXML
    public abstract void onSearchText(KeyEvent event);
    @FXML
    protected abstract void getList();
    @FXML
    protected abstract <T> void getDiagramItems(T... filter);
    @FXML
    protected abstract void onMouseClicked(MouseEvent event);

    public abstract void initialize();
}

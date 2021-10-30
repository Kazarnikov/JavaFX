package com.finance.controller;

import com.finance.MainApplication;
import com.finance.controller.converter.BigDecimalStringConverter;
import com.finance.controller.converter.LocalDateStringConverter;
import com.finance.controller.converter.LocalDateTimeStringConverter;
import com.finance.convert.Structure;
import com.finance.handler.MyFileChooser;
import com.finance.model.Transaction;
import com.finance.storage.Storage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@SuppressWarnings("unused")
public class MainController extends MainControllerXML {
    //-----Menu Settings-----//
    @Override
    protected void onMenuSetting(ActionEvent event) {
    }
    //-----Menu Settings-----//

    //-----Buttons Main-----//
    @Override
    public void onSave(MouseEvent event) {
        System.err.println(pieChartDiagram.getData().size());
    }

    @Override
    public void onSelectFile(MouseEvent event) {
        if (isTabMain) {
            tabPanel.getTabs().add(tabMain);
            tabPanel.getSelectionModel().select(tabMain);
            isTabMain = false;
        }
        MyFileChooser.openFile();
        getList();
    }

    @Override
    public void onUpdate(MouseEvent event) {
        if (isTabDiagram) {
            tabPanel.getTabs().add(tabDiagram);
            isTabDiagram = false;
        }
        monthSearch.clear();
        categorySearch.clear();
        tabPanel.getSelectionModel().select(tabDiagram);
        categoryBox();
        monthBox();
    }
    //-----Buttons Main-----//

    //-----Filter Diagram-----//
    @Override
    public void onSelectDate(Event event) {
        LocalDate after = datePikerDiagramFrom.getValue();
        LocalDate before = datePikerDiagramTo.getValue();
        if (after != null && before != null) {
            LocalDateTime fromTime = after.atTime(0, 0, 0);
            LocalDateTime toTime = before.atTime(23, 59, 59);
            getDiagramItems(fromTime, toTime);
        }
    }

    private void monthBox() {
        monthBox.getItems().clear();
        List<String> list = observableList
                .stream()
                .map(e -> e.getDateOperation().getMonth().name())
                .distinct()
                .collect(Collectors.toList());
        Collections.reverse(list);
        monthBox.getItems().addAll(list);
        monthBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) c -> {
            monthSearch.clear();
            monthSearch.addAll(monthBox.getCheckModel().getCheckedItems());
            getDiagramItems();
        });
        getDiagramItems();
    }

    private void categoryBox() {
        categoryBox.getItems().clear();
        List<String> list = observableList
                .stream()
                .map(Transaction::getCategory)
                .distinct()
                .collect(Collectors.toList());
        Collections.reverse(list);
        categoryBox.getItems().addAll(list);
        categoryBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) c -> {
            categorySearch.clear();
            categorySearch.addAll(categoryBox.getCheckModel().getCheckedItems());
            getDiagramItems();
        });
    }
    //-----Filter Diagram-----//

    @Override
    public void onSelectCategoryDiagram(MouseEvent event) {
    }

    @Override
    public void onSelectCategoryMainTable(MouseEvent event) {
    }

    @Override
    protected void onMouseClicked(MouseEvent event) {
    }

    //-----Search-----//
    @Override
    public void onSearchText(KeyEvent event) {
        KeyCode enter = KeyCode.ENTER;
        List<Transaction> collect = observableList
                .stream()
                .filter(e -> e.getCategory().toLowerCase().contains(searchTextField.getText().toLowerCase())
                        || e.getDescription().toLowerCase().contains(searchTextField.getText().toLowerCase()))
                .collect(Collectors.toList());
        label.setText("Продуктов: " + collect.size());
        tableView.getItems().clear();
        tableView.getItems().addAll(FXCollections.observableArrayList(collect));
    }
    //-----Search-----//

    @Override
    protected void getList() {
        Set<Transaction> list = Storage.getList();
        if (!list.isEmpty()) {
            observableList = FXCollections.observableArrayList(list);
            tableView.getItems().clear();
            tableView.getItems().addAll(observableList);
            label.setText("Всего файлов: " + observableList.size());
        } else {
            label.setText("Список пустой!!!");
        }
        update.setDisable(tableView.getItems().isEmpty());
        save.setDisable(tableView.getItems().isEmpty());
    }


    @SafeVarargs
    @Override
    protected final <T> void getDiagramItems(T... filter) {
        pieChartDiagram.getData().clear();
        listView.getItems().clear();
        List<Transaction> filterListItems = observableList
                .stream()
//                .filter(tinkOff -> FilterSearch.filter(tinkOff, filter))
                //TODO CexBox for "Перевод с карты"
                .filter(e -> !e.getDescription().contains(Structure.TRANSFER_CARD.getValue()))
                .filter(i -> categorySearch.isEmpty() || categorySearch.contains(i.getCategory()))
                .filter(i -> monthSearch.isEmpty() || monthSearch.contains(i.getDateOperation().getMonth().name()))
                .filter(i -> !isTransferCard || !Structure.TRANSFER_CARD.getValue().contains(i.getDescription()))
                .collect(Collectors.toList());

        Function<Transaction, DoubleStream> sumDouble = transaction -> DoubleStream.of(transaction.getOperationAmountRounding().doubleValue());
        double sumPercent = filterListItems.stream().flatMapToDouble(sumDouble).sum();

        ToDoubleFunction<Transaction> doubleFunction = var -> var.getPaymentAmount().doubleValue();

        Map<String, Double> groupByName = filterListItems
                .stream()
                .collect(Collectors.groupingBy(Transaction::getDescription, Collectors.summingDouble(doubleFunction)));

        Map<String, Double> sortByValueMap = groupByName.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        listView.getItems().addAll(sortByValueMap.keySet());
//        Platform.runLater(() -> {
            pieChartDiagram.setData(FXCollections.observableArrayList());
            for (Map.Entry<String, Double> stringDoubleEntry : sortByValueMap.entrySet()) {
                pieChartDiagram.getData().add(
                        new PieChart.Data(stringDoubleEntry.getKey() + ": " + stringDoubleEntry.getValue(),
                                stringDoubleEntry.getValue() / sumPercent * 100));
            }
            label.setText("Продуктов: " + groupByName.size());
            pieChartDiagram.getData().forEach(data -> {
                data.getNode().addEventHandler(MouseEvent.ANY, e -> {
                    captionDiagram.setText(
                            String.format("%1$s %2$.2f", data.getName().split("-")[0], data.getPieValue()) + "%");
                });
            });
//        });
    }

    @Override
    public void initialize() {
        tabMain.setOnClosed(event -> isTabMain = true);
        tabDiagram.setOnClosed(event -> isTabDiagram = true);

        tabPanel.setTabDragPolicy(TabPane.TabDragPolicy.REORDER);
        tableView.setPlaceholder(new Label("Нет данных"));

        dateOperation.setCellValueFactory(new PropertyValueFactory<>("dateOperation"));
        dateOperation.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateTimeStringConverter()));
        dateOperation.setOnEditCommit((TableColumn.CellEditEvent<Transaction, LocalDateTime> newValue) -> {
            newValue.getTableView()
                    .getItems()
                    .get(newValue.getTablePosition().getRow())
                    .setDateOperation(newValue.getNewValue() != null ? newValue.getNewValue() : newValue.getOldValue());
            tableView.refresh();
        });

        datePayment.setCellValueFactory(new PropertyValueFactory<>("datePayment"));
        datePayment.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        datePayment.setOnEditCommit((TableColumn.CellEditEvent<Transaction, LocalDate> newValue) -> {
            newValue.getTableView()
                    .getItems()
                    .get(newValue.getTablePosition().getRow())
                    .setDatePayment(newValue.getNewValue() != null ? newValue.getNewValue() : newValue.getOldValue());
            tableView.refresh();
        });

        numberCard.setCellValueFactory(new PropertyValueFactory<>("numberCard"));
        numberCard.setCellFactory(TextFieldTableCell.forTableColumn());
        numberCard.setOnEditCommit((TableColumn.CellEditEvent<Transaction, String> newValue) -> {
            newValue.getTableView()
                    .getItems()
                    .get(newValue.getTablePosition().getRow())
                    .setNumberCard(newValue.getNewValue());
            tableView.refresh();
        });

        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        status.setCellFactory(TextFieldTableCell.forTableColumn());
        status.setOnEditCommit((TableColumn.CellEditEvent<Transaction, String> newValue) -> {
            newValue.getTableView()
                    .getItems()
                    .get(newValue.getTablePosition().getRow())
                    .setStatus(newValue.getNewValue());
            tableView.refresh();
        });

        transactionAmount.setCellValueFactory(new PropertyValueFactory<>("transactionAmount"));
        transactionAmount.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        transactionAmount.setOnEditCommit((TableColumn.CellEditEvent<Transaction, BigDecimal> newValue) -> {
            newValue.getTableView()
                    .getItems()
                    .get(newValue.getTablePosition().getRow())
                    .setTransactionAmount(newValue.getNewValue() != null ? newValue.getNewValue() : newValue.getOldValue());
            tableView.refresh();
        });

        transactionCurrency.setCellValueFactory(new PropertyValueFactory<>("transactionCurrency"));
        transactionCurrency.setCellFactory(TextFieldTableCell.forTableColumn());
        transactionCurrency.setOnEditCommit((TableColumn.CellEditEvent<Transaction, String> newValue) -> {
            newValue.getTableView()
                    .getItems()
                    .get(newValue.getTablePosition().getRow())
                    .setTransactionCurrency(newValue.getNewValue());
            tableView.refresh();
        });

        paymentAmount.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
        paymentAmount.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        paymentAmount.setOnEditCommit((TableColumn.CellEditEvent<Transaction, BigDecimal> newValue) -> {
            newValue.getTableView()
                    .getItems()
                    .get(newValue.getTablePosition().getRow())
                    .setPaymentAmount(newValue.getNewValue() != null ? newValue.getNewValue() : newValue.getOldValue());
            tableView.refresh();
        });

        paymentCurrency.setCellValueFactory(new PropertyValueFactory<>("paymentCurrency"));
        paymentCurrency.setCellFactory(TextFieldTableCell.forTableColumn());
        paymentCurrency.setOnEditCommit((TableColumn.CellEditEvent<Transaction, String> newValue) -> {
            newValue.getTableView()
                    .getItems()
                    .get(newValue.getTablePosition().getRow())
                    .setPaymentCurrency(newValue.getNewValue());
            tableView.refresh();
        });

        cashback.setCellValueFactory(new PropertyValueFactory<>("cashback"));
        cashback.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        cashback.setOnEditCommit((TableColumn.CellEditEvent<Transaction, BigDecimal> newValue) -> {
            newValue.getTableView()
                    .getItems()
                    .get(newValue.getTablePosition().getRow())
                    .setCashback(newValue.getNewValue() != null ? newValue.getNewValue() : newValue.getOldValue());
            tableView.refresh();
        });

        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        category.setCellFactory(TextFieldTableCell.forTableColumn());
        category.setOnEditCommit((TableColumn.CellEditEvent<Transaction, String> newValue) -> {
            newValue.getTableView()
                    .getItems()
                    .get(newValue.getTablePosition().getRow())
                    .setCategory(newValue.getNewValue());
            tableView.refresh();
        });

        mcc.setCellValueFactory(new PropertyValueFactory<>("mcc"));
        mcc.setCellFactory(TextFieldTableCell.forTableColumn());
        mcc.setOnEditCommit((TableColumn.CellEditEvent<Transaction, String> newValue) -> {
            newValue.getTableView()
                    .getItems()
                    .get(newValue.getTablePosition().getRow())
                    .setMcc(newValue.getNewValue());
            tableView.refresh();
        });

        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        description.setCellFactory(TextFieldTableCell.forTableColumn());
        description.setOnEditCommit((TableColumn.CellEditEvent<Transaction, String> newValue) -> {
            newValue.getTableView()
                    .getItems()
                    .get(newValue.getTablePosition().getRow())
                    .setDescription(newValue.getNewValue());
            tableView.refresh();
        });

        bonuses.setCellValueFactory(new PropertyValueFactory<>("bonuses"));
        bonuses.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        bonuses.setOnEditCommit((TableColumn.CellEditEvent<Transaction, BigDecimal> newValue) -> {
            newValue.getTableView()
                    .getItems()
                    .get(newValue.getTablePosition().getRow())
                    .setBonuses(newValue.getNewValue() != null ? newValue.getNewValue() : newValue.getOldValue());
            tableView.refresh();
        });

        roundingInvestment.setCellValueFactory(new PropertyValueFactory<>("roundingInvestment"));
        roundingInvestment.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        roundingInvestment.setOnEditCommit((TableColumn.CellEditEvent<Transaction, BigDecimal> newValue) -> {
            newValue.getTableView()
                    .getItems()
                    .get(newValue.getTablePosition().getRow())
                    .setRoundingInvestment(newValue.getNewValue() != null ? newValue.getNewValue() : newValue.getOldValue());
            tableView.refresh();
        });

        operationAmountRounding.setCellValueFactory(new PropertyValueFactory<>("operationAmountRounding"));
        operationAmountRounding.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        operationAmountRounding.setOnEditCommit((TableColumn.CellEditEvent<Transaction, BigDecimal> newValue) -> {
            newValue.getTableView()
                    .getItems()
                    .get(newValue.getTablePosition().getRow())
                    .setOperationAmountRounding(newValue.getNewValue() != null ? newValue.getNewValue() : newValue.getOldValue());
            tableView.refresh();
        });

        initMenuItem();
    }

    private void initMenuItem() {
        menuExit.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        menuExit.setOnAction(actionEvent -> System.exit(0));

        menuSetting.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        menuSetting.setOnAction(actionEvent -> {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("view-menu-setting.fxml"));
                Stage newWindow = new Stage();
                newWindow.setTitle("Second Stage");
                newWindow.setScene(new Scene(fxmlLoader.load()));
                Stage primaryStage = (Stage) menuBarMain.getScene().getWindow();
                newWindow.initOwner(primaryStage);
                newWindow.initModality(Modality.WINDOW_MODAL);
                newWindow.setResizable(false);
                newWindow.setX(primaryStage.getX() + 200);
                newWindow.setY(primaryStage.getY() + 100);
                newWindow.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

package com.finance.controller;

import com.finance.MainApplication;
import com.finance.enums.FilterSearch;
import com.finance.handler.MainHandler;
import com.finance.model.TinkOff;
import com.finance.storage.StorageOff;
import com.finance.utils.FormatUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@SuppressWarnings("unused")
public class ControllerOff extends FieldsTable {

    private final String REX_SUM = "(-?\\d{2,10}[,.]?\\d{0,2})";
    ObservableList<TinkOff> observableList = null;
    private boolean isTabMain = false;
    private boolean isTabDiagramma = false;

    @FXML
    private void onSelectDate(Event event) {
        LocalDate after = datePikerDiagrammaFrom.getValue();
        LocalDate before = datePikerDiagrammaTo.getValue();
        if (after != null && before != null) {
            LocalDateTime fromTime = after.atTime(0, 0, 0);
            LocalDateTime toTime = before.atTime(23, 59, 59);
            getDiagrammaItems(fromTime, toTime);
        }
    }

    @FXML
    public void onSave(MouseEvent event) {
        System.err.println(pieChartDiagramma.getData().size());
    }

    @FXML
    void onSelectFile(MouseEvent event) {
        if (isTabMain) {
            tabPanel.getTabs().add(tabMain);
            tabPanel.getSelectionModel().select(tabMain);
            isTabMain = false;
        }
        MainHandler.openFile(MainApplication.getPrimaryStage());
        getList();
    }

    @FXML
    void onUpdate(MouseEvent event) {
        comboBox.getItems().clear();
        if (isTabDiagramma) {
            tabPanel.getTabs().add(tabDiagramma);
            isTabDiagramma = false;
        }

        tabPanel.getSelectionModel().select(tabDiagramma);

        List<String> list = observableList
                .stream()
                .map(e -> e.getDateOperation()
                        .getMonth()
                        .name())
                .distinct()
                .collect(Collectors.toList());
        list.add("ALL");
        Collections.reverse(list);
        comboBox.getItems().addAll(list);
        comboBox.getSelectionModel().select(0);
        comboBox.valueProperty().addListener((observable, oldValue, newValue) ->
                getDiagrammaItems(newValue.equals("ALL") ? "" : newValue));
        getDiagrammaItems("");
    }

    @FXML
    public void onSelectCategoryDiagramma(MouseEvent event) {
    }

    @FXML
    public void onSelectCategoryMainTable(MouseEvent event) {
    }


    @FXML
    public void onSearchText(KeyEvent event) {
        KeyCode enter = KeyCode.ENTER;
        List<TinkOff> collect = observableList
                .stream()
                .filter(e -> e.getCategory().toLowerCase().contains(searchTextField.getText().toLowerCase())
                        || e.getName().toLowerCase().contains(searchTextField.getText().toLowerCase()))
                .collect(Collectors.toList());
        label.setText("Продуктов: " + collect.size());
        tableView.getItems().clear();
        tableView.getItems().addAll(FXCollections.observableArrayList(collect));
    }

    @FXML
    private void getList() {
        List<TinkOff> list = StorageOff.getList();
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
    @FXML
    private <T> void getDiagrammaItems(T... filter) {
        pieChartDiagramma.getData().clear();
        List<TinkOff> filterListItems = observableList;
        if (filter[0] != "") {
            filterListItems = observableList
                    .stream()
                    .filter(tinkOff -> FilterSearch.filter(tinkOff, filter))
                    .collect(Collectors.toList());
        }
        Function<TinkOff, DoubleStream> sumDouble = tinkOff -> DoubleStream.of(FormatUtil.stringToNumber(tinkOff.getOperationAmountRounding()));
        double sumPercent = filterListItems.stream().flatMapToDouble(sumDouble).sum();

        ToDoubleFunction<TinkOff> doubleFunction = value -> FormatUtil.stringToNumber(value.getOperationAmountRounding());
        //TODO CexBox for "Перевод с карты"
        Map<String, Double> groupByName = filterListItems.stream().filter(e -> !e.getName().contains("Перевод с карты")).collect(
                Collectors.groupingBy(TinkOff::getName, Collectors.summingDouble(doubleFunction)));

        Map<String, Double> sortByValueMap = groupByName.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        listView.getItems().clear();
        listView.getItems().addAll(sortByValueMap.keySet());
        for (Map.Entry<String, Double> stringDoubleEntry : sortByValueMap.entrySet()) {
            pieChartDiagramma.getData().add(
                    new PieChart.Data(stringDoubleEntry.getKey() + ": " + FormatUtil.numberToString(stringDoubleEntry.getValue()),
                            stringDoubleEntry.getValue() / sumPercent * 100));
        }
        label.setText("Продуктов: " + groupByName.size());
        pieChartDiagramma.getData().forEach(data -> {
            data.getNode().addEventHandler(MouseEvent.ANY, e -> {
                captionDiagramma.setText(
                        String.format("%1$s %2$.2f", data.getName().split("-")[0], data.getPieValue()) + "%");
            });
        });
    }

    public void initialize() {
        pieChartDiagramma.setClockwise(true);

        tabMain.setOnClosed(event -> isTabMain = true);
        tabDiagramma.setOnClosed(event -> isTabDiagramma = true);

        tabPanel.setTabDragPolicy(TabPane.TabDragPolicy.REORDER);
        tableView.setPlaceholder(new Label("Нет данных"));

        dateOperation.setCellValueFactory(new PropertyValueFactory<>("dateOperation"));
        dateOperation.setCellFactory(TextFieldTableCell.forTableColumn());
        dateOperation.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> t) ->
                (t.getTableView().getItems()
                        .get(t.getTablePosition().getRow()))
                        .setDateOperation(t.getNewValue())
        );

        datePayment.setCellValueFactory(new PropertyValueFactory<>("datePayment"));
        datePayment.setCellFactory(TextFieldTableCell.forTableColumn());
        datePayment.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> t) ->
                (t.getTableView().getItems()
                        .get(t.getTablePosition().getRow()))
                        .setDateOperation(t.getNewValue())
        );

        numberCard.setCellValueFactory(new PropertyValueFactory<>("numberCard"));
        numberCard.setCellFactory(TextFieldTableCell.forTableColumn());
        numberCard.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> t) ->
                (t.getTableView().getItems()
                        .get(t.getTablePosition().getRow()))
                        .setDateOperation(t.getNewValue())
        );

        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        status.setCellFactory(TextFieldTableCell.forTableColumn());
        status.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> t) ->
                (t.getTableView().getItems()
                        .get(t.getTablePosition().getRow()))
                        .setDateOperation(t.getNewValue())
        );

        transactionAmount.setCellValueFactory(new PropertyValueFactory<>("transactionAmount"));
        transactionAmount.setCellFactory(TextFieldTableCell.forTableColumn());
        transactionAmount.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> t) ->
                (t.getTableView().getItems()
                        .get(t.getTablePosition().getRow()))
                        .setDateOperation(t.getNewValue())
        );

        transactionCurrency.setCellValueFactory(new PropertyValueFactory<>("transactionCurrency"));
        transactionCurrency.setCellFactory(TextFieldTableCell.forTableColumn());
        transactionCurrency.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> t) ->
                (t.getTableView().getItems()
                        .get(t.getTablePosition().getRow()))
                        .setDateOperation(t.getNewValue())
        );

        paymentAmount.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
        paymentAmount.setCellFactory(TextFieldTableCell.forTableColumn());
        paymentAmount.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> t) ->
                (t.getTableView().getItems()
                        .get(t.getTablePosition().getRow()))
                        .setDateOperation(t.getNewValue())
        );

        paymentCurrency.setCellValueFactory(new PropertyValueFactory<>("paymentCurrency"));
        paymentCurrency.setCellFactory(TextFieldTableCell.forTableColumn());
        paymentCurrency.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> t) ->
                (t.getTableView().getItems()
                        .get(t.getTablePosition().getRow()))
                        .setDateOperation(t.getNewValue())
        );

        cashback.setCellValueFactory(new PropertyValueFactory<>("cashback"));
        cashback.setCellFactory(TextFieldTableCell.forTableColumn());
        cashback.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> t) ->
                (t.getTableView().getItems()
                        .get(t.getTablePosition().getRow()))
                        .setDateOperation(t.getNewValue())
        );

        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        category.setCellFactory(TextFieldTableCell.forTableColumn());
        category.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> t) ->
                (t.getTableView().getItems()
                        .get(t.getTablePosition().getRow()))
                        .setCategory(t.getNewValue())
        );

        mcc.setCellValueFactory(new PropertyValueFactory<>("mcc"));
        mcc.setCellFactory(TextFieldTableCell.forTableColumn());
        mcc.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> t) ->
                (t.getTableView().getItems()
                        .get(t.getTablePosition().getRow()))
                        .setCategory(t.getNewValue())
        );

        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        description.setCellFactory(TextFieldTableCell.forTableColumn());
        description.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> t) ->
                (t.getTableView().getItems()
                        .get(t.getTablePosition().getRow()))
                        .setName(t.getNewValue())
        );

        bonuses.setCellValueFactory(new PropertyValueFactory<>("bonuses"));
        bonuses.setCellFactory(TextFieldTableCell.forTableColumn());
        bonuses.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> t) ->
                (t.getTableView().getItems()
                        .get(t.getTablePosition().getRow()))
                        .setName(t.getNewValue())
        );

        roundingInvestment.setCellValueFactory(new PropertyValueFactory<>("roundingInvestment"));
        roundingInvestment.setCellFactory(TextFieldTableCell.forTableColumn());
        roundingInvestment.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> t) ->
                (t.getTableView().getItems()
                        .get(t.getTablePosition().getRow()))
                        .setName(t.getNewValue())
        );

        operationAmountRounding.setCellValueFactory(new PropertyValueFactory<>("operationAmountRounding"));
        operationAmountRounding.setCellFactory(TextFieldTableCell.forTableColumn());
        operationAmountRounding.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> t) ->
                (t.getTableView().getItems()
                        .get(t.getTablePosition().getRow()))
                        .setName(t.getNewValue())
        );

        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        price.setCellFactory(TextFieldTableCell.forTableColumn());
        price.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> t) -> {
            String value = t.getNewValue();
            TinkOff tinkOff = t.getTableView().getItems().get(t.getTablePosition().getRow());
            if (value.matches(REX_SUM)) {
                tinkOff.setOperationAmountRounding(value);
            } else {
                tableView.refresh();
            }
        });

/**
 *  dateTime.setCellValueFactory(new PropertyValueFactory<TinkOff, LocalDateTime>("dateTime"));
 * //       dateTime.setCellFactory((param) -> new CustomCellFactory());
 *       dateTime.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateTimeStringConverter()));
 *       dateTime.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, LocalDateTime> t) ->
 *               (t.getTableView().getItems()
 *                       .get(t.getTablePosition().getRow()))
 *                       .setDateTime(t.getNewValue())
 *       );
 */
    }
}

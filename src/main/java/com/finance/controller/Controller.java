package com.finance.controller;

import com.finance.MainApplication;
import com.finance.convert.Structure;
import com.finance.handler.MainHandler;
import com.finance.model.TinkOff;
import com.finance.storage.StorageOff;
import com.finance.utils.FormatUtil;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@SuppressWarnings("unused")
public class Controller extends FieldsTable {
    //-----MENU -----//
    @Override
    protected void onMenuSetting(ActionEvent event) {


    }


    @Override
    public void onSelectDate(Event event) {
        LocalDate after = datePikerDiagrammaFrom.getValue();
        LocalDate before = datePikerDiagrammaTo.getValue();
        if (after != null && before != null) {
            LocalDateTime fromTime = after.atTime(0, 0, 0);
            LocalDateTime toTime = before.atTime(23, 59, 59);
            getDiagrammaItems(fromTime, toTime);
        }
    }

    @Override
    public void onSave(MouseEvent event) {
        System.err.println(pieChartDiagramma.getData().size());
    }

    @Override
    public void onSelectFile(MouseEvent event) {
        if (isTabMain) {
            tabPanel.getTabs().add(tabMain);
            tabPanel.getSelectionModel().select(tabMain);
            isTabMain = false;
        }
        MainHandler.openFile(MainApplication.getPrimaryStage());
        getList();
    }

    @Override
    public void onUpdate(MouseEvent event) {
        if (isTabDiagramma) {
            tabPanel.getTabs().add(tabDiagramma);
            isTabDiagramma = false;
        }
        monthSearch.clear();
        categorySearch.clear();
        tabPanel.getSelectionModel().select(tabDiagramma);
        categoryBox();
        monthBox();
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
            getDiagrammaItems();
        });
        getDiagrammaItems();
    }

    private void categoryBox() {
        categoryBox.getItems().clear();
        List<String> list = observableList
                .stream()
                .map(TinkOff::getCategory)
                .distinct()
                .collect(Collectors.toList());
        Collections.reverse(list);
        categoryBox.getItems().addAll(list);
        categoryBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) c -> {
            categorySearch.clear();
            categorySearch.addAll(categoryBox.getCheckModel().getCheckedItems());
            getDiagrammaItems();
        });
    }

    @Override
    public void onSelectCategoryDiagramma(MouseEvent event) {
    }

    @Override
    public void onSelectCategoryMainTable(MouseEvent event) {
    }

    @Override
    protected void onMouseClicked(MouseEvent event) {
    }


    @Override
    public void onSearchText(KeyEvent event) {
        KeyCode enter = KeyCode.ENTER;
        List<TinkOff> collect = observableList
                .stream()
                .filter(e -> e.getCategory().toLowerCase().contains(searchTextField.getText().toLowerCase())
                        || e.getDescription().toLowerCase().contains(searchTextField.getText().toLowerCase()))
                .collect(Collectors.toList());
        label.setText("Продуктов: " + collect.size());
        tableView.getItems().clear();
        tableView.getItems().addAll(FXCollections.observableArrayList(collect));
    }

    @Override
    protected void getList() {
        Set<TinkOff> list = StorageOff.getList();
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
    protected final <T> void getDiagrammaItems(T... filter) {
        pieChartDiagramma.getData().clear();
        listView.getItems().clear();
        List<TinkOff> filterListItems = observableList
                .stream()
//                .filter(tinkOff -> FilterSearch.filter(tinkOff, filter))
                .filter(i -> categorySearch.isEmpty() || categorySearch.contains(i.getCategory()))
                .filter(i -> monthSearch.isEmpty() || monthSearch.contains(i.getDateOperation().getMonth().name()))
                .filter(i -> !isTransferCard || !Structure.TRANSFER_CARD.getValue().contains(i.getDescription()))

                .collect(Collectors.toList());

        Function<TinkOff, DoubleStream> sumDouble = tinkOff -> DoubleStream.of(FormatUtil.stringToNumber(tinkOff.getOperationAmountRounding()));
        double sumPercent = filterListItems.stream().flatMapToDouble(sumDouble).sum();

        ToDoubleFunction<TinkOff> doubleFunction = value -> FormatUtil.stringToNumber(value.getOperationAmountRounding());
        //TODO CexBox for "Перевод с карты"
        Map<String, Double> groupByName = filterListItems
                .stream().filter(e -> !e.getDescription().contains(Structure.TRANSFER_CARD.getValue()))
                .collect(Collectors.groupingBy(TinkOff::getDescription, Collectors.summingDouble(doubleFunction)));

        Map<String, Double> sortByValueMap = groupByName.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        listView.getItems().addAll(sortByValueMap.keySet());
        Platform.runLater(() -> {
            pieChartDiagramma.setData(FXCollections.observableArrayList());
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
        });
    }

    @Override
    public void initialize() {
        pieChartDiagramma.setClockwise(true);

        tabMain.setOnClosed(event -> isTabMain = true);
        tabDiagramma.setOnClosed(event -> isTabDiagramma = true);

        tabPanel.setTabDragPolicy(TabPane.TabDragPolicy.REORDER);
        tableView.setPlaceholder(new Label("Нет данных"));

        dateOperation.setCellValueFactory(new PropertyValueFactory<>("dateOperation"));
        dateOperation.setCellFactory(TextFieldTableCell.forTableColumn());
        dateOperation.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> newValue) -> {
            String value = newValue.getNewValue();
            TinkOff tinkOff = newValue.getTableView().getItems().get(newValue.getTablePosition().getRow());
            if (value.matches(FormatUtil.getRexDateTime())) {
                tinkOff.setDateOperation(value);
            }
            tableView.refresh();
        });

        datePayment.setCellValueFactory(new PropertyValueFactory<>("datePayment"));
        datePayment.setCellFactory(TextFieldTableCell.forTableColumn());
        datePayment.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> newValue) -> {
            String value = newValue.getNewValue();
            TinkOff tinkOff = newValue.getTableView().getItems().get(newValue.getTablePosition().getRow());
            if (value.matches(FormatUtil.getRexDate())) {
                tinkOff.setDatePayment(value);
            }
            tableView.refresh();
        });

        numberCard.setCellValueFactory(new PropertyValueFactory<>("numberCard"));
        numberCard.setCellFactory(TextFieldTableCell.forTableColumn());
        numberCard.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> newValue) -> {
            (newValue.getTableView().getItems()
                    .get(newValue.getTablePosition().getRow()))
                    .setNumberCard(newValue.getNewValue());
            tableView.refresh();
        });

        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        status.setCellFactory(TextFieldTableCell.forTableColumn());
        status.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> newValue) -> {
            (newValue.getTableView().getItems()
                    .get(newValue.getTablePosition().getRow()))
                    .setStatus(newValue.getNewValue());
            tableView.refresh();
        });

        transactionAmount.setCellValueFactory(new PropertyValueFactory<>("transactionAmount"));
        transactionAmount.setCellFactory(TextFieldTableCell.forTableColumn());
        transactionAmount.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> newValue) -> {
            String value = newValue.getNewValue();
            TinkOff tinkOff = newValue.getTableView().getItems().get(newValue.getTablePosition().getRow());
            if (value.matches(FormatUtil.getRexSum())) {
                tinkOff.setTransactionAmount(value.replaceAll(",", "."));
            }
            tableView.refresh();
        });

        transactionCurrency.setCellValueFactory(new PropertyValueFactory<>("transactionCurrency"));
        transactionCurrency.setCellFactory(TextFieldTableCell.forTableColumn());
        transactionCurrency.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> newValue) -> {
            (newValue.getTableView().getItems()
                    .get(newValue.getTablePosition().getRow()))
                    .setTransactionCurrency(newValue.getNewValue());
            tableView.refresh();
        });

        paymentAmount.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
        paymentAmount.setCellFactory(TextFieldTableCell.forTableColumn());
        paymentAmount.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> newValue) -> {
            String value = newValue.getNewValue();
            TinkOff tinkOff = newValue.getTableView().getItems().get(newValue.getTablePosition().getRow());
            if (value.matches(FormatUtil.getRexSum())) {
                tinkOff.setPaymentAmount(value.replaceAll(",", "."));
            }
            tableView.refresh();
        });

        paymentCurrency.setCellValueFactory(new PropertyValueFactory<>("paymentCurrency"));
        paymentCurrency.setCellFactory(TextFieldTableCell.forTableColumn());
        paymentCurrency.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> newValue) -> {
            (newValue.getTableView().getItems()
                    .get(newValue.getTablePosition().getRow()))
                    .setPaymentCurrency(newValue.getNewValue());
            tableView.refresh();
        });

        cashback.setCellValueFactory(new PropertyValueFactory<>("cashback"));
        cashback.setCellFactory(TextFieldTableCell.forTableColumn());
        cashback.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> newValue) -> {
            String value = newValue.getNewValue();
            TinkOff tinkOff = newValue.getTableView().getItems().get(newValue.getTablePosition().getRow());
            if (value.matches(FormatUtil.getRexSum())) {
                tinkOff.setCashback(value.replaceAll(",", "."));
            }
            tableView.refresh();
        });

        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        category.setCellFactory(TextFieldTableCell.forTableColumn());
        category.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> newValue) -> {
            (newValue.getTableView().getItems()
                    .get(newValue.getTablePosition().getRow()))
                    .setCategory(newValue.getNewValue());
            tableView.refresh();
        });

        mcc.setCellValueFactory(new PropertyValueFactory<>("mcc"));
        mcc.setCellFactory(TextFieldTableCell.forTableColumn());
        mcc.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> newValue) -> {
            (newValue.getTableView().getItems()
                    .get(newValue.getTablePosition().getRow()))
                    .setMcc(newValue.getNewValue());
            tableView.refresh();
        });

        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        description.setCellFactory(TextFieldTableCell.forTableColumn());
        description.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> newValue) -> {
            (newValue.getTableView().getItems()
                    .get(newValue.getTablePosition().getRow()))
                    .setDescription(newValue.getNewValue());
            tableView.refresh();
        });

        bonuses.setCellValueFactory(new PropertyValueFactory<>("bonuses"));
        bonuses.setCellFactory(TextFieldTableCell.forTableColumn());
        bonuses.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> newValue) -> {
            String value = newValue.getNewValue();
            TinkOff tinkOff = newValue.getTableView().getItems().get(newValue.getTablePosition().getRow());
            if (value.matches(FormatUtil.getRexSum())) {
                tinkOff.setBonuses(value.replaceAll(",", "."));
            }
            tableView.refresh();
        });

        roundingInvestment.setCellValueFactory(new PropertyValueFactory<>("roundingInvestment"));
        roundingInvestment.setCellFactory(TextFieldTableCell.forTableColumn());
        roundingInvestment.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> newValue) -> {
            String value = newValue.getNewValue();
            TinkOff tinkOff = newValue.getTableView().getItems().get(newValue.getTablePosition().getRow());
            if (value.matches(FormatUtil.getRexSum())) {
                tinkOff.setRoundingInvestment(value.replaceAll(",", "."));
            }
            tableView.refresh();
        });

        operationAmountRounding.setCellValueFactory(new PropertyValueFactory<>("operationAmountRounding"));
        operationAmountRounding.setCellFactory(TextFieldTableCell.forTableColumn());
        operationAmountRounding.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> newValue) -> {
            String value = newValue.getNewValue();
            TinkOff tinkOff = newValue.getTableView().getItems().get(newValue.getTablePosition().getRow());
            if (value.matches(FormatUtil.getRexSum())) {
                tinkOff.setOperationAmountRounding(value.replaceAll(",", "."));
            }
            tableView.refresh();
        });

        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        price.setCellFactory(TextFieldTableCell.forTableColumn());
        price.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> newValue) -> {
            String value = newValue.getNewValue();
            TinkOff tinkOff = newValue.getTableView().getItems().get(newValue.getTablePosition().getRow());
            if (value.matches(FormatUtil.getRexSum())) {
                tinkOff.setPrice(value.replaceAll(",", "."));
            }
            tableView.refresh();
        });
        initMenuItem();


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

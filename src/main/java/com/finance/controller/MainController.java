package com.finance.controller;

import com.finance.MainApplication;
import com.finance.controller.cellfactory.DateEditingCell;
import com.finance.controller.cellfactory.DateTameEditingCell;
import com.finance.controller.converter.BigDecimalStringConverter;
import com.finance.convert.JOSNParser;
import com.finance.convert.Structure;
import com.finance.handler.MyFileChooser;
import com.finance.l10n.Messages;
import com.finance.model.Transaction;
import com.finance.storage.Storage;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
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
       JOSNParser.convertJSON(observableList);
    }

    @Override
    public void onSelectFile(MouseEvent event) {
        if (!tabPanel.getTabs().contains(tabMain)) {
            tabPanel.getTabs().add(tabMain);
            tabPanel.getSelectionModel().select(tabMain);
        }
        MyFileChooser.openFile((Stage) tabPanel.getScene().getWindow());
        getList();
    }

    @Override
    public void onUpdate(MouseEvent event) {
         if (!tabPanel.getTabs().contains(tabDiagram)) {
            tabPanel.getTabs().add(tabDiagram);
            tabPanel.getSelectionModel().select(tabDiagram);
        }
        monthSearch.clear();
        categorySearch.clear();
        tabPanel.getSelectionModel().select(tabDiagram);
        searchBox();
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

    private void searchBox() {
        List<Month> monthBoxItem = new ArrayList<>();
        Set<String> categoryBoxItem = new HashSet<>();
        monthBox.getItems().clear();
        categoryBox.getItems().clear();
        observableList.forEach(transaction -> {
            if (!monthBoxItem.contains(transaction.getDateOperation().getMonth())){
                monthBoxItem.add(transaction.getDateOperation().getMonth());
            }
            categoryBoxItem.add(transaction.getCategory());
        });

        Collections.sort(monthBoxItem);
        monthBox.getItems().addAll(monthBoxItem.stream().map(Month::name).collect(Collectors.toList()));
        categoryBox.getItems().addAll(categoryBoxItem);

        monthBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) c -> {
            monthSearch.clear();
            monthSearch.addAll(monthBox.getCheckModel().getCheckedItems());
            getDiagramItems();
        });

        categoryBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) c -> {
            categorySearch.clear();
            categorySearch.addAll(categoryBox.getCheckModel().getCheckedItems());
            getDiagramItems();
        });

        getDiagramItems();
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
        label.setText(Messages.getResourceBundle().getString("main.tableView.label.diagram") + collect.size());
        tableView.getItems().clear();
        tableView.getItems().addAll(FXCollections.observableArrayList(collect));
    }
    //-----Search-----//

    //-----List in Table View-----//
    @Override
    protected void getList() {
        Set<Transaction> list = Storage.getList();
        if (!list.isEmpty()) {
            Set<Transaction> collect = list.stream()
                    .filter(transaction -> !Structure.STATUS_FAILED.getValue().equals(transaction.getStatus()))
                    .filter(transaction ->  !Structure.INVESTKOPILKA.getValue().equals(transaction.getDescription()))
                    .collect(Collectors.toSet());
            observableList = FXCollections.observableArrayList(collect);
            tableView.getItems().clear();
            tableView.getItems().addAll(observableList);
            label.setText(Messages.getResourceBundle().getString("main.tableView.label.countFile") + observableList.size());
            update.setDisable(tableView.getItems().isEmpty());
            save.setDisable(tableView.getItems().isEmpty());
        } else {
            label.setText(Messages.getResourceBundle().getString("main.tableView.label.tableIsEmpty"));
        }
    }
    //-----List in Table View-----//


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

        Function<Map.Entry<String, BigDecimal>, DoubleStream> sumDouble = transaction -> DoubleStream.of(transaction.getValue().doubleValue());


//        ToDoubleFunction<Transaction> doubleFunction = var -> var.getPaymentAmount().doubleValue();

        Map<String, BigDecimal> collect = filterListItems.stream()
                .collect(Collectors.groupingBy(Transaction::getDescription,
                        Collectors.reducing(BigDecimal.ZERO, Transaction::getTransactionAmount, BigDecimal::add)));

        Map<String, BigDecimal> sortByValueMap = collect.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        double sumPercent = collect.entrySet().stream().flatMapToDouble(sumDouble).sum();

        listView.getItems().addAll(sortByValueMap.entrySet());

//        listView.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
//            captionDiagram.setText((e.getPickResult().getIntersectedNode()).toString());
//
//        });


//        Platform.runLater(() -> {
//            pieChartDiagram.setData(FXCollections.observableArrayList());
            List<PieChart.Data> dataList = new LinkedList<>();
            for (Map.Entry<String, BigDecimal> stringDoubleEntry : sortByValueMap.entrySet()) {
                dataList.add(new PieChart.Data(stringDoubleEntry.getKey() + ": " + stringDoubleEntry.getValue(),
                        stringDoubleEntry.getValue().doubleValue() / sumPercent * 100));
            }

            pieChartDiagram.getData().addAll(dataList);

            label.setText(Messages.getResourceBundle().getString("main.tableView.label.diagram") + sortByValueMap.size());
            pieChartDiagram.getData().forEach(data -> {
                data.getNode().addEventHandler(MouseEvent.ANY, e -> {
                    captionDiagram.setText(
                            String.format("%1$s ~ %2$.2f", data.getName(), data.getPieValue()) + "%");
                });
            });
//        });
    }

    @Override
    public void initialize() {
        tabPanel.setTabDragPolicy(TabPane.TabDragPolicy.REORDER);
        tableView.setPlaceholder(new Label(Messages.getResourceBundle().getString("main.tableView.tableIsEmpty")));

        dateOperation.setCellValueFactory(new PropertyValueFactory<>("dateOperation"));
        dateOperation.setCellFactory(param -> new DateTameEditingCell());
        dateOperation.setOnEditCommit((TableColumn.CellEditEvent<Transaction, LocalDateTime> newValue) -> {
            newValue.getTableView()
                    .getItems()
                    .get(newValue.getTablePosition().getRow())
                    .setDateOperation(newValue.getNewValue());
            tableView.refresh();
        });

        datePayment.setCellValueFactory(new PropertyValueFactory<>("datePayment"));
        datePayment.setCellFactory(param -> new DateEditingCell());
        datePayment.setOnEditCommit((TableColumn.CellEditEvent<Transaction, LocalDate> newValue) -> {
                    newValue.getTableView()
                            .getItems()
                            .get(newValue.getTablePosition().getRow())
                            .setDatePayment(newValue.getNewValue());
            tableView.refresh();
                });
//        datePayment.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
//       datePayment.setCellFactory(ComboBoxTableCell.<Transaction, LocalDate>forTableColumn(LocalDate.now(), LocalDate.now().plusMonths(12)));

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

        name.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        sum.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue()));
    }

    private void initMenuItem() {
        menuExit.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        menuExit.setOnAction(actionEvent -> System.exit(0));
        menuSetting.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        menuSetting.setOnAction(actionEvent -> {
            Stage primaryStage = (Stage) menuBarMain.getScene().getWindow();
            Stage newWindow = MainApplication.newStageLayout("view-menu-setting.fxml", "main.file.setting.name", new Stage());
            newWindow.initOwner(primaryStage);
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.setResizable(false);
            newWindow.setX(primaryStage.getX() + 200);
            newWindow.setY(primaryStage.getY() + 100);
            newWindow.show();
        });
    }
}

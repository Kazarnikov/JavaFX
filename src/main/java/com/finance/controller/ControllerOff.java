package com.finance.controller;

import com.finance.MainApplication;
import com.finance.handler.MainHandler;
import com.finance.model.TinkOff;
import com.finance.storage.StorageOff;
import com.finance.utils.FormatUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.converter.LocalDateTimeStringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class ControllerOff {
    private final String REX_SUM = "(-?\\d{2,10}[,.]?\\d{0,2})";
    ObservableList<TinkOff> observableList = null;
    private boolean isTabMain = false;
    private boolean isTabDiagramma = false;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TabPane tabPanel;
    @FXML
    private Tab tabMain;
    @FXML
    private AnchorPane anchorPaneTabMain;
    @FXML
    private TableView<TinkOff> tableView;
    @FXML
    private TableColumn<TinkOff, String> name;
    @FXML
    private TableColumn<TinkOff, LocalDateTime> dateTime;
    @FXML
    private TableColumn<TinkOff, String> category;
    @FXML
    private TableColumn<TinkOff, String> sum;
    @FXML
    private Tab tabDiagramma;
    @FXML
    private AnchorPane anchorPaneTabDiagramma;
    @FXML
    private PieChart pieChartDiagramma;
    @FXML
    private Label captionDiagramma;
    @FXML
    private Button selectFile;
    @FXML
    private Button update;
    @FXML
    private Button save;
    @FXML
    private Label label;
    @FXML
    private TextField searchTextField;
    @FXML
    private HBox month;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private ListView<String> listView;

    @FXML
    private DatePicker datePikerDiagrammaFrom;

    @FXML
    private DatePicker datePikerDiagrammaTo;

    @FXML
    private void onSelectDate(Event event) {
        LocalDate from = datePikerDiagrammaFrom.getValue();
        LocalDate to = datePikerDiagrammaTo.getValue();
        if (from != null && to != null) {
            LocalDateTime fromTime = from.atTime(0, 0, 0);
            LocalDateTime toTime = to.atTime(0, 0, 0);
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
        if (isTabDiagramma) {
            tabPanel.getTabs().add(tabDiagramma);
            isTabDiagramma = false;
        }

        tabPanel.getSelectionModel().select(tabDiagramma);
        pieChartDiagramma.setLabelsVisible(false);

        List<String> list = observableList
                .stream()
                .map(e -> e.getDateTime()
                        .getMonth()
                        .name())
                .distinct()
                .collect(Collectors.toList());
        list.add("ALL");
        Collections.reverse(list);

        comboBox.getItems().addAll(list);
        comboBox.getSelectionModel().select(0);
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            getDiagrammaItems(newValue.equals("ALL") ? "" : newValue);
        });

        getDiagrammaItems("");
    }

    @FXML
    public void onSelectCategoryDiagramma(MouseEvent event) {
    }

    @FXML
    public void onSelectCategoryMainTable(MouseEvent event) {
    }


    public void initialize() {
        pieChartDiagramma.setClockwise(true);
        tabMain.setOnClosed(event -> {
            isTabMain = true;
        });

        tabDiagramma.setOnClosed(event -> {
            isTabDiagramma = true;
        });

        tabPanel.setTabDragPolicy(TabPane.TabDragPolicy.REORDER);
        tableView.setPlaceholder(new Label("Нет данных"));

        name.setCellValueFactory(new PropertyValueFactory<TinkOff, String>("name"));
        name.setCellFactory(TextFieldTableCell.<TinkOff>forTableColumn());
        name.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> t) ->
                (t.getTableView().getItems()
                        .get(t.getTablePosition().getRow()))
                        .setName(t.getNewValue())
        );

        sum.setCellValueFactory(new PropertyValueFactory<TinkOff, String>("sum"));
        sum.setCellFactory(TextFieldTableCell.forTableColumn());
        sum.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> t) -> {
            String value = t.getNewValue();
            TinkOff tinkOff = t.getTableView().getItems().get(t.getTablePosition().getRow());
            if (value.matches(REX_SUM)) {
                tinkOff.setSum(value);
            } else {
                tableView.refresh();
            }
//            String collect = chars.stream().filter(c -> Character.isDigit(c))
//            String collect = t.getNewValue()
//                    .chars().mapToObj(c -> (char) c)
//                    .filter(c -> Character.isDigit(c) || c.equals('-') || c.equals('.'))
//                    .map(String::valueOf)
//                    .collect(Collectors.joining());
        });


        category.setCellValueFactory(new PropertyValueFactory<TinkOff, String>("category"));
        category.setCellFactory(TextFieldTableCell.<TinkOff>forTableColumn());
        category.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> t) ->
                (t.getTableView().getItems()
                        .get(t.getTablePosition().getRow()))
                        .setCategory(t.getNewValue())
        );

        dateTime.setCellValueFactory(new PropertyValueFactory<TinkOff, LocalDateTime>("dateTime"));
//        dateTime.setCellFactory((param) -> new CustomCellFactory());
        dateTime.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateTimeStringConverter()));
        dateTime.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, LocalDateTime> t) ->
                (t.getTableView().getItems()
                        .get(t.getTablePosition().getRow()))
                        .setDateTime(t.getNewValue())
        );


    }

    //=====EDIT===

    @FXML
    public void onEditDate(ActionEvent event) {
        System.out.println("---");
    }

    @FXML
    public void onEditCategory(ActionEvent event) {
        System.out.println("---");
    }

    @FXML
    void onEditName(ActionEvent event) {
        System.out.println("---");
    }

    @FXML
    void onEditSum(ActionEvent event) {
        System.out.println("---");
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

    @FXML
    private <T> void getDiagrammaItems(T... filter) {
        pieChartDiagramma.getData().clear();
        System.out.println(filter);
        List<TinkOff> filterListItems = observableList;
        if (filter[0] instanceof String && filter[0] != "") {
            filterListItems = observableList
                    .stream()
                    .filter(e -> e.getDateTime().getMonth().name().equals(filter[0]))
                    .collect(Collectors.toList());
        }
        if (filter[0] instanceof LocalDateTime) {
            filterListItems = observableList
                    .stream()
                    .filter(e -> {
                        LocalDateTime dateTime = e.getDateTime();
                        return dateTime.isAfter((LocalDateTime) filter[0]) && dateTime.isBefore((LocalDateTime) filter[1]);
                    })
                    .collect(Collectors.toList());
        }
        Function<TinkOff, DoubleStream> sumDouble = tinkOff -> DoubleStream.of(FormatUtil.stringToNumber(tinkOff.getSum()));
        double sumPercent = filterListItems.stream().flatMapToDouble(sumDouble).sum();

        ToDoubleFunction<TinkOff> doubleFunction = value -> FormatUtil.stringToNumber(value.getSum());
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
//                    captionDiagramma.setTranslateX(e.getSceneX());
//                    captionDiagramma.setTranslateY(e.getSceneY());
                captionDiagramma.setText(
                        String.format("%1$s %2$.2f", data.getName().split("-")[0], data.getPieValue()) + "%");
            });
        });

    }
}

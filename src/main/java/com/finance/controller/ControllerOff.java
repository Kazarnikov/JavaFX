package com.finance.controller;

import com.finance.MainApplication;
import com.finance.handler.MainHandler;
import com.finance.model.TinkOff;
import com.finance.storage.StorageOff;
import com.finance.utils.FormatUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class ControllerOff {
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
    void onSave(MouseEvent event) {
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

        Set<String> collect = observableList
                .stream()
                .map(e -> e.getDateTime().getMonth().name())
                .collect(Collectors.toCollection(LinkedHashSet::new));
        List<String> list = new ArrayList<>(collect);
        Collections.reverse(list);

        for (String entries :list){
            Button button = new Button(entries);
            month.getChildren().add(button);
            button.setOnAction(event1 -> {
                String value = ((Button) event1.getTarget()).textProperty().getValue();
                getDiagrammaItems(value);
            });
        }

        getDiagrammaItems("");
    }

    @FXML
    void onSelectCategoryDiagramma(MouseEvent event) {
    }

    @FXML
    void onSelectCategoryMainTable(MouseEvent event) {
    }

    @FXML
    void initialize() {
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
        sum.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> t) ->
                (t.getTableView().getItems()
                        .get(t.getTablePosition().getRow()))
                        .setSum(t.getNewValue())
        );

        category.setCellValueFactory(new PropertyValueFactory<TinkOff, String>("category"));
        category.setCellFactory(TextFieldTableCell.<TinkOff>forTableColumn());
        category.setOnEditCommit((TableColumn.CellEditEvent<TinkOff, String> t) ->
                (t.getTableView().getItems()
                        .get(t.getTablePosition().getRow()))
                        .setCategory(t.getNewValue())
        );

        dateTime.setCellValueFactory(new PropertyValueFactory<TinkOff, LocalDateTime>("DateTime"));
    }

//    private List<MainTink> getLest() {
//        return StorageMain.getList();
//    }

    //=====EDIT===
    @FXML
    void onEditDate(ActionEvent event) {
        System.out.println("---");
    }

    @FXML
    void onEditCategory(ActionEvent event) {
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
    void onSearchText(KeyEvent event) {
        List<TinkOff> collect = observableList
                .stream()
                .filter(e -> e.getCategory().toLowerCase().contains(searchTextField.getText().toLowerCase())
                        || e.getName().toLowerCase().contains(searchTextField.getText().toLowerCase()))
                .collect(Collectors.toList());
        label.setText("Продуктов: " + collect.size());
        tableView.getItems().clear();
        tableView.getItems().addAll(FXCollections.observableArrayList(collect));
    }


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


    private void getDiagrammaItems(String filter) {
        pieChartDiagramma.getData().clear();
        List<TinkOff> filterListItems = observableList;
        if (!Objects.equals(filter, "")) {
            filterListItems = observableList
                    .stream()
                    .filter(e -> e.getDateTime().getMonth().name().equals(filter)).collect(Collectors.toList());
        }

        Function<TinkOff, DoubleStream> sumDouble = tinkOff -> DoubleStream.of(FormatUtil.stringToNumber(tinkOff.getSum()));

        double sumPercent = filterListItems.stream().flatMapToDouble(sumDouble).sum();
        System.out.println(sumPercent);

        ToDoubleFunction<TinkOff> doubleFunction = value -> FormatUtil.stringToNumber(value.getSum());

        Map<String, Double> groupByName = filterListItems.stream().collect(
                Collectors.groupingBy(TinkOff::getName, Collectors.summingDouble(doubleFunction)));

        Map<String, Double> sortedMap = groupByName.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        for (Map.Entry<String, Double> stringDoubleEntry : sortedMap.entrySet()) {
            pieChartDiagramma.getData().add(new PieChart.Data(stringDoubleEntry.getKey() + ": " + FormatUtil.numberToString(stringDoubleEntry.getValue()),
                    stringDoubleEntry.getValue() / sumPercent * 100 ));
        }
        label.setText("Продуктов: " + groupByName.size());
        pieChartDiagramma.getData().forEach(data -> {
            data.getNode().addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
//                    captionDiagramma.setTranslateX(e.getSceneX());
//                    captionDiagramma.setTranslateY(e.getSceneY());
                    captionDiagramma.setText(
                            String.format("%1$s %2$.2f", data.getName().split("-")[0], data.getPieValue()) + "%");
                }
            });
        });

    }
}

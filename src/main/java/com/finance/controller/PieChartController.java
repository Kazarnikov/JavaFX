//package com.finance.controller;
//
//import com.finance.model.MainTink;
//import com.finance.storage.Storage;
//import javafx.event.EventHandler;
//import javafx.fxml.FXML;
//import javafx.geometry.Side;
//import javafx.scene.chart.PieChart;
//import javafx.scene.control.Label;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.paint.Color;
//
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.ResourceBundle;
//import java.util.stream.Collectors;
//
//public class PieChartController {
//
//    @FXML
//    private ResourceBundle resources;
//
//    @FXML
//    private URL location;
//
//    @FXML
//    private AnchorPane anchorPane;
//
//    @FXML
//    private PieChart pieChart;
//
//    @FXML
//    private Label caption;
//
//    @FXML
//    void initialize() {
//        List<MainTink> list = Storage.getList();
//        List<MainTink> list1 = new ArrayList<>();
//        list.forEach(e -> {
//            boolean i = true;
//            for (MainTink mainTink : list1) {
//                if (e.getName().equals(mainTink.getName())) {
//                    mainTink.setTotalSum(mainTink.getTotalSum() + e.getTotalSum());
//                    i = false;
//                }
//            }
//            if (i) {
//                list1.add(e);
//            }
//
//        });
//
//        List<PieChart.Data> collect = list1.stream()
//                .map(e -> new PieChart.Data(e.getName(), e.getTotalSum()))
//                .collect(Collectors.toList());
//
//
//        pieChart.getData().addAll(collect);
//        pieChart.setLegendSide(Side.LEFT);
//
//        caption.setTextFill(Color.RED);
//
//
//        for (final PieChart.Data data : pieChart.getData()) {
//            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent e) {
//                    caption.setTranslateX(e.getSceneX());
//                    caption.setTranslateY(e.getSceneY());
//                    caption.setText(String.valueOf(data.getPieValue()));
//                    System.err.println(e.getSceneX() + "\n" +e.getSceneY());
//                }
//            });
//        }
//    }
//}

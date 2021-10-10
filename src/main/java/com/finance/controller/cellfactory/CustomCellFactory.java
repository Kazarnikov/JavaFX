//package com.finance.controller.cellfactory;
//
//import javafx.application.Platform;
//import javafx.scene.control.ContentDisplay;
//import javafx.scene.control.TextField;
//import javafx.scene.input.KeyCode;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Text;
//
//import java.time.LocalDateTime;
//
//public class CustomCellFactory extends javafx.scene.control.TableCell<com.finance.model.TinkOff, LocalDateTime> {
//
//    TextField textField = new TextField();
//    Text text = new Text();
//
//    public CustomCellFactory() {
//        textField.setOnKeyPressed(keyEvent -> {
//            if (keyEvent.getCode() == KeyCode.ENTER) {
//                commitEdit(textField.getText());
//            }
//        });
//    }
//
//    @Override
//    public void commitEdit(LocalDateTime newValue) {
//        super.commitEdit(newValue);
//        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
//    }
//
//    @Override
//    public void startEdit() {
//        super.startEdit();
//        if (!isEmpty()) {
//            setGraphic(textField);
//            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
//            textField.setText(text.getText());
//            Platform.runLater(() -> textField.requestFocus());
//        }
//    }
//
//    @Override
//    public void cancelEdit() {
//        super.cancelEdit();
//        setContentDisplay(ContentDisplay.TEXT_ONLY);
//    }
//
//    @Override
//    protected void updateItem(LocalDateTime item, boolean empty) {
//        super.updateItem(item, empty);
//
//        if (item == null || empty) {
//            setText(null);
//        } else {
//            if (item.equals("error")) {
//                text.setFill(Color.RED);
//            } else {
//                text.setFill(Color.BLACK);
//            }
//
//            text.setText(item);
//            setGraphic(text);
//        }
//    }
//}
//}

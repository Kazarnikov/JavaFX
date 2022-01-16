package com.finance.controller.cellfactory;

import com.finance.model.Transaction;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DateTameEditingCell extends TableCell<Transaction, LocalDateTime> {

    private DatePicker datePicker;

    public DateTameEditingCell() {
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createDatePicker();
            setText(null);
            setGraphic(datePicker);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setThisText();
        setGraphic(null);
    }

    @Override
    public void updateItem(LocalDateTime item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (datePicker != null) {
                    datePicker.setValue(getDate().toLocalDate());
                }
                setText(null);
                setGraphic(datePicker);
            } else {
                setThisText();
                setGraphic(null);
            }
        }
    }

    private void createDatePicker() {
        datePicker = new DatePicker(getDate().toLocalDate());
        datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        datePicker.setOnAction((e) -> {
            commitEdit(LocalDateTime.of(datePicker.getValue(), getDate().toLocalTime()));
        });
    }

    private LocalDateTime getDate() {
        return getItem() == null ? LocalDateTime.now() : getItem();
    }

    private void setThisText(){
       setText(getDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT)));
    }
}
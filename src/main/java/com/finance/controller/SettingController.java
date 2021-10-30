package com.finance.controller;

import javafx.stage.Stage;

public class SettingController extends SettingControllerXML {
    @Override
    void initialize() {
        buttonCancelSetting.setOnAction(actionEvent -> {
            Stage stage = (Stage) buttonCancelSetting.getScene().getWindow();
            stage.close();
        });
    }
}

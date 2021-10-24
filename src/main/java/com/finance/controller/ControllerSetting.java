package com.finance.controller;

import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerSetting extends ControllerSettingXML {
    @Override
    void initialize() {
        buttonCancelSetting.setOnAction(actionEvent -> {
            Stage stage = (Stage) buttonCancelSetting.getScene().getWindow();
            stage.close();
        });
    }
}

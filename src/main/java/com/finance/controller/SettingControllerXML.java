package com.finance.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

@SuppressWarnings("unused")
abstract class SettingControllerXML {
    @FXML
    protected URL location;
    @FXML
    protected Button buttonCancelSetting;
    @FXML
    ResourceBundle resources;
    @FXML
    void initialize() {
    }
}

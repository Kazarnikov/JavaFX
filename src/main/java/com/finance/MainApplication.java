package com.finance;

import com.finance.l10n.Messages;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        newStageLayout("view-main.fxml", "main.stage.name" ,stage).show();
    }

    public static Stage newStageLayout(String modelName, String titleName, Stage stage ) {
        try {
            ResourceBundle currentBundle = Messages.getResourceBundle();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(modelName), currentBundle);
            stage.setTitle(currentBundle.getString(titleName));
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stage;
    }
}
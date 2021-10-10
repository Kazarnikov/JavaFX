package com.finance;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) {
        MainApplication.primaryStage = primaryStage;
        initRootLayout();
    }

    public void initRootLayout() {
        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("view-v2.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("view-off.fxml"));
//            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
            primaryStage.setTitle("---------------");
            primaryStage.setScene(new Scene(fxmlLoader.load()));
            primaryStage.show();
            try {
                Image image = new Image(getClass().getResourceAsStream("/com/finance/icons/main.png"));
                primaryStage.getIcons().add(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
module main.com.finance {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
//    requires eu.hansolo.tilesfx;
    requires java.sql;
//    requires org.junit.jupiter.api;

//    exports org.imgscalr;
    exports com.finance;
    opens com.finance to javafx.fxml;
    exports com.finance.controller;
    opens com.finance.controller to javafx.fxml;
    exports com.finance.model;
    opens com.finance.model to javafx.fxml;
}
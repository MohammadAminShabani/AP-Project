module org.example.frontend {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    exports divar;

    exports divar.controller;

    opens divar.controller to javafx.fxml;

    opens divar.dto.request to com.fasterxml.jackson.databind;
    opens divar.dto.response to com.fasterxml.jackson.databind;
}
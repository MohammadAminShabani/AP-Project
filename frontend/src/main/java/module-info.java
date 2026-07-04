module org.example.frontend {
    requires javafx.controls;
    requires javafx.fxml;


    opens divar.frontend to javafx.fxml;
    exports divar.frontend;
}
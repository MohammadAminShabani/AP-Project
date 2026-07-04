module org.example.frontend {
    requires javafx.controls;
    requires javafx.fxml;


    opens divar to javafx.fxml;
    exports divar;
}
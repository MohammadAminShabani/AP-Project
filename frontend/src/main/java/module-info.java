module org.example.frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;

    opens divar to javafx.fxml;
    exports divar;
}
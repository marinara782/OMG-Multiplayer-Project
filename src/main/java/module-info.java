module org.example.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.gui to javafx.fxml;
    exports org.example.gui;
}
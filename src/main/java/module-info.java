module org.example.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;


    opens org.example.gui to javafx.fxml;
    exports org.example.gui;
}
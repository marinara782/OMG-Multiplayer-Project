module org.example.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens org.example.gui to javafx.fxml;
    exports org.example.gui;
}
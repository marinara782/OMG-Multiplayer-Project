module org.example.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires javafx.media;

    opens org.example to com.fasterxml.jackson.databind;


    opens org.example.gui to javafx.fxml;
    opens org.example.game.ticTacToe to javafx.fxml;
    opens org.example.game.checkers to javafx.fxml;
    opens org.example.game.connectFour to javafx.fxml;

    exports org.example.gui;
    exports org.example.game.ticTacToe to javafx.fxml;
    exports org.example.game.connectFour to javafx.fxml;
    exports org.example.game.checkers to javafx.fxml;
    exports org.example.authentication;
}
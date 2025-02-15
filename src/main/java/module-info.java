module DiceRealms {
    requires javafx.controls;
    requires javafx.media;
    requires javafx.base;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires java.desktop;

    opens game.gui to javafx.fxml;

    exports game.gui;
}
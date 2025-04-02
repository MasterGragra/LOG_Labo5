module application {
    requires javafx.controls;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.desktop;
    requires javafx.fxml;
    requires javafx.graphics;

    exports application;
    exports controleur;
    exports modele;
    exports vue;
    exports memento;
}
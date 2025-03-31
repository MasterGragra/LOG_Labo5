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

    opens application to javafx.fxml;
    exports application;

    exports controleur;
    opens controleur to javafx.fxml;
}
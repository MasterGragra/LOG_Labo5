package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationMain extends Application {
    @Override
    public void start(Stage stage) throws IOException{
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/application/Application_image_Perspective.fxml")
        );

        Parent root = loader.load(); // Charge d'abord le root

        stage.setScene(new Scene(root));
        stage.setTitle("Application Image Perspective");
        stage.show();

    }

    public static void main(String[] args) {

        launch();
    }
}
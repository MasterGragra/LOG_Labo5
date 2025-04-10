package application;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Point d'entrée de l'application JavaFX
 */
public class ApplicationMain extends Application {
    private ApplicationController controller;
    private GestionnaireInterface gestionnaireInterface;

    /**
     * Point d'entrée principal de l'application
     * @param args Arguments de la ligne de commande
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Méthode de démarrage JavaFX
     * @param primaryStage Fenêtre principale de l'application
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // Initialiser les composants principaux
            controller = new ApplicationController();
            gestionnaireInterface = new GestionnaireInterface();

            // Connecter le contrôleur à l'interface
            gestionnaireInterface.setController(controller);

            // Créer l'interface programmatiquement
            Parent root = controller.creerInterface();

            // Ajouter le menu en haut
            BorderPane borderPane = (BorderPane) root;
            VBox topContainer = new VBox();
            MenuBar menuBar = gestionnaireInterface.creerMenu(null);
            topContainer.getChildren().add(menuBar);

            // Si le haut contient déjà la vue fixe
            if (borderPane.getTop() != null) {
                topContainer.getChildren().add(borderPane.getTop());
            }

            borderPane.setTop(topContainer);

            // Ajouter le panneau principal
            Scene scene = new Scene(borderPane, 1200, 800);
            gestionnaireInterface.configurerRaccourcisClavier(scene);

            // Créer la scène et la fenêtre
            primaryStage.setTitle("Éditeur d'images");
            primaryStage.setScene(scene);
            primaryStage.show();

            // Charger une image de test
            controller.chargerImage("src/main/resources/anger.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode appelée lors de la fermeture de l'application
     */
    @Override
    public void stop() {
        try {
            super.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
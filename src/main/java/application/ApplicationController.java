package application;

import controleur.ImageController;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import memento.Sauvegarde;
import modele.Image;
import modele.Perspective;
import vue.VueAbstraite;
import vue.VuePrincipale;
import vue.VueSecondaire;
import vue.VueFixe;
import vue.VueInteractive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contrôleur principal de l'application
 */
public class ApplicationController {
    private List<Perspective> perspectives;
    private Map<String, VueAbstraite> vues;
    private ImageController imageController;
    private Sauvegarde sauvegarde;

    /**
     * Constructeur
     */
    public ApplicationController() {
        perspectives = new ArrayList<>();
        vues = new HashMap<>();
        sauvegarde = new Sauvegarde();

        initialiserModeles();
        initialiserVues();
    }

    /**
     * Initialise les modèles de l'application
     */
    public void initialiserModeles() {
        Image image = new Image();
        // Créer deux perspectives distinctes qui partagent la même image
        Perspective perspectivePrincipale = new Perspective(image);
        Perspective perspectiveSecondaire = new Perspective(image);

        // Ajouter les deux perspectives à la liste
        perspectives.add(perspectivePrincipale);
        perspectives.add(perspectiveSecondaire);
    }

    /**
     * Initialise les vues de l'application
     */
    public void initialiserVues() {
        if (perspectives.isEmpty()) {
            return;
        }

        // Utiliser des perspectives distinctes pour chaque vue
        Perspective perspectivePrincipale = perspectives.getFirst();
        Perspective perspectiveSecondaire = perspectives.get(1);

        VuePrincipale vuePrincipale = new VuePrincipale(perspectivePrincipale);
        VueFixe vueFixe = new VueFixe(perspectivePrincipale);
        VueSecondaire vueSecondaire = new VueSecondaire(perspectiveSecondaire);

        vues.put("principale", vuePrincipale);
        vues.put("miniature", vueFixe);
        vues.put("secondaire", vueSecondaire);
    }

    /**
     * Crée une interface utilisateur
     * @return Le panneau racine de l'interface.
     */
    public Parent creerInterface() {
        // Utiliser un BorderPane comme conteneur principal
        BorderPane root = new BorderPane();

        // Créer un HBox pour aligner les trois vues horizontalement
        HBox conteneurPrincipal = new HBox(5);
        conteneurPrincipal.setStyle("-fx-padding: 10;");
        conteneurPrincipal.setFillHeight(true);

        // Force le conteneur à prendre tout l'espace disponible
        conteneurPrincipal.prefWidthProperty().bind(root.widthProperty());
        conteneurPrincipal.prefHeightProperty().bind(root.heightProperty());

        // Créer des conteneurs pour chaque vue avec une taille fixe relative
        StackPane conteneurMiniature = new StackPane();
        StackPane conteneurPrincipale = new StackPane();
        StackPane conteneurSecondaire = new StackPane();

        // Définir des contraintes de répartition égale (1/3 de l'espace pour chaque conteneur)
        HBox.setHgrow(conteneurMiniature, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(conteneurPrincipale, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(conteneurSecondaire, javafx.scene.layout.Priority.ALWAYS);

        // Force la taille des conteneurs à être relative au conteneur parent
        double percentage = 1.0/3.0;  // Chaque panneau prend 1/3 de la largeur
        conteneurMiniature.prefWidthProperty().bind(conteneurPrincipal.widthProperty().multiply(percentage).subtract(10));
        conteneurPrincipale.prefWidthProperty().bind(conteneurPrincipal.widthProperty().multiply(percentage).subtract(10));
        conteneurSecondaire.prefWidthProperty().bind(conteneurPrincipal.widthProperty().multiply(percentage).subtract(10));

        // Bordures pour visualiser les conteneurs
        conteneurMiniature.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        conteneurPrincipale.setStyle("-fx-border-color: blue; -fx-border-width: 2px;");
        conteneurSecondaire.setStyle("-fx-border-color: green; -fx-border-width: 2px;");

        // Ajouter les vues dans leurs conteneurs respectifs
        if (vues.containsKey("miniature")) {
            Pane vueMiniature = vues.get("miniature").getPane();
            conteneurMiniature.getChildren().add(vueMiniature);
            vueMiniature.prefWidthProperty().bind(conteneurMiniature.widthProperty());
            vueMiniature.prefHeightProperty().bind(conteneurMiniature.heightProperty());
            // Forcer la vue à s'adapter à son conteneur
            vueMiniature.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        }

        if (vues.containsKey("principale")) {
            Pane vuePrincipale = vues.get("principale").getPane();
            conteneurPrincipale.getChildren().add(vuePrincipale);
            vuePrincipale.prefWidthProperty().bind(conteneurPrincipale.widthProperty());
            vuePrincipale.prefHeightProperty().bind(conteneurPrincipale.heightProperty());
            vuePrincipale.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        }

        if (vues.containsKey("secondaire")) {
            Pane vueSecondaire = vues.get("secondaire").getPane();
            conteneurSecondaire.getChildren().add(vueSecondaire);
            vueSecondaire.prefWidthProperty().bind(conteneurSecondaire.widthProperty());
            vueSecondaire.prefHeightProperty().bind(conteneurSecondaire.heightProperty());
            vueSecondaire.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        }

        // Ajouter les conteneurs au HBox principal
        conteneurPrincipal.getChildren().addAll(conteneurMiniature, conteneurPrincipale, conteneurSecondaire);

        // Placer le HBox au centre du BorderPane
        root.setCenter(conteneurPrincipal);

        // Créer un ImageController et associer les vues aux perspectives
        imageController = new ImageController();

        // Initialiser les collections de l'ImageController manuellement
        if (perspectives.isEmpty() || perspectives.get(0) == null) {
            // Assurer qu'il y a au moins une perspective
            initialiserModeles();
        }

        // Associer les perspectives aux vues
        Perspective perspectivePrincipale = perspectives.get(0);
        Perspective perspectiveSecondaire = perspectives.get(1);

        // Associer les contrôleurs souris aux vues interactives
        if (vues.get("principale") instanceof VueInteractive vuePrincipale) {
            imageController.configurerEvenementsSouris(conteneurPrincipale, perspectivePrincipale, vuePrincipale);
        }

        if (vues.get("secondaire") instanceof VueInteractive vueSecondaire) {
            imageController.configurerEvenementsSouris(conteneurSecondaire, perspectiveSecondaire, vueSecondaire);
        }

        // Forcer le rendu initial des vues
        Platform.runLater(() -> {
            for (VueAbstraite vue : vues.values()) {
                vue.renduInitial();
            }
        });

        return root;
    }

    /**
     * Charge une image dans le modèle
     * @param cheminImage Chemin de l'image à charger
     */
    public void chargerImage(String cheminImage) {
        if (!perspectives.isEmpty()) {
            Perspective perspective = perspectives.getFirst();
            perspective.getImage().chargerImage(cheminImage);
        }
    }

    /**
     * Retourne les perspectives
     * @return Liste des perspectives
     */
    public List<Perspective> getPerspectives() {
        return perspectives;
    }

    /**
     * Retourne les vues
     * @return Map des vues
     */
    public Map<String, VueAbstraite> getVues() {
        return vues;
    }
}
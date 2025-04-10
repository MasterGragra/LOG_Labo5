package application;

import commande.CommandManager;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import vue.VueAbstraite;

import java.io.File;

/**
 * Gestion de l'interface utilisateur (fenêtres, menus, etc.)
 */
public class GestionnaireInterface {
    private Stage stage;
    private Scene scene;
    private ApplicationController controller;

    /**
     * Crée une fenêtre avec le titre spécifié
     * @param titre Titre de la fenêtre
     * @return La fenêtre créée
     */
    public Stage creerFenetre(String titre) {
        stage = new Stage();
        stage.setTitle(titre);
        return stage;
    }

    /**
     * Crée un menu pour l'application
     * @param root Le conteneur racine où ajouter le menu
     * @return La barre de menu créée
     */
    public MenuBar creerMenu(Parent root) {
        MenuBar menuBar = new MenuBar();

        // Menu Fichier
        Menu menuFichier = new Menu("Fichier");
        MenuItem itemOuvrir = new MenuItem("Ouvrir");
        MenuItem itemSauvegarder = new MenuItem("Sauvegarder");
        MenuItem itemQuitter = new MenuItem("Quitter");

        itemOuvrir.setOnAction(event -> {
            // Ouvrir un sélecteur de fichier
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Ouvrir une sauvegarde");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier de sauvegarde", "*.dat"));
            File fichier = fileChooser.showOpenDialog(stage);
            if (fichier != null) {
                controller.chargerSauvegarde(fichier.getAbsolutePath());
            }
        });

        itemSauvegarder.setOnAction(event -> {
            // Ouvrir un sélecteur de fichier
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Sauvegarder l'état");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier de sauvegarde", "*.dat"));
            File fichier = fileChooser.showSaveDialog(stage);
            if (fichier != null) {
                controller.sauvegarderEtat(fichier.getAbsolutePath());
            }
        });
        menuFichier.getItems().addAll(itemOuvrir, itemSauvegarder, itemQuitter);

        // Menu Edition avec fonctionnalités Annuler/Refaire
        Menu menuEdition = new Menu("Édition");
        MenuItem itemAnnuler = new MenuItem("Undo");
        MenuItem itemRefaire = new MenuItem("Redo");

        // Récupérer l'instance du CommandManager
        CommandManager commandManager = CommandManager.getInstance();

        // Associer les actions aux éléments du menu
        itemAnnuler.setOnAction(event -> commandManager.undo());
        itemRefaire.setOnAction(event -> commandManager.redo());

        // Ajouter des raccourcis clavier
        itemAnnuler.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        itemRefaire.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));

        // Désactiver les éléments de menu quand aucune commande n'est disponible
        itemAnnuler.disableProperty().bind(commandManager.canUndoProperty().not());
        itemRefaire.disableProperty().bind(commandManager.canRedoProperty().not());

        // Ajouter les éléments au menu Édition
        menuEdition.getItems().addAll(itemAnnuler, itemRefaire);

        // Menu Affichage
        /*
        Menu menuAffichage = new Menu("Affichage");
        MenuItem itemZoom = new MenuItem("Mode Zoom");
        MenuItem itemTranslation = new MenuItem("Mode Translation");
        menuAffichage.getItems().addAll(itemZoom, itemTranslation);
        */

        // Ajouter les menus à la barre de menu
        menuBar.getMenus().addAll(menuFichier, menuEdition);

        // Si le root est un VBox, on peut y ajouter directement la barre de menu
        if (root instanceof VBox) {
            ((VBox) root).getChildren().addFirst(menuBar);
        }

        return menuBar;
    }

    /**
     * Ajoute une vue à un conteneur
     * @param conteneur Le conteneur où ajouter la vue
     * @param vue La vue à ajouter
     */
    public void ajouterVue(Pane conteneur, VueAbstraite vue) {
        if (conteneur != null && vue != null) {
            conteneur.getChildren().add(vue.getPane());
        }
    }

    /**
     * Configure les raccourcis clavier pour la scène
     * @param scene La scène à configurer
     */
    public void configurerRaccourcisClavier(Scene scene) {
        this.scene = scene;
        CommandManager commandManager = CommandManager.getInstance();

        // Raccourcis pour undo/redo fonctionnels au niveau global
        scene.setOnKeyPressed(event -> {
            if (new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN).match(event)) {
                if (commandManager.canUndo()) {
                    commandManager.undo();
                    event.consume();
                }
            } else if (new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN).match(event)) {
                if (commandManager.canRedo()) {
                    commandManager.redo();
                    event.consume();
                }
            }
        });

        System.out.println("Raccourcis clavier configurés globalement");
    }

    public void setController(ApplicationController controller) {
        this.controller = controller;
    }
}
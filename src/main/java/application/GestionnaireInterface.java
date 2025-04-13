package application;

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

import commande.CommandManager;
import commande.CommandeColler;
import commande.CommandeCopier;
import memento.PerspectiveMediator;
import modele.Perspective;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import java.util.Optional;

/**
 * Gestion de l'interface utilisateur (fenêtres, menus, etc.)
 */
public class GestionnaireInterface {
    private Stage stage;
    private Scene scene;
    private ApplicationController controller;

    private boolean copierZoom = true;
    private boolean copierCoords = true;
    private static GestionnaireInterface instance = new GestionnaireInterface();
    private Perspective perspectiveActive;

    public boolean getCopierZoom() {
        return copierZoom;
    }

    public boolean getCopierCoords() {
        return copierCoords;
    }
    public static GestionnaireInterface getInstance() {
        return instance;
    }

    public void setPerspectiveActive(Perspective perspective) {
        this.perspectiveActive = perspective;
    }

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
        MenuItem itemCharger = new MenuItem("Charger");
        MenuItem itemSauvegarder = new MenuItem("Sauvegarder");
        MenuItem itemQuitter = new MenuItem("Quitter");

        // Action pour ouvrir une image
        itemOuvrir.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Ouvrir une image");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif")
            );
            File fichier = fileChooser.showOpenDialog(stage);
            if (fichier != null) {
                controller.chargerImage(fichier.getAbsolutePath());
            }
        });

        // Action pour charger une sauvegarde
        itemCharger.setOnAction(event -> {
            // Ouvrir un sélecteur de fichier
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Ouvrir une sauvegarde");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier de sauvegarde", "*.dat"));
            File fichier = fileChooser.showOpenDialog(stage);
            if (fichier != null) {
                controller.chargerSauvegarde(fichier.getAbsolutePath());
            }
        });

        // Action pour sauvegarder l'état
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
        menuFichier.getItems().addAll(itemOuvrir, itemCharger, itemSauvegarder, itemQuitter);

        // Menu Edition avec fonctionnalités Annuler/Refaire
        Menu menuEdition = new Menu("Édition");
        MenuItem itemAnnuler = new MenuItem("Undo");
        MenuItem itemRefaire = new MenuItem("Redo");
        MenuItem itemParametresCopie = new MenuItem("Paramètres de copie");

        // Récupérer l'instance du CommandManager
        CommandManager commandManager = CommandManager.getInstance();

        // Associer les actions aux éléments du menu
        itemAnnuler.setOnAction(event -> commandManager.undo());
        itemRefaire.setOnAction(event -> commandManager.redo());
        itemParametresCopie.setOnAction(e -> afficherDialogueParametresCopie());

        // Ajouter des raccourcis clavier
        itemAnnuler.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        itemRefaire.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));

        // Désactiver les éléments de menu quand aucune commande n'est disponible
        itemAnnuler.disableProperty().bind(commandManager.canUndoProperty().not());
        itemRefaire.disableProperty().bind(commandManager.canRedoProperty().not());

        // Ajouter les éléments au menu Édition
        menuEdition.getItems().addAll(itemAnnuler, itemRefaire, itemParametresCopie);


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

    /**
     * Displays a dialog box for the copy options
     */
    public void afficherDialogueCopie(Perspective perspective) {
        CommandeCopier commande = new CommandeCopier(perspective, copierZoom, copierCoords);
        CommandManager.getInstance().executeCommand(commande);
    }

    public void afficherDialogueParametresCopie() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Paramètres de copie");

        CheckBox checkZoom = new CheckBox("Zoom");
        checkZoom.setSelected(copierZoom);
        CheckBox checkCoords = new CheckBox("Coordonnées");
        checkCoords.setSelected(copierCoords);

        VBox contenu = new VBox(10);
        contenu.getChildren().addAll(new Label("Choisir les éléments à copier par défaut:"), checkZoom, checkCoords);
        dialog.getDialogPane().setContent(contenu);

        ButtonType boutonOk = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(boutonOk, ButtonType.CANCEL);

        Optional<ButtonType> resultat = dialog.showAndWait();
        if (resultat.isPresent() && resultat.get() == boutonOk) {
            copierZoom = checkZoom.isSelected();
            copierCoords = checkCoords.isSelected();
        }
    }

    /**
     * Adds a contextual menu for copy paste
     */
    public void ajouterMenuCopierColler(Node node, Perspective perspective) {
        ContextMenu menu = new ContextMenu();

        MenuItem itemCopier = new MenuItem("Copier");
        MenuItem itemColler = new MenuItem("Coller");

        itemCopier.setOnAction(e -> afficherDialogueCopie(perspective));
        itemColler.setOnAction(e -> {
            CommandeColler commande = new CommandeColler(perspective);
            CommandManager.getInstance().executeCommand(commande);
        });

        node.setOnContextMenuRequested(event -> {
            itemColler.setDisable(PerspectiveMediator.isEmpty());
            menu.getItems().setAll(itemCopier, itemColler);
            menu.show(node, event.getScreenX(), event.getScreenY());
        });
    }

    /**
     * Adds the Ctrl+C and Ctrl+V shortcuts
     */
    public void ajouterRaccourcisClavier(Scene scene, Perspective perspective) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.C) {
                if (perspectiveActive != null) {
                    afficherDialogueCopie(perspectiveActive);
                }
                event.consume();
            } else if (event.isControlDown() && event.getCode() == KeyCode.V) {
                if (perspectiveActive != null && !PerspectiveMediator.isEmpty()) {
                    CommandeColler commande = new CommandeColler(perspectiveActive);
                    CommandManager.getInstance().executeCommand(commande);
                    event.consume();
                }
            }
        });
    }
}
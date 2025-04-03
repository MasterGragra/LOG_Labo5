package controleur;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import commande.CommandManager;
import modele.Perspective;
import vue.VueInteractive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contrôleur pour gérer les interactions avec les images
 */
public class ImageController {
//    // Références aux éléments FXML
//    @FXML private StackPane panneau1, panneau2, panneau3;
//    @FXML private ImageView imageComplete, image2, image3;

    // Listes pour stocker les perspectives et les vues
    private List<Perspective> perspectives = new ArrayList<>();
    private List<ImageView> imageViews = new ArrayList<>();

    // Listes pour stocker les panneaux et les vues interactives
    private List<StackPane> panes = new ArrayList<>();

    // Maps pour stocker les stratégies de contrôle de la souris et de zoom
    private Map<StackPane, ControleSouris> controleSourisHashMap = new HashMap<>();
    private Map<StackPane, ControleZoom> controleZooms = new HashMap<>();

    // Référence au gestionnaire de commandes
    private final CommandManager commandManager = CommandManager.getInstance();

//    /**
//     * Méthode d'initialisation appelée après le chargement du fichier FXML
//     */
//    @FXML
//    public void initialize() {
//        System.out.println("ImageController initialized");
//
//        // Initialiser les collections
//        imageViews.add(imageComplete);
//        imageViews.add(image2);
//        imageViews.add(image3);
//
//        // Ajouter les panneaux à la liste
//        panes.add(panneau1);
//        panes.add(panneau2);
//        panes.add(panneau3);
//    }

    /**
     * Configure les événements souris pour un panneau et sa perspective associée
     */
    public void configurerEvenementsSouris(StackPane pane, Perspective perspective, VueInteractive vue) {
        // Vérifier si le panneau, la perspective et la vue sont valides
        if (perspective == null || vue == null) {
            return;
        }

        // Créer les stratégies pour ce panneau avec la perspective associée
        ControleZoom controleZoom = new ControleZoom(perspective);
        ControleSouris controleTranslation = new ControleTranslation(perspective);

        // Associer le contrôleur directement à la vue
        vue.setControleSouris(controleTranslation);

        // Stocker les contrôleurs pour référence
        controleSourisHashMap.put(pane, controleTranslation);
        controleZooms.put(pane, controleZoom);

        // Configurer le panneau
        pane.setPickOnBounds(true);

        // Trouver l'ImageView associée au panneau (pour compatibilité)
        ImageView imageView = null;
        for (int i = 0; i < panes.size(); i++) {
            if (panes.get(i) == pane) {
                imageView = imageViews.get(i);
                break;
            }
        }

        // Configurer l'ImageView pour qu'il prenne en compte les événements de la souris
        if (imageView != null) {
            imageView.setPickOnBounds(true);
        }

        // Déléguer les événements directement au panneau et au contrôleur approprié
        pane.setOnMousePressed(event -> {
            vue.getControleSouris().gererEvenement(event, ControleSouris.TypeEvenement.CLIQUE);
            event.consume();
        });

        pane.setOnMouseDragged(event -> {
            vue.getControleSouris().gererEvenement(event, ControleSouris.TypeEvenement.DRAG);
            event.consume();
        });

        pane.setOnMouseReleased(event -> {
            vue.getControleSouris().gererEvenement(event, ControleSouris.TypeEvenement.RELACHE);
            event.consume();
        });

        pane.setOnScroll(event -> {
            System.out.println("Événement de défilement détecté: " + event.getDeltaY());
            controleZooms.get(pane).gererEvenement(event, ControleSouris.TypeEvenement.SCROLL);
            event.consume();
        });
    }

    /**
     * Lie une perspective à une vue
     * @param perspective La perspective à lier
     * @param imageView L'ImageView à synchroniser avec la perspective
     * @param vue La vue interactive associée
     */
    public void lierPerspectiveEtVue(Perspective perspective, ImageView imageView, VueInteractive vue) {
        // Vérifier si la perspective et l'ImageView sont valides
        if (perspective != null && imageView != null) {

            // Synchroniser les propriétés de position et d'échelle
            imageView.scaleXProperty().bind(perspective.facteurEchelleProperty());
            imageView.scaleYProperty().bind(perspective.facteurEchelleProperty());
            imageView.translateXProperty().bind(perspective.positionXProperty());
            imageView.translateYProperty().bind(perspective.positionYProperty());

            // Ajouter la perspective à la liste si elle n'y est pas déjà
            if (!perspectives.contains(perspective)) {
                perspectives.add(perspective);
            }

            // Trouver le panneau associé à cet ImageView et configurer les événements
            for (int i = 0; i < imageViews.size(); i++) {
                if (imageViews.get(i) == imageView) {
                    configurerEvenementsSouris(panes.get(i), perspective, vue);
                    break;
                }
            }
        }
    }
}
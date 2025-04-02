package vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import modele.Perspective;

/**
 * Vue principale interactive affichant l'image avec ses transformations.
 * Permet l'interaction utilisateur via les contrôles souris.
 */
public class VuePrincipale extends VueInteractive {

    private ImageView imageView;

    /**
     * Constructeur de la vue principale
     * @param perspective La perspective à observer et manipuler
     */
    public VuePrincipale(Perspective perspective) {
        super(perspective);

        // Créer l'ImageView
        imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        // Ajouter l'ImageView au panneau
        pane.getChildren().add(imageView);

        // Mettre à jour la taille du panneau quand sa taille change
        pane.widthProperty().addListener((obs, oldVal, newVal) -> dessiner());
        pane.heightProperty().addListener((obs, oldVal, newVal) -> dessiner());
    }

    /**
     * Dessine l'image dans la vue avec les transformations actuelles
     * de la perspective (position, zoom)
     */
    @Override
    protected void dessiner() {
        if (perspective == null || perspective.getImage() == null) {
            return;
        }

        // Récupérer l'image du modèle
        Image image = perspective.getImage().getJavaFXImage();
        if (image == null) {
            return;
        }

        // Définir l'image dans l'ImageView
        imageView.setImage(image);

        // Obtenir les dimensions du panneau
        double panelWidth = pane.getWidth();
        double panelHeight = pane.getHeight();

        if (panelWidth <= 0 || panelHeight <= 0) {
            return;
        }

        // Calculer la taille de base
        double imageWidth = image.getWidth();
        double imageHeight = image.getHeight();
        double baseScale;

        if (imageWidth / panelWidth > imageHeight / panelHeight) {
            baseScale = panelWidth / imageWidth;
        } else {
            baseScale = panelHeight / imageHeight;
        }

        // Appliquer le facteur d'échelle de la perspective
        double finalScale = baseScale * perspective.getFacteurEchelle();
        imageView.setFitWidth(imageWidth * finalScale);
        imageView.setFitHeight(imageHeight * finalScale);

        // Centrer l'image de base
        double centerX = (panelWidth - imageView.getFitWidth()) / 2;
        double centerY = (panelHeight - imageView.getFitHeight()) / 2;

        // Appliquer la translation de la perspective
        imageView.setLayoutX(centerX + perspective.getPositionX());
        imageView.setLayoutY(centerY + perspective.getPositionY());
    }

    /**
     * Retourne l'ImageView utilisée dans cette vue
     * @return L'ImageView
     */
    public ImageView getImageView() {
        return imageView;
    }
}
package vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import modele.Perspective;

/**
 * Vue miniature non interactive affichant l'image.
 */
public class VueFixe extends VueAbstraite {

    private ImageView imageView;

    /**
     * Constructeur de la vue miniature
     * @param perspective La perspective à observer
     */
    public VueFixe(Perspective perspective) {
        super(perspective);

        // Créer l'ImageView pour la miniature
        imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        // Ajouter l'ImageView au panneau
        pane.getChildren().add(imageView);

        // Supprimer les contraintes de taille fixe
        // Laisser le binding dans ApplicationController s'en charger
    }

    /**
     * Dessine l'image en utilisant la même logique que les autres vues
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

        // Calculer la taille optimale pour afficher l'image entière
        double panelWidth = pane.getWidth();
        double panelHeight = pane.getHeight();

        if (panelWidth <= 0 || panelHeight <= 0) {
            return;
        }

        // Redimensionner l'ImageView pour remplir le panneau tout en conservant le ratio
        double imageWidth = image.getWidth();
        double imageHeight = image.getHeight();
        double baseScale;

        if (imageWidth / panelWidth > imageHeight / panelHeight) {
            // Limité par la largeur
            baseScale = panelWidth / imageWidth;
        } else {
            // Limité par la hauteur
            baseScale = panelHeight / imageHeight;
        }

        // IGNORER le facteur d'échelle de la perspective - toujours utiliser l'échelle de base
        imageView.setFitWidth(imageWidth * baseScale);
        imageView.setFitHeight(imageHeight * baseScale);

        // Centrer l'image dans le panneau SANS appliquer la translation de la perspective
        double centerX = (panelWidth - imageView.getFitWidth()) / 2;
        double centerY = (panelHeight - imageView.getFitHeight()) / 2;

        // Positionner l'image au centre du panneau sans décalage
        imageView.setLayoutX(centerX);
        imageView.setLayoutY(centerY);
    }

    /**
     * Retourne l'ImageView utilisée par cette vue
     * @return L'ImageView de la miniature
     */
    public ImageView getImageView() {
        return imageView;
    }
}
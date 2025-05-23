package vue;

import application.GestionnaireInterface;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import modele.Perspective;

/**
 * Vue secondaire interactive offrant une visualisation alternative de l'image
 * avec ses propres contrôles d'interaction utilisateur.
 */
public class VueSecondaire extends VueInteractive {

    private ImageView imageView;

    /**
     * Constructeur de la vue secondaire
     * @param perspective La perspective à observer et manipuler
     */
    public VueSecondaire(Perspective perspective) {
        super(perspective);

        GestionnaireInterface.getInstance().ajouterMenuCopierColler(pane, this.perspective);

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

        // Appliquer le facteur d'échelle de la perspective
        double finalScale = baseScale * perspective.getFacteurEchelle();
        imageView.setFitWidth(imageWidth * finalScale);
        imageView.setFitHeight(imageHeight * finalScale);

        // Centrer l'image dans le panneau
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
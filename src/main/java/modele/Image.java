package modele;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import vue.Observer;

/**
 * Classe représentant une image
 * Implémente Subject dans le pattern Observer
 */
public class Image implements Subject {
    private String chemin; // Chemin du fichier image
    private javafx.scene.image.Image imageJavaFX; // Image JavaFX chargée
    private List<Observer> observers = new ArrayList<>(); // Liste des observateurs

    public Image() {
        // Constructeur par défaut
    }

    /**
     * Charge une image à partir d'un fichier
     * @param chemin le chemin du fichier image
     */
    public void chargerImage(String chemin) {
        this.chemin = chemin; // Mémorisation du chemin

        try {
            // Conversion du chemin en URL de fichier
            File file = new File(chemin);
            String fileUrl = file.toURI().toString();

            // Chargement de l'image avec JavaFX
            this.imageJavaFX = new javafx.scene.image.Image(fileUrl);

            // Notification des observateurs
            notifyObservers();
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'image: " + e.getMessage());
        }
    }

    /**
     * Retourne l'image JavaFX
     * @return l'image JavaFX
     */
    public javafx.scene.image.Image getJavaFXImage() {
        return imageJavaFX;
    }

    /**
     * Ajoute un observateur à la liste
     * @param observer l'observateur à ajouter
     */
    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /**
     * Retire un observateur de la liste
     * @param observer l'observateur à retirer
     */
    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifie tous les observateurs
     */
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}
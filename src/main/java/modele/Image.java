package modele;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import vue.Observer;

/**
 * Classe représentant une image
 * Implémente Subject dans le pattern Observer et Serializable pour la persistance
 */
public class Image implements Subject, Serializable {
    private static final long serialVersionUID = 1L;

    private String chemin; // Chemin du fichier image
    private transient javafx.scene.image.Image imageJavaFX; // Image JavaFX chargée (non sérialisable)
    private transient List<Observer> observers = new ArrayList<>(); // Liste des observateurs

    public Image() {
        // Constructeur par défaut
    }

    /**
     * Méthode de sérialisation personnalisée
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject(); // Sauvegarde uniquement le chemin
    }

    /**
     * Méthode de désérialisation personnalisée
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // Charge le chemin

        // Réinitialiser les collections transientes
        observers = new ArrayList<>();

        // Recharger l'image si un chemin est défini
        if (chemin != null && !chemin.isEmpty()) {
            try {
                File file = new File(chemin);
                if (file.exists()) {
                    String fileUrl = file.toURI().toString();
                    this.imageJavaFX = new javafx.scene.image.Image(fileUrl);
                }
            } catch (Exception e) {
                System.err.println("Erreur lors du rechargement de l'image: " + e.getMessage());
            }
        }
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
     * Retourne le chemin de l'image
     * @return le chemin de l'image
     */
    public String getChemin() {
        return chemin;
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
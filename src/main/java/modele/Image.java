package modele;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import vue.Observer;

/**
 * Classe représentant une image
 */
public class Image implements Subject {
    private String chemin; // Chemin du fichier image
    private BufferedImage image; // Image chargée
    private List<Observer> observers = new ArrayList<>(); // Liste des observateurs

    public Image() {
        // Constructeur par défaut
    }

    /**
     * Charge une image à partir d'un fichier
     * @param chemin le chemin du fichier image
     */
    public void chargerImage(String chemin) throws IOException {
        this.chemin = chemin; // Mémorisation du chemin
        this.image = ImageIO.read(new File(chemin)); // Chargement de l'image
        notifyObservers(); // Notification des observateurs
    }

    /**
     * Retourne le chemin du fichier image
     * @return le chemin du fichier image
     */
    public BufferedImage getBufferedImage() {
        return image;
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
            observer.update(this); // Valeur non utilisée ici
        }
    }
}
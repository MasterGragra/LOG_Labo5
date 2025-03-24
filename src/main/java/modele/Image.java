package modele;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import vue.Observer;

public class Image implements Subject {
    private String chemin;
    private BufferedImage image;
    private List<Observer> observers = new ArrayList<>();

    public Image() {
        // Constructeur par défaut
    }

    public void chargerImage(String chemin) throws IOException {
        this.chemin = chemin;
        this.image = ImageIO.read(new File(chemin));
        notifyObservers();
    }

    public BufferedImage getBufferedImage() {
        return image;
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(0); // Valeur non utilisée ici
        }
    }
}
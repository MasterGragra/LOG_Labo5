package memento;

import modele.Perspective;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui gère la sauvegarde et le chargement de perspectives
 * Utilise la sérialisation Java pour persister les objets
 */
public class Sauvegarde {

    /**
     * Sauvegarde une liste de perspectives dans un fichier
     * @param perspectives liste de perspectives à sauvegarder
     * @param fichier chemin du fichier de sauvegarde
     * @throws IOException en cas d'erreur d'écriture
     */
    public void sauvegarderEtat(List<Perspective> perspectives, String fichier) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(fichier))) {
            oos.writeObject(perspectives);
        }
    }

    /**
     * Charge une liste de perspectives depuis un fichier
     * @param fichier chemin du fichier de sauvegarde
     * @return liste de perspectives chargées
     * @throws IOException en cas d'erreur de lecture
     * @throws ClassNotFoundException si la classe sérialisée n'est pas trouvée
     */
    @SuppressWarnings("unchecked")
    public List<Perspective> chargerEtat(String fichier) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(fichier))) {
            return (List<Perspective>) ois.readObject();
        } catch (EOFException e) {
            // Fichier vide ou corrompu
            return new ArrayList<>();
        }
    }

    /**
     * Vérifie si un fichier de sauvegarde existe
     * @param fichier chemin du fichier à vérifier
     * @return true si le fichier existe, false sinon
     */
    public boolean fichierExiste(String fichier) {
        return new File(fichier).exists();
    }
}
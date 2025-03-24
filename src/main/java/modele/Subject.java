package modele;

import vue.Observer;

/*
    * Interface représentant un sujet observable
 */
public interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers();
}

package vue;

import modele.Subject;

// Interface Observateur
public interface Observer {
    void update(Subject parameter);
}
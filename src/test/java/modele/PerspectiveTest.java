package modele;

import memento.PerspectiveMemento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vue.Observer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe Perspective
 */
class PerspectiveTest {

    private Perspective perspective;
    private Image image;
    private TestObserver observer;

    /**
     * Initialisation des attributs
     */
    @BeforeEach
    void setUp() {
        image = new Image();
        perspective = new Perspective(image);
        observer = new TestObserver();
    }

    /**
     * Test du constructeur
     */
    @Test
    void constructeur_doitInitialiserLesAttributsCorrectement() {
        // Assert
        assertEquals(1.0, perspective.getFacteurEchelle());
        assertEquals(0, perspective.getPositionX());
        assertEquals(0, perspective.getPositionY());
        assertSame(image, perspective.getImage());
    }

    /**
     * Test de createMemento
     */
    @Test
    void createMemento_doitCreerUnMementoAvecLesBonnesValeurs() {
        // Arrange
        perspective.setFacteurEchelle(2.5);
        perspective.setPosition(100, 200);

        // Act
        PerspectiveMemento memento = perspective.createMemento();

        // Assert
        assertEquals(2.5, memento.getFacteurEchelle());
        assertEquals(100, memento.getPositionX());
        assertEquals(200, memento.getPositionY());
    }

    /**
     * Test de setMemento
     */
    @Test
    void setMemento_doitRestaurerLesValeurs() {
        // Arrange
        PerspectiveMemento memento = new PerspectiveMemento(2.5, 100, 200);

        // Act
        perspective.setMemento(memento);

        // Assert
        assertEquals(2.5, perspective.getFacteurEchelle());
        assertEquals(100, perspective.getPositionX());
        assertEquals(200, perspective.getPositionY());
    }

    /**
     * Test de la méthode attach
     */
    @Test
    void attach_doitAjouterUnObservateur() {
        // Arrange
        perspective.attach(observer);
        observer.resetStats();

        // Act
        perspective.setFacteurEchelle(2.0);

        // Assert
        assertEquals(1, observer.getNotificationCount());
        assertEquals(perspective, observer.getLastSubject());
    }

    /**
     * Test que la méthode attach ignore les doublons
     */
    @Test
    void attach_doitIgnorerUnObservateurDejaPresent() {
        // Arrange
        perspective.attach(observer);
        perspective.attach(observer); // Doublon

        // Act
        observer.resetStats();
        perspective.setFacteurEchelle(2.0);

        // Assert
        assertEquals(1, observer.getNotificationCount());
    }

    /**
     * Test de la méthode detach
     */
    @Test
    void detach_doitSupprimerUnObservateur() {
        // Arrange
        perspective.attach(observer);
        perspective.detach(observer);

        // Act
        observer.resetStats();
        perspective.setFacteurEchelle(2.0);

        // Assert
        assertEquals(0, observer.getNotificationCount());
    }

    /**
     * Test de la méthode setFacteurEchelle
     */
    @Test
    void setFacteurEchelle_doitModifierLaValeurEtNotifier() {
        // Arrange
        perspective.attach(observer);
        observer.resetStats();

        // Act
        perspective.setFacteurEchelle(2.5);

        // Assert
        assertEquals(2.5, perspective.getFacteurEchelle());
        assertEquals(1, observer.getNotificationCount());
    }

    /**
     * Test de la méthode setPosition
     */
    @Test
    void setPosition_doitModifierLesValeursEtNotifier() {
        // Arrange
        perspective.attach(observer);
        observer.resetStats();

        // Act
        perspective.setPosition(150, 250);

        // Assert
        assertEquals(150, perspective.getPositionX());
        assertEquals(250, perspective.getPositionY());
        assertEquals(1, observer.getNotificationCount());
    }

    // Classe interne pour simuler un observateur
    private static class TestObserver implements Observer {
        private int notificationCount = 0;
        private Subject lastSubject = null;

        @Override
        public void update(Subject subject) {
            notificationCount++;
            lastSubject = subject;
        }

        public void resetStats() {
            notificationCount = 0;
            lastSubject = null;
        }

        public int getNotificationCount() {
            return notificationCount;
        }

        public Subject getLastSubject() {
            return lastSubject;
        }
    }
}
package memento;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PerspectiveMementoTest {

    /**
     * Test du constructeur
     */
    @Test
    void constructeurDoitInitialiserLesAttributsCorrectement() {
        // Arrange
        double facteurEchelle = 2.5;
        int positionX = 100;
        int positionY = 200;

        // Act
        PerspectiveMemento memento = new PerspectiveMemento(facteurEchelle, positionX, positionY);

        // Assert
        assertEquals(facteurEchelle, memento.getFacteurEchelle(),
                "Le facteur d'échelle devrait être correctement initialisé");
        assertEquals(positionX, memento.getPositionX(),
                "La position X devrait être correctement initialisée");
        assertEquals(positionY, memento.getPositionY(),
                "La position Y devrait être correctement initialisée");
    }

    /**
     * Test des getters
     */
    @Test
    void getFacteurEchelleDoitRetournerLaValeurInitialisee() {
        // Arrange
        double facteurEchelle = 1.75;
        PerspectiveMemento memento = new PerspectiveMemento(facteurEchelle, 0, 0);

        // Act & Assert
        assertEquals(facteurEchelle, memento.getFacteurEchelle(),
                "getFacteurEchelle devrait retourner la valeur initialisée");
    }

    /**
     * Test des getters
     */
    @Test
    void getPositionXDoitRetournerLaValeurInitialisee() {
        // Arrange
        int positionX = 150;
        PerspectiveMemento memento = new PerspectiveMemento(1.0, positionX, 0);

        // Act & Assert
        assertEquals(positionX, memento.getPositionX(),
                "getPositionX devrait retourner la valeur initialisée");
    }

    /**
     * Test des getters
     */
    @Test
    void getPositionYDoitRetournerLaValeurInitialisee() {
        // Arrange
        int positionY = 250;
        PerspectiveMemento memento = new PerspectiveMemento(1.0, 0, positionY);

        // Act & Assert
        assertEquals(positionY, memento.getPositionY(),
                "getPositionY devrait retourner la valeur initialisée");
    }
}
package modele;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import vue.Observer;

class ImageTest {

    private Image image;
    private MockObserver observer;

    @BeforeEach
    public void setUp() {
        image = new Image();
        observer = new MockObserver();
    }

    @Test
    public void testChargerImage() throws IOException {
        // Arrange
        String cheminTest = "src/test/resources/xd.png"; // Créez ce fichier

        // Act
        image.chargerImage(cheminTest);

        // Assert
        assertNotNull(image.getBufferedImage(), "L'image chargée ne devrait pas être null");
    }

    @Test
    public void testAttachDetach() {
        // Attach
        image.attach(observer);

        // Détection indirecte via notification
        try {
            image.chargerImage("src/test/resources/xd.png");
            assertTrue(observer.estNotifie(), "L'observateur aurait dû être notifié");
        } catch (IOException e) {
            fail("Exception lors du chargement de l'image");
        }

        // Detach
        observer.reinitialiser();
        image.detach(observer);

        try {
            image.chargerImage("src/test/resources/xd.png");
            assertFalse(observer.estNotifie(), "L'observateur ne devrait plus être notifié");        } catch (IOException e) {
            fail("Exception lors du chargement de l'image");
        }
    }

    // Classe interne pour simuler un observateur
    private static class MockObserver implements Observer {
        private boolean notifie = false;

        @Override
        public void update(float parametre) {
            notifie = true;
        }

        public boolean estNotifie() {
            return notifie;
        }

        public void reinitialiser() {
            notifie = false;
        }
    }
}
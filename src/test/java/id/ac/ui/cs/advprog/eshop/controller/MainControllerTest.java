package id.ac.ui.cs.advprog.eshop.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainControllerTest {

    private MainController mainController;

    @BeforeEach
    void setUp() {
        mainController = new MainController();
    }

    @Test
    void testMainPage() {
        String view = mainController.mainPage();
        assertEquals("homePage", view);
    }
}

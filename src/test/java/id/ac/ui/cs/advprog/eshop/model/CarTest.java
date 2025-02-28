package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setCarId("car-123");
        car.setCarName("Test Car");
        car.setCarColor("Blue");
        car.setCarQuantity(3);
    }

    @Test
    void testCarId() {
        assertEquals("car-123", car.getCarId());
    }

    @Test
    void testCarName() {
        assertEquals("Test Car", car.getCarName());
    }

    @Test
    void testCarColor() {
        assertEquals("Blue", car.getCarColor());
    }

    @Test
    void testCarQuantity() {
        assertEquals(3, car.getCarQuantity());
    }
}

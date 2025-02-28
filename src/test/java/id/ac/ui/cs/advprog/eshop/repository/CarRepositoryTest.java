package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CarRepositoryTest {

    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void testCreateCarWithNullId() {
        // Create a car with null ID; repository should assign a UUID.
        Car car = new Car();
        car.setCarName("Test Car");
        car.setCarColor("Blue");
        car.setCarQuantity(4);
        assertNull(car.getCarId());
        Car createdCar = carRepository.create(car);
        assertNotNull(createdCar.getCarId());
        assertFalse(createdCar.getCarId().isEmpty());
        Iterator<Car> iterator = carRepository.findAll();
        assertTrue(iterator.hasNext());
    }

    @Test
    void testCreateCarWithProvidedId() {
        // Create a car with a provided ID; it should remain unchanged.
        Car car = new Car();
        car.setCarId("custom-id");
        car.setCarName("Custom Car");
        car.setCarColor("Red");
        car.setCarQuantity(2);
        Car createdCar = carRepository.create(car);
        assertEquals("custom-id", createdCar.getCarId());
    }

    @Test
    void testFindAll() {
        // Initially, the repository should be empty.
        Iterator<Car> iterator = carRepository.findAll();
        assertFalse(iterator.hasNext());

        // Add a car and verify it's returned by findAll().
        Car car = new Car();
        car.setCarName("Test Car");
        car.setCarColor("Blue");
        car.setCarQuantity(4);
        carRepository.create(car);
        iterator = carRepository.findAll();
        assertTrue(iterator.hasNext());
        Car foundCar = iterator.next();
        assertEquals(car.getCarName(), foundCar.getCarName());
    }

    @Test
    void testFindById_Found() {
        Car car = new Car();
        car.setCarId("id-1");
        car.setCarName("Car One");
        carRepository.create(car);
        Optional<Car> result = carRepository.findById("id-1");
        assertTrue(result.isPresent());
        assertEquals("Car One", result.get().getCarName());
    }

    @Test
    void testFindById_NotFound() {
        Optional<Car> result = carRepository.findById("non-existent-id");
        assertFalse(result.isPresent());
    }

    @Test
    void testUpdateCar_Success() {
        // Create and add a car.
        Car car = new Car();
        car.setCarId("id-1");
        car.setCarName("Original Car");
        car.setCarColor("Black");
        car.setCarQuantity(1);
        carRepository.create(car);

        // Update the car.
        Car updatedCar = new Car();
        updatedCar.setCarId("id-1");
        updatedCar.setCarName("Updated Car");
        updatedCar.setCarColor("White");
        updatedCar.setCarQuantity(5);

        Optional<Car> resultOpt = carRepository.update(updatedCar);
        assertTrue(resultOpt.isPresent());
        Car updated = resultOpt.get();
        assertEquals("Updated Car", updated.getCarName());
        assertEquals("White", updated.getCarColor());
        assertEquals(5, updated.getCarQuantity());
    }

    @Test
    void testUpdateCar_Failure() {
        // Attempt to update a car that doesn't exist.
        Car updatedCar = new Car();
        updatedCar.setCarId("non-existent");
        updatedCar.setCarName("No Car");
        updatedCar.setCarColor("Grey");
        updatedCar.setCarQuantity(0);
        Optional<Car> resultOpt = carRepository.update(updatedCar);
        assertFalse(resultOpt.isPresent());
    }

    @Test
    void testUpdateCar_MultipleCars() {
        // Create two cars.
        Car car1 = new Car();
        car1.setCarId("id-1");
        car1.setCarName("Car One");
        car1.setCarColor("Blue");
        car1.setCarQuantity(1);
        carRepository.create(car1);

        Car car2 = new Car();
        car2.setCarId("id-2");
        car2.setCarName("Car Two");
        car2.setCarColor("Red");
        car2.setCarQuantity(2);
        carRepository.create(car2);

        // Update the second car.
        Car updatedCar2 = new Car();
        updatedCar2.setCarId("id-2");
        updatedCar2.setCarName("Car Two Updated");
        updatedCar2.setCarColor("Green");
        updatedCar2.setCarQuantity(5);

        Optional<Car> resultOpt = carRepository.update(updatedCar2);
        assertTrue(resultOpt.isPresent());
        Car updated = resultOpt.get();
        assertEquals("Car Two Updated", updated.getCarName());
        assertEquals("Green", updated.getCarColor());
        assertEquals(5, updated.getCarQuantity());

        // Verify that car1 remains unchanged.
        Optional<Car> car1Opt = carRepository.findById("id-1");
        assertTrue(car1Opt.isPresent());
        assertEquals("Car One", car1Opt.get().getCarName());
    }

    @Test
    void testDeleteCar() {
        Car car = new Car();
        car.setCarId("id-1");
        car.setCarName("Car to Delete");
        car.setCarColor("Green");
        car.setCarQuantity(3);
        carRepository.create(car);
        // Verify the car exists.
        Optional<Car> found = carRepository.findById("id-1");
        assertTrue(found.isPresent());
        // Delete the car.
        carRepository.delete("id-1");
        // Verify the car is removed.
        found = carRepository.findById("id-1");
        assertFalse(found.isPresent());
    }

    @Test
    void testDeleteCar_NotFound() {
        // Deleting a non-existent car should not throw an exception.
        assertDoesNotThrow(() -> carRepository.delete("non-existent"));
    }
}

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
    void testCreateAndFind() {
        Car car = new Car();
        car.setCarId("car-1");
        car.setCarName("Test Car 1");
        car.setCarColor("Red");
        car.setCarQuantity(5);
        carRepository.create(car);
        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
        Car savedCar = carIterator.next();
        assertEquals("car-1", savedCar.getCarId());
        assertEquals("Test Car 1", savedCar.getCarName());
        assertEquals("Red", savedCar.getCarColor());
        assertEquals(5, savedCar.getCarQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Car> carIterator = carRepository.findAll();
        assertFalse(carIterator.hasNext());
    }

    @Test
    void testFindById_Found() {
        Car car = new Car();
        car.setCarId("car-1");
        car.setCarName("Test Car");
        car.setCarColor("Green");
        car.setCarQuantity(2);
        carRepository.create(car);
        Optional<Car> foundOpt = carRepository.findById("car-1");
        assertTrue(foundOpt.isPresent());
        Car found = foundOpt.get();
        assertEquals("Test Car", found.getCarName());
    }

    @Test
    void testFindById_NotFound() {
        Optional<Car> foundOpt = carRepository.findById("non-existent");
        assertFalse(foundOpt.isPresent());
    }

    @Test
    void testUpdate_Success() {
        Car car = new Car();
        car.setCarId("car-1");
        car.setCarName("Original Car");
        car.setCarColor("Black");
        car.setCarQuantity(1);
        carRepository.create(car);
        Car updatedCar = new Car();
        updatedCar.setCarId("car-1");
        updatedCar.setCarName("Updated Car");
        updatedCar.setCarColor("White");
        updatedCar.setCarQuantity(10);
        Optional<Car> resultOpt = carRepository.update(updatedCar);
        assertTrue(resultOpt.isPresent());
        Car result = resultOpt.get();
        assertEquals("Updated Car", result.getCarName());
        assertEquals("White", result.getCarColor());
        assertEquals(10, result.getCarQuantity());
    }

    @Test
    void testUpdate_Failure() {
        Car updatedCar = new Car();
        updatedCar.setCarId("non-existent");
        updatedCar.setCarName("Non-existent");
        updatedCar.setCarColor("N/A");
        updatedCar.setCarQuantity(0);
        Optional<Car> resultOpt = carRepository.update(updatedCar);
        assertFalse(resultOpt.isPresent());
    }

    @Test
    void testDelete_Success() {
        Car car = new Car();
        car.setCarId("car-1");
        car.setCarName("Test Car");
        car.setCarColor("Yellow");
        car.setCarQuantity(3);
        carRepository.create(car);
        carRepository.delete("car-1");
        Iterator<Car> carIterator = carRepository.findAll();
        assertFalse(carIterator.hasNext());
    }

    @Test
    void testDelete_NotFound() {
        assertDoesNotThrow(() -> carRepository.delete("non-existent"));
    }
}

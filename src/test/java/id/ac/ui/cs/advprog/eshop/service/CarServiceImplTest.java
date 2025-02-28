package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    private Car sampleCar;

    @BeforeEach
    void setUp() {
        sampleCar = new Car();
        sampleCar.setCarId("1");
        sampleCar.setCarName("Test Car");
        sampleCar.setCarColor("Blue");
        sampleCar.setCarQuantity(5);
    }

    @Test
    void testCreateCar() {
        when(carRepository.create(sampleCar)).thenReturn(sampleCar);
        Car created = carService.create(sampleCar);
        assertNotNull(created);
        assertEquals("Test Car", created.getCarName());
        verify(carRepository, times(1)).create(sampleCar);
    }

    @Test
    void testFindAllCars() {
        Car car1 = new Car();
        car1.setCarId("1");
        car1.setCarName("Car 1");
        car1.setCarColor("Red");
        car1.setCarQuantity(2);

        Car car2 = new Car();
        car2.setCarId("2");
        car2.setCarName("Car 2");
        car2.setCarColor("Green");
        car2.setCarQuantity(3);

        List<Car> carList = Arrays.asList(car1, car2);
        when(carRepository.findAll()).thenReturn(carList.iterator());

        List<Car> result = carService.findAll();
        assertEquals(2, result.size());
        assertTrue(result.contains(car1));
        assertTrue(result.contains(car2));
    }

    @Test
    void testFindCarById_Found() {
        when(carRepository.findById("1")).thenReturn(Optional.of(sampleCar));
        Optional<Car> foundOpt = carService.findById("1");
        assertTrue(foundOpt.isPresent());
        Car found = foundOpt.get();
        assertEquals("Test Car", found.getCarName());
        verify(carRepository, times(1)).findById("1");
    }

    @Test
    void testFindCarById_NotFound() {
        when(carRepository.findById("non-existent")).thenReturn(Optional.empty());
        Optional<Car> foundOpt = carService.findById("non-existent");
        assertFalse(foundOpt.isPresent());
    }

    @Test
    void testUpdateCar() {
        // Simulate successful update by returning Optional.of(sampleCar)
        when(carRepository.update(sampleCar)).thenReturn(Optional.of(sampleCar));
        carService.updateById("1", sampleCar);
        verify(carRepository, times(1)).update(sampleCar);
    }

    @Test
    void testDeleteCar() {
        doNothing().when(carRepository).delete("1");
        assertDoesNotThrow(() -> carService.deleteById("1"));
        verify(carRepository, times(1)).delete("1");
    }
}

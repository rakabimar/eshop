package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CarControllerTest {

    @InjectMocks
    private CarController carController;

    @Mock
    private CarService carService;

    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        model = new ExtendedModelMap();
    }

    @Test
    void testCreateCarPage() {
        String view = carController.createCarPage(model);
        assertEquals("createCar", view);
        assertTrue(model.containsAttribute("car"));
        Object carObj = model.getAttribute("car");
        assertNotNull(carObj);
        assertTrue(carObj instanceof Car);
    }

    @Test
    void testCreateCarPost() {
        Car car = new Car();
        String view = carController.createCarPost(car);
        assertEquals("redirect:/car/listCar", view);
        verify(carService, times(1)).create(car);
    }

    @Test
    void testCarListPage() {
        List<Car> carList = new ArrayList<>();
        Car c1 = new Car();
        carList.add(c1);
        when(carService.findAll()).thenReturn(carList);
        String view = carController.carListPage(model);
        assertEquals("carList", view);
        assertTrue(model.containsAttribute("cars"));
        assertEquals(carList, model.getAttribute("cars"));
    }

    @Test
    void testEditCarPage_NotFound() {
        String carId = "nonexistent";
        when(carService.findById(carId)).thenReturn(Optional.empty());
        String view = carController.editCarPage(carId, model);
        assertEquals("redirect:/car/listCar", view);
        assertFalse(model.containsAttribute("car"));
    }

    @Test
    void testEditCarPage_Found() {
        String carId = "existent";
        Car car = new Car();
        when(carService.findById(carId)).thenReturn(Optional.of(car));
        String view = carController.editCarPage(carId, model);
        assertEquals("editCar", view);
        assertTrue(model.containsAttribute("car"));
        assertEquals(car, model.getAttribute("car"));
    }

    @Test
    void testEditCarPost() {
        Car car = new Car();
        car.setCarId("some-id");
        String view = carController.editCarPost(car);
        assertEquals("redirect:/car/listCar", view);
        verify(carService, times(1)).updateById("some-id", car);
    }

    @Test
    void testDeleteCar() {
        String carId = "delete-id";
        String view = carController.deleteCar(carId);
        assertEquals("redirect:/car/listCar", view);
        verify(carService, times(1)).deleteById(carId);
    }
}

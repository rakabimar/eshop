package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;
import java.util.Optional;

import id.ac.ui.cs.advprog.eshop.model.Car;

public interface CarService {
    Car create(Car car);
    List<Car> findAll();
    Optional<Car> findById(String carId);
    void updateById(String carId, Car car);
    void deleteById(String carId);
}
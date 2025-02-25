package id.ac.ui.cs.advprog.eshop.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarRepository carRepository;

    @Override
    public Car create(Car car) {
        carRepository.create(car);
        return car;
    }

    @Override
    public List<Car> findAll() {
        Iterator<Car> carIterator = carRepository.findAll();
        List<Car> carsList = new ArrayList<>();
        carIterator.forEachRemaining(carsList::add);
        return carsList;
    }

    @Override
    public Optional<Car> findById(String carId) {
        Optional<Car> car = carRepository.findById(carId);
        return car;
    }

    @Override
    public void updateById(String carId, Car car) {
        carRepository.update(carId, car);
    }

    @Override
    public void deleteById(String carId) {
        carRepository.delete(carId);
    }
}
package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import java.util.Optional;

public interface CarService extends BaseService<Car> {
    @Override
    Car create(Car car);

    @Override
    java.util.List<Car> findAll();

    @Override
    Optional<Car> findById(String id);

    @Override
    void updateById(String id, Car car);

    @Override
    void deleteById(String id);
}

package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CarRepository implements BaseRepository<Car> {

    private final List<Car> carData = new ArrayList<>();

    @Override
    public Car create(Car car) {
        if (car.getCarId() == null) {
            car.setCarId(UUID.randomUUID().toString());
        }
        carData.add(car);
        return car;
    }

    @Override
    public Iterator<Car> findAll() {
        return carData.iterator();
    }

    @Override
    public Optional<Car> findById(String id) {
        return carData.stream()
                .filter(car -> car.getCarId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Car> update(Car updatedCar) {
        for (int i = 0; i < carData.size(); i++) {
            if (carData.get(i).getCarId().equals(updatedCar.getCarId())) {
                Car existing = carData.get(i);
                existing.setCarName(updatedCar.getCarName());
                existing.setCarColor(updatedCar.getCarColor());
                existing.setCarQuantity(updatedCar.getCarQuantity());
                return Optional.of(existing);
            }
        }
        return Optional.empty();
    }

    @Override
    public void delete(String id) {
        carData.removeIf(car -> car.getCarId().equals(id));
    }
}

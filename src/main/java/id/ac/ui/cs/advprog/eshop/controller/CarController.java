package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/car")
public class CarController {

    private static final String CAR_ATTRIBUTE = "car"; // Constant for car attribute key
    private static final String REDIRECT_CAR_LIST = "redirect:/car/listCar"; // Constant for redirecting to car list

    @Autowired
    private CarService carService;

    @GetMapping("/create")
    public String createCarPage(Model model) {
        model.addAttribute(CAR_ATTRIBUTE, new Car());
        return "createCar";
    }

    @PostMapping("/create")
    public String createCarPost(@ModelAttribute Car car) {
        carService.create(car);
        return REDIRECT_CAR_LIST;
    }

    @GetMapping("/listCar")
    public String carListPage(Model model) {
        List<Car> allCars = carService.findAll();
        model.addAttribute("cars", allCars);
        return "carList";
    }

    @GetMapping("/edit/{carId}")
    public String editCarPage(@PathVariable("carId") String carId, Model model) {
        Optional<Car> carOptional = carService.findById(carId);
        if (carOptional.isEmpty()) {
            return REDIRECT_CAR_LIST;
        }
        model.addAttribute(CAR_ATTRIBUTE, carOptional.get());
        return "editCar";
    }

    @PostMapping("/edit")
    public String editCarPost(@ModelAttribute Car car) {
        carService.updateById(car.getCarId(), car);
        return REDIRECT_CAR_LIST;
    }

    @PostMapping("/delete")
    public String deleteCar(@RequestParam("carId") String carId) {
        carService.deleteById(carId);
        return REDIRECT_CAR_LIST;
    }
}

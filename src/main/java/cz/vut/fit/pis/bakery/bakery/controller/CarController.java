package cz.vut.fit.pis.bakery.bakery.controller;

import cz.vut.fit.pis.bakery.bakery.model.Car;
import cz.vut.fit.pis.bakery.bakery.model.UsersOrder;
import cz.vut.fit.pis.bakery.bakery.repository.CarRepository;
import cz.vut.fit.pis.bakery.bakery.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/car")
public class CarController {

    private final CarRepository carRepository;

    private final OrderRepository orderRepository;

    @Autowired
    public CarController(CarRepository carRepository, OrderRepository orderRepository) {
        this.carRepository = carRepository;
        this.orderRepository = orderRepository;
    }


    @GetMapping("/")
    public List<Car> cars(){
        return (List<Car>) carRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCar(@PathVariable(value = "id") Long id){
        Car car = carRepository.findOne(id);

        if (car == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(car);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<UsersOrder>> getOrdersOfCar(@PathVariable(value = "id") Long id){
        Car car = carRepository.findOne(id);

        if (car == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(car.getOrders());
    }



    @PostMapping("/")
    public Car createCar(@Valid @RequestBody Car car){
        return carRepository.save(car);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable(value = "id") Long id, @RequestBody Car details){
        Car car = carRepository.findOne(id);
        if (car == null){
            return ResponseEntity.notFound().build();
        }
        car.setDateofAcquire(details.getDateofAcquire());
        car.setType(details.getType());

        carRepository.save(car);
        return ResponseEntity.ok(car);
    }

    @PostMapping("/{id}/add")
    public ResponseEntity<List<UsersOrder>> addOrders(@PathVariable(value = "id") Long id,@RequestBody List<Long> orderIDs){
        Car car = carRepository.findOne(id);
        if (car == null){
            return ResponseEntity.notFound().build();
        }

        List<UsersOrder> orders = orderIDs.stream()
                .map(orderRepository::findOne)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (orders.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        orderRepository.save(orders);

        return ResponseEntity.ok(orders);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Car> delete(@PathVariable(value = "id") Long id){
        Car car = carRepository.findOne(id);

        if (car == null){
            return ResponseEntity.notFound().build();
        }

        carRepository.delete(id);

        return ResponseEntity.ok().build();
    }



}

package cz.vut.fit.pis.bakery.bakery.controller;

import cz.vut.fit.pis.bakery.bakery.model.Car;
import cz.vut.fit.pis.bakery.bakery.model.Order;
import cz.vut.fit.pis.bakery.bakery.repository.CarRepository;
import cz.vut.fit.pis.bakery.bakery.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarRepository carRepository;

    private final OrderRepository orderRepository;

    @Autowired
    public CarController(CarRepository carRepository, OrderRepository orderRepository) {
        this.carRepository = carRepository;
        this.orderRepository = orderRepository;
    }


    /**
     *
     * @return List of all available cars
     */
    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public List<Car> cars(){
        return (List<Car>) carRepository.findAll();
    }

    /**
     *
     * @param id Car's id
     * @return Car with "id" or FAIL
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<Car> getCar(@PathVariable(value = "id") Long id){
        Car car = carRepository.findOne(id);

        if (car == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(car);
    }

    /**
     * Get car's orders
     * @param id Car's id
     * @return List of orders assigned to the car
     */
    @GetMapping("/{id}/orders")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<List<Order>> getOrdersOfCar(@PathVariable(value = "id") Long id){
        Car car = carRepository.findOne(id);

        if (car == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(car.getOrders());
    }


    /**
     * Add new car
     * @param car New car
     * @return New added car
     */

    @PostMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Car createCar(@Valid @RequestBody Car car){
        return carRepository.save(car);
    }

    /**
     * Update information about car
     * @param id Car's id
     * @param details New information
     * @return Updated car
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<Car> updateCar(@PathVariable(value = "id") Long id, @RequestBody Car details){
        Car car = carRepository.findOne(id);
        if (car == null){
            return ResponseEntity.notFound().build();
        }

        if (!details.getOrders().isEmpty()){
            List<Order> orders = details.getOrders().stream()  //Find all orders
                    .map(Order::getId)
                    .map(orderRepository::findOne)
                    .filter(Objects::nonNull)
                    .filter(ord -> ord.getCar() == null)
                    .collect(Collectors.toList());

            if (!orders.isEmpty()){
                // For each order set car should deliver it
                for (Order ordr:
                        orders) {
                    ordr.setCar(car);
                }

                orderRepository.save(orders);
            }

        }else{

            for (Order ord:
                 car.getOrders()) {
                ord.setCar(null);
            }
            car.setOrders(new ArrayList<>());
        }
        car.setDateAdd(details.getDateAdd());
        car.setType(details.getType());

        carRepository.save(car);
        return ResponseEntity.ok(car);
    }

    /**
     * Delete car
     * @param id Car's identifier
     * @return OK of FAIL
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Car> delete(@PathVariable(value = "id") Long id){
        Car car = carRepository.findOne(id);

        if (car == null){
            return ResponseEntity.notFound().build();
        }

        carRepository.delete(id);

        return ResponseEntity.ok().build();
    }



}

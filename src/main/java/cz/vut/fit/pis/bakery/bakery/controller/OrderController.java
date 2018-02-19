package cz.vut.fit.pis.bakery.bakery.controller;

import cz.vut.fit.pis.bakery.bakery.model.UsersOrder;
import cz.vut.fit.pis.bakery.bakery.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @GetMapping("/")
    public List<UsersOrder> users() {
        return (List<UsersOrder>) orderRepository.findAll();
    }

    @PostMapping("/")
    public UsersOrder createUser(@Valid @RequestBody UsersOrder usersOrder) {
        return orderRepository.save(usersOrder);
    }
}

package cz.vut.fit.pis.bakery.bakery.controller;

import cz.vut.fit.pis.bakery.bakery.model.BakeryUser;
import cz.vut.fit.pis.bakery.bakery.model.UsersOrder;
import cz.vut.fit.pis.bakery.bakery.repository.OrderRepository;
import cz.vut.fit.pis.bakery.bakery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }


    @GetMapping("/")
    public List<UsersOrder> orders() {
        return (List<UsersOrder>) orderRepository.findAll();
    }

    @PostMapping("/user/{id}")
    public UsersOrder createOrderForUser(@PathVariable(value = "id") Long id, @Valid @RequestBody UsersOrder usersOrder) {
        BakeryUser user = userRepository.findOne(id);

        if (user == null){
            return null;
        }
        usersOrder.setBakeryUser(user);
        return orderRepository.save(usersOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsersOrder> deleteOrder(@PathVariable(value = "id") Long id){
        UsersOrder order = orderRepository.findOne(id);

        if (order == null){
            return ResponseEntity.notFound().build();
        }

        orderRepository.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsersOrder> updateOrder(@PathVariable(value = "id") Long id, @Valid @RequestBody UsersOrder newDetaild){
        UsersOrder order = orderRepository.findOne(id);

        if (order == null){
            return ResponseEntity.notFound().build();
        }

        order.setOrderDate(newDetaild.getOrderDate());
        order.setState(newDetaild.getState());

        orderRepository.save(order);


        return ResponseEntity.ok(order);
    }
}

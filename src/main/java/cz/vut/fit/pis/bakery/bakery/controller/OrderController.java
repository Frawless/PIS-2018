package cz.vut.fit.pis.bakery.bakery.controller;

import cz.vut.fit.pis.bakery.bakery.model.BakeryUser;
import cz.vut.fit.pis.bakery.bakery.model.Item;
import cz.vut.fit.pis.bakery.bakery.model.UsersOrder;
import cz.vut.fit.pis.bakery.bakery.repository.OrderRepository;
import cz.vut.fit.pis.bakery.bakery.repository.ProductRepository;
import cz.vut.fit.pis.bakery.bakery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }


    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public List<UsersOrder> orders() {
        return (List<UsersOrder>) orderRepository.findAll();
    }

    @GetMapping("/{username}/{orderId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE') or #principal.name == #username")
    public ResponseEntity<UsersOrder> getOrder(Principal principal
            , @PathVariable(value = "username") String username
            , @PathVariable(value = "odrerId") Long orderId){
        UsersOrder order = orderRepository.findOne(orderId);

        if (order == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(order);
    }

    @PostMapping("/")
    public UsersOrder createOrderForUser(@RequestBody UsersOrder usersOrder) {
        BakeryUser user = userRepository.findOne(usersOrder.getBakeryUser().getId());

        if (user == null){
            return null;
        }

        for (Item i:
             usersOrder.getItems()) {
            i.setProduct(productRepository.findOne(i.getProduct().getId()));
        }

        usersOrder.setBakeryUser(user);
        return orderRepository.save(usersOrder);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UsersOrder> deleteOrder(@PathVariable(value = "id") Long id){
        UsersOrder order = orderRepository.findOne(id);

        if (order == null){
            return ResponseEntity.notFound().build();
        }

        orderRepository.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<UsersOrder> updateOrder(@PathVariable(value = "id") Long id, @Valid @RequestBody UsersOrder details){
        UsersOrder order = orderRepository.findOne(id);

        if (order == null){
            return ResponseEntity.notFound().build();
        }

        for (Item i:
                details.getItems()) {
            i.setProduct(productRepository.findOne(i.getProduct().getId()));
        }

        order.setOrderDate(details.getOrderDate());
        order.setState(details.getState());

        orderRepository.save(order);


        return ResponseEntity.ok(order);
    }
}

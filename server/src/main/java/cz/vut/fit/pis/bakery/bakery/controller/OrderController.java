package cz.vut.fit.pis.bakery.bakery.controller;

import cz.vut.fit.pis.bakery.bakery.model.*;
import cz.vut.fit.pis.bakery.bakery.repository.CarRepository;
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

    private final CarRepository carRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository,
                           CarRepository carRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.carRepository = carRepository;
    }

    /**
     *
     * @return List of all orders have ever made
     */
    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public List<Order> orders() {
        return (List<Order>) orderRepository.findAll();
    }

    /**
     * Get certain information about particulat order
     * @param principal users principal(used in case of user wants to see his order)
     * @param username identify user
     * @param orderId Identify order
     * @return Users order
     */
    @GetMapping("/{username}/{orderId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE') or #principal.name == #username")
    public ResponseEntity<Order> getOrder(Principal principal
            , @PathVariable(value = "username") String username
            , @PathVariable(value = "orderId") Long orderId){
        Order order = orderRepository.findOne(orderId);

        if (order == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(order);
    }

    /**
     * Create new order
     * @param order information about order
     * @return Created order or fail
     */
    @PostMapping("/")
    public Order createOrderForUser(@RequestBody Order order) {
        User user = userRepository.findOne(order.getUser().getId());

        if (user == null){
            return null;
        }

        for (Item i:
             order.getItems()) {
            i.setProduct(productRepository.findOne(i.getProduct().getId()));
        }

        order.setUser(user);
        return orderRepository.save(order);
    }

    /**
     * Remove order from the table
     * @param id Identifier
     * @return  OK or FAIL
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Order> deleteOrder(@PathVariable(value = "id") Long id){
        Order order = orderRepository.findOne(id);

        if (order == null){
            return ResponseEntity.notFound().build();
        }

        orderRepository.delete(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Update some information about order.
     * @param id Identifier
     * @param details New information about order
     * @return Updated order
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<Order> updateOrder(@PathVariable(value = "id") Long id, @Valid @RequestBody Order details){
        Order order = orderRepository.findOne(id);

        if (order == null){
            return ResponseEntity.notFound().build();
        }

        for (Item i:
                details.getItems()) {
            Product product = productRepository.findOne(i.getProduct().getId());

            if (details.getState() == State.READY){
                if (product.getTotalAmount() >= i.getCountOrdered()){
                    productRepository.decrementProduct(product.getId(), i.getCountOrdered());
                    product = productRepository.findOne(i.getProduct().getId());
                }else {
                    return ResponseEntity.noContent().build();
                }
            }
            i.setProduct(product);

        }

        order.setState(details.getState());
        order.setUser(userRepository.findOne(details.getUser().getId()));
        order.setCreateDate(details.getCreateDate());
        if (details.getCar() != null)
        {
            order.setCar(carRepository.findOne(details.getCar().getId()));
        }
        else
        {
            order.setCar(details.getCar());
        }

        //order.setExportDate(details.getExportDate());

        orderRepository.save(order);


        return ResponseEntity.ok(order);
    }

    @GetMapping("/{id}/user")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<User> getUserForOrder(@PathVariable(name = "id") Long id){
        Order order = orderRepository.findOne(id);
        if (order == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(order.getUser());
    }
}

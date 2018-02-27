package cz.vut.fit.pis.bakery.bakery.controller;

import cz.vut.fit.pis.bakery.bakery.model.Product;
import cz.vut.fit.pis.bakery.bakery.model.Item;
import cz.vut.fit.pis.bakery.bakery.model.UsersOrder;
import cz.vut.fit.pis.bakery.bakery.repository.ProductRepository;
import cz.vut.fit.pis.bakery.bakery.repository.ItemRepository;
import cz.vut.fit.pis.bakery.bakery.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemRepository itemRepository;

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    @Autowired
    public ItemController(ItemRepository itemRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.itemRepository = itemRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/")
    public List<Item> items(){
        return (List<Item>) itemRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable(value = "id") Long id){
        Item item = itemRepository.findOne(id);

        if (item == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(item);
    }

    @PostMapping("/add/{name}/{orderId}")
    public ResponseEntity<Item> createItem(
            @PathVariable(value = "name") String name
            ,@PathVariable(value = "orderId") Long orderId
            ,@RequestBody Item item){
        Product product = productRepository.findByName(name);
        UsersOrder order = orderRepository.findOne(orderId);

        if (product == null || order == null){
            return ResponseEntity.notFound().build();
        }

        item.setProduct(product);
        item.setOrder(order);

        return ResponseEntity.ok().body(itemRepository.save(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable(value = "id") Long id, @RequestBody Item details){
        Item item = itemRepository.findOne(id);
        if (item == null){
            return ResponseEntity.notFound().build();
        }

        item.setBestBefore(details.getBestBefore());
        item.setDateOfCooking(details.getDateOfCooking());
        item.setOrderedAmount(details.getOrderedAmount());

        itemRepository.save(item);

        return ResponseEntity.ok(item);
    }



}

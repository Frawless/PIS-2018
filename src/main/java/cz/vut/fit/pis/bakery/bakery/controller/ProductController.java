package cz.vut.fit.pis.bakery.bakery.controller;


import cz.vut.fit.pis.bakery.bakery.model.Ingredient;
import cz.vut.fit.pis.bakery.bakery.model.Product;
import cz.vut.fit.pis.bakery.bakery.repository.IngredientRepository;
import cz.vut.fit.pis.bakery.bakery.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;

    private final IngredientRepository ingredientRepository;


    @Autowired
    public ProductController(ProductRepository productRepository, IngredientRepository ingredientRepository) {
        this.productRepository = productRepository;
        this.ingredientRepository = ingredientRepository;
    }


    @GetMapping("/")
    public List<Product> products(){
        return (List<Product>) productRepository.findAll();
    }

    @GetMapping("/{name}")
    public ResponseEntity<Product> getProduct(@PathVariable(value = "name") String id){
        Product product = productRepository.findByName(id);
        if (product == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(product);
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Product createProduct(@RequestBody Product product){
        return productRepository.save(product);
    }

    @PutMapping("/{name}")
    public ResponseEntity<Product> updateProduct(@PathVariable(name = "name") String name, @RequestBody Product details){
        Product product = productRepository.findByName(name);

        if (product == null){
            return ResponseEntity.notFound().build();
        }

        product.setTotalAmount(details.getTotalAmount());
        product.setEnergyValue(details.getEnergyValue());

        productRepository.save(product);

        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Product> delete(@PathVariable(name = "name") String name){
        Product product = productRepository.findByName(name);

        if (product == null){
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteByName(name);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{name}/add/ingredient")
    public ResponseEntity<Product> addIngredient(@PathVariable(value = "name") String productName, @RequestBody List<Long> ingIds){

        Product product = productRepository.findByName(productName);

        if (product == null){
            return ResponseEntity.notFound().build();
        }

        List<Ingredient> ingredients = ingIds.stream()
                .map(ingredientRepository::findOne)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());


        if (ingredients.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        product.setIngredients(ingredients);

        productRepository.save(product);

        return ResponseEntity.ok(product);


    }
}

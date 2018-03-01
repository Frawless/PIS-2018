package cz.vut.fit.pis.bakery.bakery.controller;


import cz.vut.fit.pis.bakery.bakery.model.Ingredient;
import cz.vut.fit.pis.bakery.bakery.model.Product;
import cz.vut.fit.pis.bakery.bakery.repository.IngredientRepository;
import cz.vut.fit.pis.bakery.bakery.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;

    private final IngredientRepository ingredientRepository;


    @Autowired
    public ProductController(ProductRepository productRepository, IngredientRepository ingredientRepository) {
        this.productRepository = productRepository;
        this.ingredientRepository = ingredientRepository;
    }


    /**
     *
     * @return List of all products
     */
    @GetMapping("/")
    public List<Product> products(){
        return (List<Product>) productRepository.findAll();
    }

    /**
     *
     * @param id ID
     * @return certain product
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable(value = "id") Long id){
        Product product = productRepository.findOne(id);
        if (product == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(product);
    }

    /**
     * Create new product
     * @param product new product
     * @return created product
     */
    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public Product createProduct(@Valid @RequestBody Product product){

        List<Ingredient> ingredients;

        if (!product.getIngredients().isEmpty()) {
            ingredients = product.getIngredients().stream()
                    .map(Ingredient::getId)
                    .map(ingredientRepository::findOne)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            product.setIngredients(new HashSet<>(ingredients));
        }
        return productRepository.save(product);
    }

    /**
     * Update existing product
     * @param id
     * @param details
     * @return
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<Product> updateProduct(@PathVariable(name = "id") Long id, @Valid @RequestBody Product details){
        Product product = productRepository.findOne(id);

        if (product == null){
            return ResponseEntity.notFound().build();
        }

        List<Ingredient> ingredients;

        if (!details.getIngredients().isEmpty()){
            ingredients = details.getIngredients().stream()
                    .map(Ingredient::getId)
                    .map(ingredientRepository::findOne)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            product.setIngredients(new HashSet<>(ingredients));
        }
        product.setName(details.getName());
        product.setTotalAmount(details.getTotalAmount());
        product.setEnergyValue(details.getEnergyValue());

        productRepository.save(product);

        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Product> delete(@PathVariable(name = "id") Long id){
        Product product = productRepository.findOne(id);

        if (product == null){
            return ResponseEntity.notFound().build();
        }
        productRepository.delete(id);
        return ResponseEntity.ok().build();
    }

}

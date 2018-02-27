package cz.vut.fit.pis.bakery.bakery.controller;

import cz.vut.fit.pis.bakery.bakery.model.Ingredient;
import cz.vut.fit.pis.bakery.bakery.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping("/")
    public List<Ingredient> ingredients(){
        return (List<Ingredient>) ingredientRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable(value = "id") Long id){
        Ingredient ingredient = ingredientRepository.findOne(id);

        if (ingredient == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(ingredient);
    }

    @PostMapping("/")
    public Ingredient createIngredient(@RequestBody Ingredient ingredient){
        return ingredientRepository.save(ingredient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable(value = "id") Long ingId, @RequestBody Ingredient details){
        Ingredient ingredient = ingredientRepository.findOne(ingId);

        if (ingId == null){
            return ResponseEntity.notFound().build();
        }

        ingredient.setBestBefore(details.getBestBefore());
        ingredient.setDateOfManufacture(details.getDateOfManufacture());
        ingredient.setName(details.getName());
        ingredient.setStored(details.getStored());
        ingredient.setSupplier(details.getSupplier());

        ingredientRepository.save(ingredient);

        return ResponseEntity.ok(ingredient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Ingredient> delete(@PathVariable(value = "id") Long id){
        Ingredient ingredient = ingredientRepository.findOne(id);

        if (ingredient == null){
            return ResponseEntity.notFound().build();
        }

        ingredientRepository.delete(id);
        return ResponseEntity.ok().build();
    }
}

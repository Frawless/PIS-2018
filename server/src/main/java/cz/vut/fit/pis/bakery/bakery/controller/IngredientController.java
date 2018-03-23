package cz.vut.fit.pis.bakery.bakery.controller;

import cz.vut.fit.pis.bakery.bakery.model.Ingredient;
import cz.vut.fit.pis.bakery.bakery.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    /**
     *
     * @return List of all ingredients
     */
    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public List<Ingredient> ingredients(){
        return (List<Ingredient>) ingredientRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable(value = "id") Long id){
        Ingredient ingredient = ingredientRepository.findOne(id);

        if (ingredient == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(ingredient);
    }

    /**
     * Add new ingredient to store
     * @param ingredient new added ingredient
     * @return new added ingredient
     */
    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public Ingredient createIngredient(@Valid @RequestBody Ingredient ingredient){
        return ingredientRepository.save(ingredient);
    }

    /**
     * Update exists ingredient.
     * @param id Ingredients ID
     * @param details new details about ingredient
     * @return Updated ingredient
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable(value = "id") Long id, @Valid @RequestBody Ingredient details){
        Ingredient ingredient = ingredientRepository.findOne(id);

        if (ingredient == null){
            return ResponseEntity.notFound().build();
        }

        ingredient.setSupplier(details.getSupplier());
        ingredient.setName(details.getName());
        ingredient.setUnit(details.getUnit());

        ingredientRepository.save(ingredient);

        return ResponseEntity.ok(ingredient);
    }

    /**
     * Remove ingredient
     * @param id ID of ingredient will be removed
     * @return OK or FAIL
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<Ingredient> delete(@PathVariable(value = "id") Long id){
        Ingredient ingredient = ingredientRepository.findOne(id);

        if (ingredient == null){
            return ResponseEntity.notFound().build();
        }

        ingredientRepository.delete(id);
        return ResponseEntity.ok().build();
    }
}

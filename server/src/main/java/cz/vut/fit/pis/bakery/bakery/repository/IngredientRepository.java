package cz.vut.fit.pis.bakery.bakery.repository;

import cz.vut.fit.pis.bakery.bakery.model.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}

package cz.vut.fit.pis.bakery.bakery.repository;

import cz.vut.fit.pis.bakery.bakery.model.BakeryUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<BakeryUser, Long> {
    BakeryUser findByEmail(String email);
}

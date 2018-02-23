package cz.vut.fit.pis.bakery.bakery.repository;

import cz.vut.fit.pis.bakery.bakery.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {
}

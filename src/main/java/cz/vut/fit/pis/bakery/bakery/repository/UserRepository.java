package cz.vut.fit.pis.bakery.bakery.repository;

import cz.vut.fit.pis.bakery.bakery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

package cz.vut.fit.pis.bakery.bakery.repository;

import cz.vut.fit.pis.bakery.bakery.model.Catalog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CatalogRepository extends CrudRepository<Catalog, Long> {

    Catalog findByName(String name);

    @Transactional
    void deleteByName(String name);
}

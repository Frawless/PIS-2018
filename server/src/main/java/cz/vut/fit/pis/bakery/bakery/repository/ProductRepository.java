package cz.vut.fit.pis.bakery.bakery.repository;

import cz.vut.fit.pis.bakery.bakery.model.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    Product findByName(String name);

    @Transactional
    void deleteByName(String name);

    @Modifying
    @Transactional
    @Query("UPDATE Product SET totalAmount = totalAmount - :val WHERE id = :productId AND totalAmount >= :val")
    void decrementProduct(@Param("productId") Long productId, @Param("val") int val);

    @Modifying
    @Transactional
    @Query("UPDATE Product SET totalAmount = totalAmount + :val WHERE id = :productId")
    void incrementProduct(@Param("productId") Long productId, @Param("val") int val);

    @Modifying
    @Transactional
    @Query("UPDATE Product SET totalAmount = :maxAmount")
    void resetTotalAmount(@Param("maxAmount") int maxAmount);

}

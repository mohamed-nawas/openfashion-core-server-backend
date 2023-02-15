package com.computicsolutions.openfashion.repository;

import com.computicsolutions.openfashion.entity.Product;
import com.computicsolutions.openfashion.enums.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Product Repository
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    /**
     * This method returns a product by name and description
     *
     * @param name        product name
     * @param description product description
     * @return Product/ null
     */
    Optional<Product> findByNameAndDescription(String name, String description);

    /**
     * This method finds all products by search term
     *
     * @param pageable   pageable
     * @param searchTerm searchTerm
     * @return Products page
     */
    @Query("SELECT p from Product p WHERE p.name LIKE %:searchTerm% OR p.description LIKE %:searchTerm% OR p.type LIKE %:searchTerm%")
    Page<Product> getAllProductsBySearchTerm(Pageable pageable, @Param("searchTerm") String searchTerm);

    /**
     * This method finds all products by product type
     *
     * @param pageable pageable
     * @param type     product type
     * @return Products page
     */
    @Query("SELECT p FROM Product p WHERE p.type = :type")
    Page<Product> getAllProductsByProductType(Pageable pageable, @Param("type") ProductType type);
}

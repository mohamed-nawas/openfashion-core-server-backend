package com.computicsolutions.openfashion.service;

import com.computicsolutions.openfashion.entity.Product;
import com.computicsolutions.openfashion.enums.ProductType;
import com.computicsolutions.openfashion.exception.OpenFashionCoreException;
import com.computicsolutions.openfashion.exception.ProductAlreadyExistsException;
import com.computicsolutions.openfashion.exception.ProductNotFoundException;
import com.computicsolutions.openfashion.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Product Service
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * This method creates a Product in the database
     *
     * @param product product
     */
    public void createProduct(Product product) {
        try {
            if (isProductExists(product.getName(), product.getDescription()))
                throw new ProductAlreadyExistsException("Product already exists, Please do update operation.");
            productRepository.save(product);
        } catch (DataAccessException e) {
            throw new OpenFashionCoreException("Failed to save product to DB for product id: {}" + product.getId(), e);
        }
    }

    /**
     * This method finds a product by id
     *
     * @param id product id
     * @return Product/ null
     */
    public Product getProduct(String id) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(id);
            if (!optionalProduct.isPresent())
                throw new ProductNotFoundException("Product not found for id: " + id);
            return optionalProduct.get();
        } catch (DataAccessException e) {
            throw new OpenFashionCoreException("Failed to get product from DB for product id: " + id, e);
        }
    }

    /**
     * Get all products by page wise
     *
     * @param pageable pageable
     * @return Product page
     */
    public Page<Product> getAllProducts(Pageable pageable) {
        try {
            return productRepository.findAll(pageable);
        } catch (DataAccessException e) {
            throw new OpenFashionCoreException("Failed to get all products from DB", e);
        }
    }

    /**
     * Get all products by page wise by search term
     *
     * @param pageable   pageable
     * @param searchTerm search term
     * @return Product page
     */
    public Page<Product> getAllProductsBySearchTerm(Pageable pageable, String searchTerm) {
        try {
            return productRepository.getAllProductsBySearchTerm(pageable, searchTerm);
        } catch (DataAccessException e) {
            throw new OpenFashionCoreException("Failed to get all products by search term from DB", e);
        }
    }

    /**
     * Get all products by page wise by product type
     *
     * @param pageable    pageable
     * @param productType product type
     * @return Product page
     */
    public Page<Product> getAllProductsByProductType(Pageable pageable, String productType) {
        ProductType type = ProductType.valueOf(productType);
        try {
            return productRepository.getAllProductsByProductType(pageable, type);
        } catch (DataAccessException e) {
            throw new OpenFashionCoreException("Failed to get all products by product type from DB", e);
        }
    }

    /**
     * This method checks if a product exists in the database by name and description
     *
     * @param name        product name
     * @param description product description
     * @return true/false
     */
    private boolean isProductExists(String name, String description) {
        try {
            return productRepository.findByNameAndDescription(name, description).isPresent();
        } catch (DataAccessException e) {
            throw new OpenFashionCoreException("Failed to read product from database", e);
        }
    }
}

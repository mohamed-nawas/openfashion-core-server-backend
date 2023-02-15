package com.computicsolutions.openfashion.controllers;

import com.computicsolutions.openfashion.dto.request.ProductRegistrationRequestDto;
import com.computicsolutions.openfashion.dto.response.ProductListResponseDto;
import com.computicsolutions.openfashion.dto.response.ProductResponseDto;
import com.computicsolutions.openfashion.entity.File;
import com.computicsolutions.openfashion.entity.Product;
import com.computicsolutions.openfashion.enums.ErrorResponseStatusType;
import com.computicsolutions.openfashion.enums.FileType;
import com.computicsolutions.openfashion.enums.SuccessResponseStatusType;
import com.computicsolutions.openfashion.exception.OpenFashionCoreException;
import com.computicsolutions.openfashion.exception.ProductAlreadyExistsException;
import com.computicsolutions.openfashion.exception.ProductNotFoundException;
import com.computicsolutions.openfashion.exception.UploadFileNotFoundException;
import com.computicsolutions.openfashion.service.FileStorageService;
import com.computicsolutions.openfashion.service.ProductService;
import com.computicsolutions.openfashion.wrapper.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

/**
 * Product Controller
 */
@RestController
@RequestMapping("api/v1/products")
@Slf4j
public class ProductController extends Controller {

    private final ProductService productService;
    private final FileStorageService fileStorageService;

    @Autowired
    public ProductController(ProductService productService, FileStorageService fileStorageService) {
        this.productService = productService;
        this.fileStorageService = fileStorageService;
    }

    /**
     * This method creates a Product
     *
     * @param requestDto product registration request dto
     * @return success(product response)/ error response
     */
    @PostMapping(path = "", consumes = APPLICATION_JSON_UTF_8, produces = APPLICATION_JSON_UTF_8)
    public ResponseEntity<ResponseWrapper> createProduct(@RequestParam String imageId,
                                                         @RequestBody ProductRegistrationRequestDto requestDto) {
        try {
            File file = fileStorageService.getFileById(imageId);
            if (!file.getType().equals(FileType.JPEG.getType()) && !file.getType().equals(FileType.PNG.getType())) {
                log.error("File type not supported for request DTO for creating product");
                return getBadRequestResponse(ErrorResponseStatusType.INVALID_FILE_TYPE);
            }
            requestDto.setImage(file);
            if (!requestDto.isRequiredAvailable()) {
                log.error("Required fields missing in product registration request DTO for creating product");
                return getBadRequestResponse(ErrorResponseStatusType.MISSING_REQUIRED_FIELDS);
            }
            Product product = new Product(requestDto);
            productService.createProduct(product);
            ProductResponseDto responseDto = new ProductResponseDto(product);
            log.debug("Successfully created the product {}", responseDto.toLogJson());
            return getSuccessResponse(SuccessResponseStatusType.CREATE_PRODUCT, responseDto);
        } catch (UploadFileNotFoundException e) {
            log.error("Image id provided is not found in DB");
            return getBadRequestResponse(ErrorResponseStatusType.FILE_NOT_FOUND);
        } catch (ProductAlreadyExistsException e) {
            log.error("Product already exists create product requestDto: {}", requestDto.toLogJson(), e);
            return getBadRequestResponse(ErrorResponseStatusType.PRODUCT_ALREADY_EXISTS);
        } catch (OpenFashionCoreException e) {
            log.error("Creating product was failed for requestDto: {}", requestDto.toLogJson(), e);
            return getInternalServerErrorResponse();
        }
    }

    /**
     * This method finds a product by id
     *
     * @param productId product id
     * @return success(product response)/ error response
     */
    @GetMapping(path = "/{productId}", produces = APPLICATION_JSON_UTF_8)
    public ResponseEntity<ResponseWrapper> getProductById(@PathVariable(name = "productId") String productId) {
        try {
            Product product = productService.getProduct(productId);
            ProductResponseDto responseDto = new ProductResponseDto(product);
            log.debug("Successfully returned the product {}", responseDto.toLogJson());
            return getSuccessResponse(SuccessResponseStatusType.READ_PRODUCT, responseDto);
        } catch (ProductNotFoundException e) {
            log.error("Product not found for getting product by id: {}", productId, e);
            return getBadRequestResponse(ErrorResponseStatusType.PRODUCT_NOT_FOUND);
        } catch (OpenFashionCoreException e) {
            log.error("Failed to get product from DB for id: {}", productId, e);
            return getInternalServerErrorResponse();
        }
    }

    /**
     * This method returns all the products by page and size
     *
     * @param page page number
     * @param size number of items per page
     * @return success(products list)/ error response
     */
    @GetMapping(path = "/ALL/{page}/{size}", produces = APPLICATION_JSON_UTF_8)
    public ResponseEntity<ResponseWrapper> getAllProducts(@Min(DEFAULT_PAGE) @PathVariable(name = "page") int page,
                                                          @Positive @Max(PAGE_MAX_SIZE) @PathVariable(name = "size")
                                                                  int size) {
        try {
            Page<Product> productPage = productService.getAllProducts(generatePageable(page, size, UNSORTED,
                    true));
            ProductListResponseDto responseDto = new ProductListResponseDto(productPage);
            log.debug("Successfully returned all products for page: {}, size {}, product list: {}", page, size,
                    responseDto.toLogJson());
            return getSuccessResponse(SuccessResponseStatusType.READ_PRODUCT_LIST, responseDto);
        } catch (OpenFashionCoreException e) {
            log.error("Failed to get all products from DB", e);
            return getInternalServerErrorResponse();
        }
    }

    /**
     * This method returns all products by search term, page and size
     *
     * @param page       page number
     * @param size       number of items per page
     * @param searchTerm search term
     * @return success(products list)/ error response
     */
    @GetMapping(path = "/ALL/{page}/{size}/search/{searchTerm}", produces = APPLICATION_JSON_UTF_8)
    public ResponseEntity<ResponseWrapper> getAllProductsBySearchTerm(@Min(DEFAULT_PAGE) @PathVariable(name = "page") int page,
                                                                      @Positive @Max(PAGE_MAX_SIZE) @PathVariable(name = "size") int size,
                                                                      @PathVariable(name = "searchTerm") String searchTerm) {
        try {
            Page<Product> productPage = productService
                    .getAllProductsBySearchTerm(generatePageable(page, size, DEFAULT_SORT, true), searchTerm);
            ProductListResponseDto responseDto = new ProductListResponseDto(productPage);
            log.debug("Successfully returned all products by search term for page: {}, size {}, searchTerm: {}, " +
                    "product list: {}", page, size, searchTerm, responseDto.toLogJson());
            return getSuccessResponse(SuccessResponseStatusType.READ_PRODUCT_LIST, responseDto);
        } catch (OpenFashionCoreException e) {
            log.error("Failed to get all products by search term from DB for searchTerm: {}", searchTerm, e);
            return getInternalServerErrorResponse();
        }
    }

    /**
     * This method returns all products by page, size and product type
     *
     * @param page        page number
     * @param size        number of items per page
     * @param productType product type
     * @return success(product list)/ error response
     */
    @GetMapping(value = "/ALL/{page}/{size}/{productType}", produces = APPLICATION_JSON_UTF_8)
    public ResponseEntity<ResponseWrapper> getAllProductsByProductType(@Min(DEFAULT_PAGE) @PathVariable(name = "page") int page,
                                                                       @Positive @Max(PAGE_MAX_SIZE) @PathVariable(name = "size") int size,
                                                                       @PathVariable(name = "productType") String productType) {
        try {
            Page<Product> productPage = productService
                    .getAllProductsByProductType(generatePageable(page, size, UNSORTED, true), productType);
            ProductListResponseDto responseDto = new ProductListResponseDto(productPage);
            log.debug("Successfully returned all products by product type for page: {}, size {}, productType: {}, " +
                    "product list: {}", page, size, productType, responseDto.toLogJson());
            return getSuccessResponse(SuccessResponseStatusType.READ_PRODUCT_LIST, responseDto);
        } catch (OpenFashionCoreException e) {
            log.error("Failed to get all products by product type from DB for productType: {}", productType, e);
            return getInternalServerErrorResponse();
        }
    }

    /**
     * This method generates a pageable object
     *
     * @param page        page number
     * @param size        items per page
     * @param sortBy      sorting attribute
     * @param isAscending is ascending
     * @return Pageable
     */
    private Pageable generatePageable(int page, int size, String sortBy, boolean isAscending) {
        return sortBy.equals(UNSORTED) ? PageRequest.of(page, size) :
                isAscending ? PageRequest.of(page, size, Sort.by(sortBy).ascending()) :
                        PageRequest.of(page, size, Sort.by(sortBy).descending());
    }
}

package com.jpcg.shoppingcart.application.services.product;

import com.jpcg.shoppingcart.application.exceptions.ResourceNotFoundException;
import com.jpcg.shoppingcart.application.request.AddProductRequest;
import com.jpcg.shoppingcart.application.request.UpdateProductRequest;
import com.jpcg.shoppingcart.domain.dtos.ImageDto;
import com.jpcg.shoppingcart.domain.dtos.ProductDto;
import com.jpcg.shoppingcart.domain.model.Category;
import com.jpcg.shoppingcart.domain.model.Image;
import com.jpcg.shoppingcart.domain.model.Product;
import com.jpcg.shoppingcart.infrastructure.out.CategoryRepository;
import com.jpcg.shoppingcart.infrastructure.out.ImageRepository;
import com.jpcg.shoppingcart.infrastructure.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing products in the shopping cart system.
 * This service handles the logic for adding, updating, retrieving, and deleting products.
 */
@Service
@RequiredArgsConstructor
public class ProductService  implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    /**
     * Adds a new product to the system.
     *
     * @param request the request object containing product details
     * @return the saved product
     */
    @Override
    public Product addProduct(AddProductRequest request) {

        // Find or create category based on the request
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(()-> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        // Save the new product
        return productRepository.save(createProduct(request, category));
    }

    /**
     * Creates a product instance from the request data.
     *
     * @param request the request object containing product details
     * @param category the category of the product
     * @return the created product instance
     */
    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getDescription(),
                request.getPrice(),
                request.getInventory(),
                category
        );
    }

    /**
     * Updates an existing product.
     *
     * @param request the request object containing updated product details
     * @param productId the ID of the product to update
     * @return the updated product
     * @throws ResourceNotFoundException if the product is not found
     */
    @Override
    public Product updateProduct(UpdateProductRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(product -> udpateExistingProduct(product, request))
                .map(productRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    /**
     * Updates the fields of an existing product.
     *
     * @param existingProduct the product to update
     * @param request the request object containing updated product details
     * @return the updated product
     */
    private Product udpateExistingProduct(Product existingProduct, UpdateProductRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);

        return existingProduct;
    }

    /**
     * Deletes a product by its ID.
     *
     * @param productId the ID of the product to delete
     * @throws ResourceNotFoundException if the product is not found
     */
    @Override
    public void deleteProduct(Long productId) {
        productRepository.findById(productId).ifPresentOrElse(productRepository::delete, ()-> {throw new ResourceNotFoundException("Product not found!");});
    }

    /**
     * Finds a product by its ID.
     *
     * @param productId the ID of the product
     * @return the product if found
     * @throws ResourceNotFoundException if the product is not found
     */
    @Override
    public Product findProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
    }

    /**
     * Retrieves all products from the database.
     *
     * @return a list of all products
     */
    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Retrieves products by their category.
     *
     * @param category the category name
     * @return a list of products in the specified category
     */
    @Override
    public List<Product> findProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    /**
     * Retrieves products by their name.
     *
     * @param name the name of the product
     * @return a list of products with the specified name
     */
    @Override
    public List<Product> findProductsByName(String name) {
        return productRepository.findByName(name);
    }

    /**
     * Retrieves products by their brand.
     *
     * @param brand the brand name
     * @return a list of products from the specified brand
     */
    @Override
    public List<Product> findProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    /**
     * Retrieves products by their category and brand.
     *
     * @param category the category name
     * @param brand the brand name
     * @return a list of products matching both category and brand
     */
    @Override
    public List<Product> findProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    /**
     * Retrieves products by their brand and name.
     *
     * @param brand the brand name
     * @param name the product name
     * @return a list of products matching the brand and name
     */
    @Override
    public List<Product> findProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    /**
     * Counts the number of products by brand and name.
     *
     * @param brand the brand name
     * @param name the product name
     * @return the number of products matching the brand and name
     */
    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }

    @Override
    public List<ProductDto> findProductDtos(List<Product> products) {
        return products.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto =  modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();

        productDto.setImages(imageDtos);

        return productDto;
    }
}

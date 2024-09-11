package com.jpcg.shoppingcart.application.services.product;

import com.jpcg.shoppingcart.application.exceptions.ResourceNotFoundException;
import com.jpcg.shoppingcart.application.request.AddProductRequest;
import com.jpcg.shoppingcart.application.request.UpdateProductRequest;
import com.jpcg.shoppingcart.domain.model.Category;
import com.jpcg.shoppingcart.domain.model.Product;
import com.jpcg.shoppingcart.infrastructure.out.CategoryRepository;
import com.jpcg.shoppingcart.infrastructure.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService  implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest request) {

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(()-> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);

        return productRepository.save(createProduct(request, category));
    }

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

    @Override
    public Product updateProduct(UpdateProductRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(product -> udpateExistingProduct(product, request))
                .map(productRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

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

    @Override
    public void deleteProduct(Long productId) {
        productRepository.findById(productId).ifPresentOrElse(productRepository::delete, ()-> {throw new ResourceNotFoundException("Product not found!");});
    }

    @Override
    public Product findProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> findProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> findProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> findProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> findProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }
}

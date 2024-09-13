package com.jpcg.shoppingcart.application.services.product;

import com.jpcg.shoppingcart.application.request.AddProductRequest;
import com.jpcg.shoppingcart.application.request.UpdateProductRequest;
import com.jpcg.shoppingcart.domain.dtos.ProductDto;
import com.jpcg.shoppingcart.domain.model.Product;

import java.util.List;

public interface IProductService {

    Product addProduct(AddProductRequest product);
    Product updateProduct(UpdateProductRequest product, Long productId);
    void deleteProduct(Long productId);
    Product findProductById(Long productId);
    List<Product> findAllProducts();
    List<Product> findProductsByCategory(String category);
    List<Product> findProductsByBrand(String brand);
    List<Product> findProductsByCategoryAndBrand(String category, String brand);
    List<Product> findProductsByName(String name);
    List<Product> findProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);

    List<ProductDto> findProductDtos(List<Product> products);
    ProductDto convertToDto(Product product);
}

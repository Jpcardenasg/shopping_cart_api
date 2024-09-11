package com.jpcg.shoppingcart.infrastructure.in;

import com.jpcg.shoppingcart.application.exceptions.ResourceNotFoundException;
import com.jpcg.shoppingcart.application.request.AddProductRequest;
import com.jpcg.shoppingcart.application.request.UpdateProductRequest;
import com.jpcg.shoppingcart.application.response.ApiResponse;
import com.jpcg.shoppingcart.application.services.product.IProductService;
import com.jpcg.shoppingcart.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {

        try {
            Product newProduct = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Product added successfully", newProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long productId, @RequestBody UpdateProductRequest product) {

        try {
            Product updatedProduct = productService.updateProduct(product, productId);
            return ResponseEntity.ok(new ApiResponse("Product updated successfully", updatedProduct));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {

        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok(new ApiResponse("Product deleted successfully", productId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {

        try {
            List<Product> products = productService.findAllProducts();
            return ResponseEntity.ok(new ApiResponse("All products", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {

        try {
            Product product = productService.findProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Product found!", product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/product/{productName}")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String productName) {

        try {
            List<Product> products = productService.findProductsByName(productName);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product not found!", productName));
            }
            return ResponseEntity.ok(new ApiResponse("Product found!", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/{productBrand}")
    public ResponseEntity<ApiResponse> getProductByBrand(@PathVariable String productBrand) {

        try {
            List<Product> products = productService.findProductsByBrand(productBrand);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product not found!", productBrand));
            }
            return ResponseEntity.ok(new ApiResponse("Product found!", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{brand}/{name}")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@PathVariable String brand, @PathVariable String name) {

        try {
            List<Product> products = productService.findProductsByBrandAndName(brand, name);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product not found!", products));
            }
            return ResponseEntity.ok(new ApiResponse("Product found!", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{category}/{brand}")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@PathVariable String category, @PathVariable String brand) {

        try {
            List<Product> products = productService.findProductsByCategoryAndBrand(category, brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product not found!", products));
            }
            return ResponseEntity.ok(new ApiResponse("Product found!", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{category}")
    public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String category) {

        try {
            List<Product> products = productService.findProductsByCategory(category);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product not found!", products));
            }
            return ResponseEntity.ok(new ApiResponse("Product found!", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("count/{brand}/{name}")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@PathVariable String brand, @PathVariable String name) {

        try {
            Long productsCount = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse("Product count!", productsCount));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}



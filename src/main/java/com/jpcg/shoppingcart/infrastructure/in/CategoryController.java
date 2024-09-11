package com.jpcg.shoppingcart.infrastructure.in;

import com.jpcg.shoppingcart.application.exceptions.AlreadyExistsException;
import com.jpcg.shoppingcart.application.exceptions.ResourceNotFoundException;
import com.jpcg.shoppingcart.application.response.ApiResponse;
import com.jpcg.shoppingcart.application.services.category.ICategoryService;
import com.jpcg.shoppingcart.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService  categoryService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category) {

        try {
            Category newCategory = categoryService.addCategory(category);
            return  ResponseEntity.ok(new ApiResponse("Category created successfully!", newCategory));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long categoryId, @RequestBody Category category) {

        try {
            Category updatedCategory = categoryService.updateCategory(category, categoryId);
            return  ResponseEntity.ok(new ApiResponse("Category updated successfully!", updatedCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId) {

        try {
            categoryService.deleteCategory(categoryId);
            return  ResponseEntity.ok(new ApiResponse("Category deleted successfully!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {

        try {
            List<Category> categories = categoryService.findAllCategories();
            return  ResponseEntity.ok(new ApiResponse("All categories found!", categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId) {

        try {
            Category category = categoryService.findCategoryById(categoryId);
            return  ResponseEntity.ok(new ApiResponse("Category found!", category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String categoryName) {

        try {
            Category category = categoryService.findCategoryByName(categoryName);
            return  ResponseEntity.ok(new ApiResponse("Category found!", category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}

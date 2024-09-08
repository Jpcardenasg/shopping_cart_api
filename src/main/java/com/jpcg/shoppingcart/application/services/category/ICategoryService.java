package com.jpcg.shoppingcart.application.services.category;

import com.jpcg.shoppingcart.domain.model.Category;

import java.util.List;

public interface ICategoryService {

    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    void deleteCategory(Long id);
    Category findCategoryById(Long id);
    Category findCategoryByName(String name);
    List<Category> findAllCategories();

}

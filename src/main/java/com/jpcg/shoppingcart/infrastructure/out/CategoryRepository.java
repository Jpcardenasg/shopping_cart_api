package com.jpcg.shoppingcart.infrastructure.out;

import com.jpcg.shoppingcart.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);

    boolean existsByName(String name);

}

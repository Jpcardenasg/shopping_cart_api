package com.jpcg.shoppingcart.infrastructure.out;

import com.jpcg.shoppingcart.domain.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByProductId(Long id);
}

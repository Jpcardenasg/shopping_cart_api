package com.jpcg.shoppingcart.infrastructure;

import com.jpcg.shoppingcart.domain.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}

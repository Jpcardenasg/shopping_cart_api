package com.jpcg.shoppingcart.application.services.image;

import com.jpcg.shoppingcart.domain.dtos.ImageDto;
import com.jpcg.shoppingcart.domain.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {

    List<ImageDto> saveImage(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long id);
    void deleteImage(Long id);
    Image findImageById(Long id);
}

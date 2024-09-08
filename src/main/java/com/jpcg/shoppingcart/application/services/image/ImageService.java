package com.jpcg.shoppingcart.application.services.image;

import com.jpcg.shoppingcart.application.exceptions.ResourceNotFoundException;
import com.jpcg.shoppingcart.application.services.product.ProductService;
import com.jpcg.shoppingcart.domain.dtos.ImageDto;
import com.jpcg.shoppingcart.domain.model.Image;
import com.jpcg.shoppingcart.domain.model.Product;
import com.jpcg.shoppingcart.infrastructure.out.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final ProductService productService;

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {

        Product product = productService.findProductById(productId);
        List<ImageDto> savedImageDto = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String baseDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = baseDownloadUrl + image.getId();
                image.setUrl(downloadUrl);

                Image savedImage = imageRepository.save(image);
                savedImage.setUrl(baseDownloadUrl + savedImage.getId());

                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setImageUrl(savedImage.getUrl());
                savedImageDto.add(imageDto);

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long id) {

        try {
            Image image = findImageById(id);
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
            throw new ResourceNotFoundException("Image not found with id: " + id);
        });
    }

    @Override
    public Image findImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Image not found with id: " + id));
    }
}

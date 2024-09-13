package com.jpcg.shoppingcart.domain.dtos;

import lombok.Data;

@Data
public class ImageDto {

    private Long id;
    private String fileName;
    private String downloadUrl;

}

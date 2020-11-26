package com.test.movierent.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MovieDto {
    private String tittle;
    private String description;
    private Integer stock;
    private BigDecimal rentalPrice;
    private BigDecimal salePrice;
    private Boolean availability;
    private byte[] image;
}

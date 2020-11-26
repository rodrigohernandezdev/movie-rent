package com.test.movierent.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MovieDto {
    private String tittle;
    private String description;
    private Integer stock;
    @JsonProperty("rental_price")
    private BigDecimal rentalPrice;
    @JsonProperty("sale_price")
    private BigDecimal salePrice;
    private Boolean availability;
    private byte[] image;
}

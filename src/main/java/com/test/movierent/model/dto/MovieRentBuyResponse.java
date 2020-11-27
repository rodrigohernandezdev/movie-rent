package com.test.movierent.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.test.movierent.model.Movie;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MovieRentBuyResponse {
    private Movie movie;

    private Integer quantity;

    @JsonProperty( "rent_date")
    private LocalDateTime rentDate;

    @JsonProperty( "total_pay")
    private BigDecimal totalPay;

    @JsonProperty( "pay_late_return")
    private BigDecimal payLateReturn;

    private String message;
}

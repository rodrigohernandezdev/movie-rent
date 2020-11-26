package com.test.movierent.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.math.BigDecimal;

@Getter
@Setter
@Entity(name = "movies_log")
public class LogMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movies_log_seq_gen")
    @SequenceGenerator(name = "movies_log_seq_gen", sequenceName = "movies_log_id_seq", initialValue = 10, allocationSize = 1)
    @Column(name = "movies_log_id")
    private Long id;

    @OneToOne(targetEntity = Movie.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "movie_id")
    private Movie movie;

    @Column(length = 100, nullable = false)
    private String tittle;

    @Column(name = "rental_price")
    private BigDecimal rentalPrice;

    @Column(name = "sale_price")
    private BigDecimal salePrice;

}

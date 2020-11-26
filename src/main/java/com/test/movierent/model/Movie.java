package com.test.movierent.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movies_seq_gen")
    @SequenceGenerator(name = "movies_seq_gen", sequenceName = "movies_id_seq", initialValue = 10)
    @Column(name = "movie_id")
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String tittle;

    @Column(length = 100, nullable = false)
    private String description;

    private byte[] image;

    @Column(length = 100, nullable = false)
    private Integer stock;

    @Column(name = "rental_price")
    private BigDecimal rentalPrice;

    @Column(name = "sale_price")
    private BigDecimal salePrice;

    @Column(length = 100, nullable = false)
    private Boolean availability;

    private Integer likes = 0;
}

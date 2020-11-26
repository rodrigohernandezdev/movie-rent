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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "movie_user_rent_buy")
public class MovieUserRentBuy {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_user_rent_buy_seq_gen")
    @SequenceGenerator(name = "movie_user_rent_buy_seq_gen", sequenceName = "movie_user_rent_buy_id_seq", initialValue = 10, allocationSize = 1)
    @Column(name = "movie_user_rent_buy_id")
    private Long id;

    @OneToOne(targetEntity = Movie.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "movie_id")
    private Movie movie;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private Integer quantity;

    @Column(name = "rent_date")
    private LocalDateTime rentDate;

    // Type for represent if it is 1-Rent 2-Buy
    @Column(name = "type")
    private Integer type;

    @Column(name = "total_pay")
    private BigDecimal totalPay;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @Column(name = "pay_late_return")
    private BigDecimal payLateReturn;

}

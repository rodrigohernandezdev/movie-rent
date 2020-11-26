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

@Getter
@Setter
@Entity(name = "movie_user_liked")
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"movie_id", "user_id"})
})
public class MovieUserLiked {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movies_user_liked_seq_gen")
    @SequenceGenerator(name = "movies_user_liked_seq_gen", sequenceName = "movies_user_liked_id_seq", initialValue = 10, allocationSize = 1)
    @Column(name = "movies_user_liked_id")
    private Long id;

    @OneToOne(targetEntity = Movie.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "movie_id")
    private Movie movie;

    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;


}

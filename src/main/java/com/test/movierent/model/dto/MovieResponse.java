package com.test.movierent.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.test.movierent.model.Movie;
import lombok.Data;

import java.util.List;

@Data
public class MovieResponse {
    List<Movie> movies;

    @JsonProperty("page_num")
    Integer pageNum;

    @JsonProperty("page_size")
    Integer pageSize;

    @JsonProperty("total_count")
    Integer count;

    @JsonProperty("total_pages")
    Integer totalPages;

    @JsonProperty("total_elements")
    Long totalElements;
}

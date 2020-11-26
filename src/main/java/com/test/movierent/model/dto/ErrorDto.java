package com.test.movierent.model.dto;

import lombok.Data;

/**
 * Class used for return the responses with error
 **/

@Data
public class ErrorDto {
    private Object timestamp;
    private Integer status;
    private Object error;
    private String message;
}

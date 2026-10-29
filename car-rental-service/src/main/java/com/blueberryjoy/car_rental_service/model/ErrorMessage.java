package com.blueberryjoy.car_rental_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private Integer code;
    private String message;
}

/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/25/2023
 * Time: 12:03 AM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
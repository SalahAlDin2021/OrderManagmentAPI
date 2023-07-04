/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/25/2023
 * Time: 8:48 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.exception;

import org.springframework.http.HttpStatus;

public class OrderManagementAPIException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public OrderManagementAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public OrderManagementAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
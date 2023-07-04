/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/26/2023
 * Time: 11:48 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.dto;

import lombok.Data;

@Data
public class JWTAuthResponse {

    private String token;

    public JWTAuthResponse(String token) {
        this.token = token;
    }
}
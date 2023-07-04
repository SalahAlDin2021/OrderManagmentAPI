/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/26/2023
 * Time: 10:43 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SignUpDto {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private LocalDate bornAt;
}
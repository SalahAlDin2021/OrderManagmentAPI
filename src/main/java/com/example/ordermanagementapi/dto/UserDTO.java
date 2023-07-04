/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/26/2023
 * Time: 11:58 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.dto;

import lombok.Data;

import java.util.Set;
@Data
public class UserDTO {

    private Long id;
    private String username;
    private Set<String> roles;
}

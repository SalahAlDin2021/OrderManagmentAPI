/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/19/2023
 * Time: 11:24 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}

/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/19/2023
 * Time: 11:17 PM
 * Project Name: orderManagementApi
 */

package com.example.ordermanagementapi.entity;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String slug;
    private String name;
    private String reference;
//    @DecimalMin(value = "2.0", inclusive = true)
//    @DecimalMax(value = "10.0", inclusive = true)
    @Column(precision = 10, scale = 2)
    private BigDecimal price;
//    @DecimalMin(value = "2.0", inclusive = true)
//    @DecimalMax(value = "10.0", inclusive = true)
    @Column(precision = 10, scale = 2)
    private BigDecimal vat;
    private Boolean stockable;
}

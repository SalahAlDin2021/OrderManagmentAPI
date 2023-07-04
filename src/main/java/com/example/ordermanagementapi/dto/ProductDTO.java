/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/19/2023
 * Time: 11:53 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
@Data
@Getter
@Setter
public class ProductDTO extends RepresentationModel<ProductDTO> {

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
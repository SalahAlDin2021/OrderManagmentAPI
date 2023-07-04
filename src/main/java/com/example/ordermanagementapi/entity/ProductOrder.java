/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/19/2023
 * Time: 11:18 PM
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

@Entity
@Data

@Getter
@Setter
//@IdClass(ProductOrderKey.class)
public class ProductOrder {

    @EmbeddedId
    private ProductOrderKey id;

    private Integer quantity;
//    @DecimalMin(value = "2.0", inclusive = true)
//    @DecimalMax(value = "10.0", inclusive = true)
    @Column(precision = 10, scale = 2)
    private BigDecimal price;
//    @DecimalMin(value = "2.0", inclusive = true)
//    @DecimalMax(value = "10.0", inclusive = true)
    @Column(precision = 10, scale = 2)
    private BigDecimal vat;

    @MapsId("productId")
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @MapsId("orderId")
    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;
}
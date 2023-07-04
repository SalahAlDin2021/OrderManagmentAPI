/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/19/2023
 * Time: 11:23 PM
 * Project Name: orderManagementApi
 */

package com.example.ordermanagementapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    private Integer quantity;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime updateAt;
}

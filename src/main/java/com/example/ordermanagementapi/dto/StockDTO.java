/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/19/2023
 * Time: 11:58 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class StockDTO {

    private Integer id;
    private Integer productId;
    private Integer quantity;
    private LocalDateTime updateAt;
}
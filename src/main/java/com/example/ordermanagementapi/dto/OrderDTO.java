/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/19/2023
 * Time: 11:50 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO extends RepresentationModel<OrderDTO> {
    public OrderDTO(Integer id, LocalDateTime orderAt) {
        this.id = id;
        this.orderAt = orderAt;
    }

    private Integer id;
    private Integer customerId;
    private LocalDateTime orderAt;
}
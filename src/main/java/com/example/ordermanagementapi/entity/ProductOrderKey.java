/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/19/2023
 * Time: 11:20 PM
 * Project Name: orderManagementApi
 */

package com.example.ordermanagementapi.entity;


import lombok.Data;

import jakarta.persistence.*;
import java.io.Serializable;
@Data
@Embeddable
public class ProductOrderKey implements Serializable {

    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "order_id")
    private Integer orderId;
}

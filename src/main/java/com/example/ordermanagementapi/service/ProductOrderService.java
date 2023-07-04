/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/30/2023
 * Time: 7:28 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.service;

import com.example.ordermanagementapi.dto.ProductOrderDTO;

import java.util.List;

public interface ProductOrderService {
    List<ProductOrderDTO> getAllProductOrders();
    List<ProductOrderDTO> getProductsOfOrder(Integer orderId);
    ProductOrderDTO getProductOfOrder(Integer orderId, Integer productId);
    ProductOrderDTO addProductToOrder(Integer orderId, ProductOrderDTO productOrderDTO);
    ProductOrderDTO updateProductOfOrder(Integer orderId, Integer productId, ProductOrderDTO productOrderDTO);
    void deleteProductOfOrder(Integer orderId, Integer productId);
}

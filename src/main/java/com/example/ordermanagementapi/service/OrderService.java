/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/30/2023
 * Time: 7:26 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.service;

import com.example.ordermanagementapi.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    List<OrderDTO> getAllOrders();
    OrderDTO getOrderById(Integer id);
    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO updateOrder(Integer id, OrderDTO orderDTO);
    void deleteOrder(Integer id);
    Page<OrderDTO> getOrdersByCustomerId(Integer customerId, Pageable pageable);
    OrderDTO getOrderByCustomerIdAndOrderId(Integer customerId, Integer orderId);
    OrderDTO createOrderByCustomerId(Integer customerId, OrderDTO orderDTO);
    OrderDTO updateOrderByCustomerIdAndOrderId(Integer customerId, Integer orderId, OrderDTO orderDTO);
    void deleteOrderByCustomerIdAndOrderId(Integer customerId, Integer orderId);
}

/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/29/2023
 * Time: 11:44 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.repository;

import com.example.ordermanagementapi.entity.Order;
import com.example.ordermanagementapi.entity.Product;
import com.example.ordermanagementapi.entity.ProductOrder;
import com.example.ordermanagementapi.entity.ProductOrderKey;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, ProductOrderKey> {

    // Find all product orders by order ID
    List<ProductOrder> findByOrderId(Integer orderId);

    // Find a product order by order and product
    Optional<ProductOrder> findByOrderAndProduct(Order order, Product product);

    // Delete a product order by order ID and product ID
    @Transactional
    void deleteByOrderAndProduct(Order order, Product product);
}
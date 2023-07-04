/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/29/2023
 * Time: 11:43 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.repository;
import com.example.ordermanagementapi.entity.Customer;
import com.example.ordermanagementapi.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Page<Order> findByCustomer(Customer customer, Pageable pageable);
    Optional<Order> findByIdAndCustomer(Integer id, Customer customer);
}
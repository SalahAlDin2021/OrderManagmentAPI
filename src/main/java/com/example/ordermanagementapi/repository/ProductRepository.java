/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/29/2023
 * Time: 11:44 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.repository;

import com.example.ordermanagementapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
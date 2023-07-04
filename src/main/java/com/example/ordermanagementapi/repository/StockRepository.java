/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/19/2023
 * Time: 11:45 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.repository;

import com.example.ordermanagementapi.entity.Stock;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
    List<Stock> findByProduct_Id(Integer productId);
    Optional<Stock> findByIdAndProduct_Id(Integer stockId, Integer productId);
    @Transactional
    void deleteByIdAndProduct_Id(Integer stockId, Integer productId);
}
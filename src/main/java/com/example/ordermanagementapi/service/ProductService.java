/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/30/2023
 * Time: 7:29 PM
 * Project Name: Order Management API
 */
package com.example.ordermanagementapi.service;

import com.example.ordermanagementapi.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Page<ProductDTO> getAllProducts(Pageable pageable);
    ProductDTO getProductById(Integer id);
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Integer id, ProductDTO productDTO);
    void deleteProduct(Integer id);
}
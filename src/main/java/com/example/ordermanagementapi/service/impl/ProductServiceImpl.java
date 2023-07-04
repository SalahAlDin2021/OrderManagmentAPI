/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/30/2023
 * Time: 7:56 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.service.impl;

import com.example.ordermanagementapi.dto.ProductDTO;
import com.example.ordermanagementapi.entity.Order;
import com.example.ordermanagementapi.entity.Product;
import com.example.ordermanagementapi.exception.ResourceNotFoundException;
import com.example.ordermanagementapi.repository.ProductRepository;
import com.example.ordermanagementapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(this::convertToDTO);
    }

    @Override
    public ProductDTO getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return convertToDTO(product);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        return convertToDTO(productRepository.save(product));
    }

    @Override
    public ProductDTO updateProduct(Integer id, ProductDTO productDTO) {
        productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Product product = convertToEntity(productDTO);
        product.setId(id);
        return convertToDTO(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    // Method to convert Product to ProductDTO
    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setSlug(product.getSlug());
        productDTO.setName(product.getName());
        productDTO.setReference(product.getReference());
        productDTO.setPrice(product.getPrice());
        productDTO.setVat(product.getVat());
        productDTO.setStockable(product.getStockable());
        return productDTO;
    }

    // Method to convert ProductDTO to Product
    private Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setSlug(productDTO.getSlug());
        product.setName(productDTO.getName());
        product.setReference(productDTO.getReference());
        product.setPrice(productDTO.getPrice());
        product.setVat(productDTO.getVat());
        product.setStockable(productDTO.getStockable());
        return product;
    }
}

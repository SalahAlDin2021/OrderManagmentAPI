/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/3ss0/2023
 * Time: 7:53 PM
 * Project Name: Order Management API
 */
package com.example.ordermanagementapi.service.impl;

import com.example.ordermanagementapi.dto.ProductDTO;
import com.example.ordermanagementapi.dto.ProductOrderDTO;
import com.example.ordermanagementapi.entity.Order;
import com.example.ordermanagementapi.entity.Product;
import com.example.ordermanagementapi.entity.ProductOrder;
import com.example.ordermanagementapi.entity.ProductOrderKey;
import com.example.ordermanagementapi.exception.ResourceNotFoundException;
import com.example.ordermanagementapi.repository.OrderRepository;
import com.example.ordermanagementapi.repository.ProductOrderRepository;
import com.example.ordermanagementapi.repository.ProductRepository;
import com.example.ordermanagementapi.service.ProductOrderService;
import com.example.ordermanagementapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {

    @Autowired
    private ProductOrderRepository productOrderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<ProductOrderDTO> getAllProductOrders() {
        return productOrderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductOrderDTO> getProductsOfOrder(Integer orderId) {
        return productOrderRepository.findByOrderId(orderId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductOrderDTO getProductOfOrder(Integer orderId, Integer productId) {

        Product product= productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product id "+ productId+" not found"));
        System.out.println(product);

        Order order= orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("order id "+ orderId+" not found"));
        System.out.println(order);

        ProductOrder productOrder = productOrderRepository.findByOrderAndProduct(order, product)
                .orElseThrow(() -> new ResourceNotFoundException("Product order not found"));
        System.out.println(productOrder);
//        productOrder.setProduct(product);
//        productOrder.setOrder(order);
        return convertToDTO(productOrder);
    }

    @Override
    public ProductOrderDTO addProductToOrder(Integer orderId, ProductOrderDTO productOrderDTO) {
        Product product= productRepository.findById(productOrderDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("product id "+ productOrderDTO.getProductId()));

        Order order= orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("order id "+ orderId));

        BigDecimal quantity = new BigDecimal(productOrderDTO.getQuantity());
        BigDecimal price=product.getPrice().multiply(quantity);
        BigDecimal vat=product.getVat().multiply(price);
        productOrderDTO.setVat(vat);
        productOrderDTO.setPrice(price.add(vat));

        ProductOrder productOrder = convertToEntity(productOrderDTO);
        productOrder.setProduct(product);
        productOrder.setOrder(order);

        return convertToDTO(productOrderRepository.save(productOrder));
    }

    @Override
    public ProductOrderDTO updateProductOfOrder(Integer orderId, Integer productId, ProductOrderDTO productOrderDTO) {
        Product product= productRepository.findById(productOrderDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("can't find product id "+ productOrderDTO.getProductId()));

        Order order= orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("can't find order id "+ orderId));

        BigDecimal quantity = new BigDecimal(productOrderDTO.getQuantity());
        BigDecimal price=product.getPrice().multiply(quantity);
        BigDecimal vat=product.getVat().multiply(price);
        productOrderDTO.setVat(vat);
        productOrderDTO.setPrice(price.add(vat));
        productOrderDTO.setOrderId(orderId);


        ProductOrder productOrder = productOrderRepository.findByOrderAndProduct(order, product)
                .orElseThrow(() -> new ResourceNotFoundException("Product order not found"));
        System.out.println(productOrder);

//        ProductOrder productOrder=productOrderRepository.findByOrderAndProduct(order, product)
//                .orElseThrow(() -> new ResourceNotFoundException("Product order not found"));


        productOrder = convertToEntity(productOrderDTO);
        productOrder.setId(productOrder.getId());
        return convertToDTO(productOrderRepository.save(productOrder));
    }

    @Override
    public void deleteProductOfOrder(Integer orderId, Integer productId) {
        Product product= productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("can't find product id "+ productId));

        Order order= orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("can't find order id "+ orderId));

        productOrderRepository.deleteByOrderAndProduct(order, product);
    }

    // Method to convert ProductOrder to ProductOrderDTO
    private ProductOrderDTO convertToDTO(ProductOrder productOrder) {
        ProductOrderDTO productOrderDTO = new ProductOrderDTO();
        // Accessing orderId and productId through the composite key
        productOrderDTO.setOrderId(productOrder.getId().getOrderId());
        productOrderDTO.setProductId(productOrder.getId().getProductId());
        productOrderDTO.setQuantity(productOrder.getQuantity());
        productOrderDTO.setPrice(productOrder.getPrice());
        productOrderDTO.setVat(productOrder.getVat());
        return productOrderDTO;
    }

    // Method to convert ProductOrderDTO to ProductOrder
    private ProductOrder convertToEntity(ProductOrderDTO productOrderDTO) {
        ProductOrder productOrder = new ProductOrder();
        // Creating a composite key and setting it to the productOrder
        ProductOrderKey id = new ProductOrderKey();
        id.setOrderId(productOrderDTO.getOrderId());
        id.setProductId(productOrderDTO.getProductId());
        productOrder.setId(id);
        productOrder.setQuantity(productOrderDTO.getQuantity());
        productOrder.setPrice(productOrderDTO.getPrice());
        productOrder.setVat(productOrderDTO.getVat());
        return productOrder;
    }
}

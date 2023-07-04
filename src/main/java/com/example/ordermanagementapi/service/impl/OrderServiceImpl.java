/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/30/2023
 * Time: 7:51 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.service.impl;

import com.example.ordermanagementapi.dto.OrderDTO;
import com.example.ordermanagementapi.entity.Customer;
import com.example.ordermanagementapi.entity.Order;
import com.example.ordermanagementapi.exception.ResourceNotFoundException;
import com.example.ordermanagementapi.repository.CustomerRepository;
import com.example.ordermanagementapi.repository.OrderRepository;
import com.example.ordermanagementapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

        @Override
        public Page<OrderDTO> getOrdersByCustomerId(Integer customerId, Pageable pageable) {
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
            Page<Order> orders = orderRepository.findByCustomer(customer, pageable);
            return orders.map(this::convertToDTO);
        }

    @Override
    public OrderDTO getOrderById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return convertToDTO(order);
    }

    @Override
    public OrderDTO getOrderByCustomerIdAndOrderId(Integer customerId, Integer orderId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        Order order = orderRepository.findByIdAndCustomer(orderId, customer)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for given customer"));
        return convertToDTO(order);
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = convertToEntity(orderDTO);
        return convertToDTO(orderRepository.save(order));
    }

    @Override
    public OrderDTO createOrderByCustomerId(Integer customerId, OrderDTO orderDTO) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        Order order = convertToEntity(orderDTO);
        order.setCustomer(customer);
        return convertToDTO(orderRepository.save(order));
    }

    @Override
    public OrderDTO updateOrder(Integer id, OrderDTO orderDTO) {
        orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        Order order = convertToEntity(orderDTO);
        order.setId(id);
        return convertToDTO(orderRepository.save(order));
    }

    @Override
    public OrderDTO updateOrderByCustomerIdAndOrderId(Integer customerId, Integer orderId, OrderDTO orderDTO) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        Order order = orderRepository.findByIdAndCustomer(orderId, customer)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for given customer"));
        order = convertToEntity(orderDTO);
        order.setId(orderId);
        order.setCustomer(customer);
        return convertToDTO(orderRepository.save(order));
    }

    @Override
    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void deleteOrderByCustomerIdAndOrderId(Integer customerId, Integer orderId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        Order order = orderRepository.findByIdAndCustomer(orderId, customer)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for given customer"));
        orderRepository.delete(order);
    }

    // Method to convert Order to OrderDTO
    private OrderDTO convertToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        if (order.getCustomer() != null) {
            orderDTO.setCustomerId(order.getCustomer().getId());
        }
        orderDTO.setOrderAt(order.getOrderAt());
        return orderDTO;
    }

    // Method to convert OrderDTO to Order
    private Order convertToEntity(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getId());
        if (orderDTO.getCustomerId() != null) {
            Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
            order.setCustomer(customer);
        }
        order.setOrderAt(orderDTO.getOrderAt());
        return order;
    }
}

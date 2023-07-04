/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/30/2023
 * Time: 7:55 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.controller;

import com.example.ordermanagementapi.dto.CustomerDTO;
import com.example.ordermanagementapi.dto.OrderDTO;
import com.example.ordermanagementapi.entity.User;
import com.example.ordermanagementapi.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
@Tag(name = "Orders")
@RestController
@RequestMapping("/api/v1")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Get all orders for a specific customer")
    @GetMapping("/customers/{customerId}/orders")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Page<OrderDTO>> getOrdersByCustomerId(@PathVariable Integer customerId
            , @RequestParam(defaultValue = "0") Integer pageNo
            , @RequestParam(defaultValue = "10") Integer pageSize
            , @RequestParam(defaultValue = "id") String sortBy
            , Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) userDetails;
        Integer authCustomerId = user.getCustomerId();
        // Check if the user is an admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        if (authCustomerId.equals(customerId) || isAdmin) {
            Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
            Page<OrderDTO> orders=orderService.getOrdersByCustomerId(customerId,paging);
            orders.forEach(order -> {
                Link ordersLink = linkTo(methodOn(ProductOrderController.class)
                        .getProductsOfOrder(customerId,order.getId(),authentication))
                        .withRel("OrderProducts");
                order.add(ordersLink);
            });
            return ResponseEntity.ok(orders);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have access to this orders.");
//            return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "Get specific order of a specific customer")
    @GetMapping("/customers/{customerId}/orders/{orderId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<OrderDTO> getOrderByIdAndCustomerId(@PathVariable Integer customerId, @PathVariable Integer orderId,Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) userDetails;
        Integer authCustomerId = user.getCustomerId();
        // Check if the user is an admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        if(authCustomerId.equals(customerId) || isAdmin) {
            OrderDTO order=orderService.getOrderByCustomerIdAndOrderId(customerId,orderId);
                Link ordersLink = linkTo(methodOn(ProductOrderController.class)
                        .getProductsOfOrder(customerId,order.getId(),authentication))
                        .withRel("OrderProducts");
                order.add(ordersLink);
            return ResponseEntity.ok(order);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have access to this orders.");
//            return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "Create an order for a specific customer")
    @PostMapping("/customers/{customerId}/orders")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<OrderDTO> createOrderForCustomer(@PathVariable Integer customerId, @RequestBody OrderDTO orderDTO, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) userDetails;
        Integer authCustomerId = user.getCustomerId();
        // Check if the user is an admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        if(authCustomerId.equals(customerId) || isAdmin) {
            orderDTO.setCustomerId(authCustomerId);
            return ResponseEntity.ok(orderService.createOrderByCustomerId(customerId, orderDTO));
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have access to this orders.");
        }
    }

    @Operation(summary = "Update an order for a specific customer")
    @PutMapping("/customers/{customerId}/orders/{orderId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<OrderDTO> updateOrderForCustomer(@PathVariable Integer customerId, @PathVariable Integer orderId, @RequestBody OrderDTO orderDTO, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) userDetails;
        Integer authCustomerId = user.getCustomerId();
        // Check if the user is an admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        if(authCustomerId.equals(customerId) || isAdmin) {
            orderDTO.setCustomerId(authCustomerId);
            return ResponseEntity.ok(orderService.updateOrderByCustomerIdAndOrderId(customerId, orderId, orderDTO));
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have access to this orders.");
        }
    }

    @Operation(summary = "Delete an order for a specific customer")
    @DeleteMapping("/customers/{customerId}/orders/{orderId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Void> deleteOrderForCustomer(@PathVariable Integer customerId, @PathVariable Integer orderId, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) userDetails;
        Integer authCustomerId = user.getCustomerId();
        // Check if the user is an admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        if(authCustomerId.equals(customerId) || isAdmin) {
            orderService.deleteOrderByCustomerIdAndOrderId(customerId, orderId);
            return ResponseEntity.ok().build();
        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have access to this orders.");
        }
    }
}
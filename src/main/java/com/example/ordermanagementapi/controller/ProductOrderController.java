/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/30/2023
 * Time: 7:56 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.controller;

import com.example.ordermanagementapi.dto.OrderDTO;
import com.example.ordermanagementapi.dto.ProductOrderDTO;
import com.example.ordermanagementapi.entity.User;
import com.example.ordermanagementapi.service.OrderService;
import com.example.ordermanagementapi.service.ProductOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Product Orders")
@RestController
@RequestMapping("/api/v1")
public class ProductOrderController {

    @Autowired
    private ProductOrderService productOrderService;

    // Get the products of a specific order
    @Operation(summary = "Get products of specific order")
    @GetMapping("/customers/{customerId}/orders/{orderId}/products")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<List<ProductOrderDTO>> getProductsOfOrder(@PathVariable Integer customerId,@PathVariable Integer orderId, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) userDetails;
        Integer authCustomerId = user.getCustomerId();
        // Check if the user is an admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        if (authCustomerId.equals(customerId) || isAdmin) {
            List<ProductOrderDTO> productOrders=productOrderService.getProductsOfOrder(orderId);
            productOrders.forEach(productOrder -> {
                Link productOrderLink = linkTo(methodOn(ProductController.class)
                        .getProductById(productOrder.getProductId(),authentication))
                        .withRel("OrderProducts");
                productOrder.add(productOrderLink);
            });
            return ResponseEntity.ok(productOrders);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have access to this ProductOrders.");
//            return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    // Get a specific product of a specific order
    @Operation(summary = "Get specific product of specific order")
    @GetMapping("/customers/{customerId}/orders/{orderId}/products/{productId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<ProductOrderDTO> getProductOfOrder(@PathVariable Integer customerId,@PathVariable Integer orderId, @PathVariable Integer productId, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) userDetails;
        Integer authCustomerId = user.getCustomerId();
        // Check if the user is an admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        if (authCustomerId.equals(customerId) || isAdmin) {
            ProductOrderDTO productOrder=productOrderService.getProductOfOrder(orderId,productId);
                Link productOrderLink = linkTo(methodOn(ProductController.class)
                        .getProductById(productOrder.getProductId(),authentication))
                        .withRel("OrderProducts");
                productOrder.add(productOrderLink);
            return ResponseEntity.ok(productOrder);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have access to this ProductOrders.");
//            return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    // Post a specific product to a specific order
    @Operation(summary = "Post specific product to specific order")
    @PostMapping("/customers/{customerId}/orders/{orderId}/products")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<ProductOrderDTO> postProductToOrder(@PathVariable Integer customerId,@PathVariable Integer orderId, @RequestBody ProductOrderDTO productOrderDTO,Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) userDetails;
        Integer authCustomerId = user.getCustomerId();
        // Check if the user is an admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        if (authCustomerId.equals(customerId) || isAdmin) {
            return ResponseEntity.ok(productOrderService.addProductToOrder(orderId, productOrderDTO));
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have access to this ProductOrders.");
//            return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    // Update a specific product of a specific order
    @Operation(summary = "Update specific product of specific order")
    @PutMapping("/customers/{customerId}/orders/{orderId}/products/{productId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<ProductOrderDTO> updateProductOfOrder(@PathVariable Integer customerId,@PathVariable Integer orderId, @PathVariable Integer productId, @RequestBody ProductOrderDTO productOrderDTO , Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) userDetails;
        Integer authCustomerId = user.getCustomerId();
        // Check if the user is an admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        if (authCustomerId.equals(customerId) || isAdmin) {
            return ResponseEntity.ok(productOrderService.updateProductOfOrder(orderId, productId, productOrderDTO));
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have access to this ProductOrders.");
//            return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    // Delete a specific product of a specific order
    @Operation(summary = "Delete specific product of specific order")
    @DeleteMapping("/customers/{customerId}/orders/{orderId}/products/{productId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Void> deleteProductOfOrder(@PathVariable Integer customerId,@PathVariable Integer orderId, @PathVariable Integer productId,Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) userDetails;
        Integer authCustomerId = user.getCustomerId();
        // Check if the user is an admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        if (authCustomerId.equals(customerId) || isAdmin) {
            System.out.println("delete productOrder");
            productOrderService.deleteProductOfOrder(orderId, productId);
            return ResponseEntity.ok().build();
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have access to this ProductOrders.");
//            return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

}

/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/30/2023
 * Time: 7:55 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.controller;

import com.example.ordermanagementapi.dto.CustomerDTO;
import com.example.ordermanagementapi.entity.User;
import com.example.ordermanagementapi.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@Tag(name = "Customers")
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Operation(summary = "Get all customers")
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) userDetails;
        Integer authCustomerId = user.getCustomerId();
        System.out.println(user);
        // Check if the user is an admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            // If user is admin, get all customers
            List<CustomerDTO> customers = customerService.getAllCustomers();
            customers.forEach(customer -> {
                Link ordersLink = linkTo(methodOn(OrderController.class)
                        .getOrdersByCustomerId(customer.getId(),1,10,"orderAt", authentication))
                        .withRel("customerOrders");
//                Link productsLink = linkTo(methodOn(ProductController.class)
//                        .getAllProducts(null,null,null,authentication))
//                        .withRel("products");
//                customer.add(productsLink);
                customer.add(ordersLink);
            });
            return  ResponseEntity.ok(customers);
        }else{
            // If user is customer, then get just his information
            CustomerDTO customer = customerService.getCustomerById(authCustomerId);
            Link ordersLink = linkTo(methodOn(OrderController.class)
                    .getOrdersByCustomerId(customer.getId(),1,10,"orderAt", authentication))
                    .withRel("customerOrders");
            customer.add(ordersLink);
            Link logoutLink = Link.of(ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()
                            +"/api/v1/auth/logout")
                    .withRel("logout");
            customer.add(logoutLink);
//            Link productsLink = linkTo(methodOn(ProductController.class)
//                    .getAllProducts(null,null,null,authentication))
//                    .withRel("products");
//            customer.add(productsLink);
            List<CustomerDTO> customers=new ArrayList<>();
            customers.add(customer);
            return ResponseEntity.ok(customers);

        }
    }

    @Operation(summary = "Get customer by ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Integer id,Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) userDetails;
        Integer authCustomerId = user.getCustomerId();
        CustomerDTO customer = customerService.getCustomerById(id);
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            // If user is admin, get the customer
            return  ResponseEntity.ok(customer);
        }else {
            // If user is customer, just can get his information
            if (authCustomerId.equals(id)) {
                Link ordersLink = linkTo(methodOn(OrderController.class)
                        .getOrdersByCustomerId(customer.getId(),1,10,"orderAt", authentication))
                        .withRel("customerOrders");
                customer.add(ordersLink);
                Link logoutLink = Link.of(ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()
                                +"/api/v1/auth/logout")
                        .withRel("logout");
                customer.add(logoutLink);
//                Link productsLink = linkTo(methodOn(ProductController.class)
//                        .getAllProducts(null,null,null,authentication))
//                        .withRel("products");
//                customer.add(productsLink);
                return ResponseEntity.ok(customer);
            } else {
                return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
        }
    }

    @Operation(summary = "Create a new customer")
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.ok(customerService.createCustomer(customerDTO));
    }

    @Operation(summary = "Update customer")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Integer id, @RequestBody CustomerDTO customerDTO,Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) userDetails;
        Integer authCustomerId = user.getCustomerId();
        CustomerDTO customer = customerService.getCustomerById(id);
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            // If user is admin, can update any customer information
            return ResponseEntity.ok(customerService.updateCustomer(id, customerDTO));
        }else {
            // If user is admin, can update just his information
            customer.setId(authCustomerId);
            if (authCustomerId.equals(id)) {
                return ResponseEntity.ok(customerService.updateCustomer(id, customerDTO));
            } else {
                return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
        }
    }

    @Operation(summary = "Delete customer by ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }
}

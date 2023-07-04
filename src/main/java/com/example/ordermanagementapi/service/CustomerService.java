/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/30/2023
 * Time: 7:24 PM
 * Project Name: Order Management API
 */
package com.example.ordermanagementapi.service;

import com.example.ordermanagementapi.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(Integer id);
    CustomerDTO createCustomer(CustomerDTO customerDTO);
    CustomerDTO updateCustomer(Integer id, CustomerDTO customerDTO);
    void deleteCustomer(Integer id);
}
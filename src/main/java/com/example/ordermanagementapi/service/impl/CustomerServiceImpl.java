/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/30/2023
 * Time: 7:44 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.service.impl;

import com.example.ordermanagementapi.dto.CustomerDTO;
import com.example.ordermanagementapi.entity.Customer;
import com.example.ordermanagementapi.exception.ResourceNotFoundException;
import com.example.ordermanagementapi.repository.CustomerRepository;
import com.example.ordermanagementapi.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Add methods for converting between Customer and CustomerDTO

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return convertToDTO(customer);
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = convertToEntity(customerDTO);
        return convertToDTO(customerRepository.save(customer));
    }

    @Override
    public CustomerDTO updateCustomer(Integer id, CustomerDTO customerDTO) {
        customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Customer customer = convertToEntity(customerDTO);
        customer.setId(id);
        return convertToDTO(customerRepository.save(customer));
    }

    @Override
    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }

    // Methods to convert between Customer and CustomerDTO would go here

    // Convert Customer entity to CustomerDTO
    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setBornAt(customer.getBornAt());
        // set other fields
        return customerDTO;
    }

    // Convert CustomerDTO to Customer entity
    private Customer convertToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setBornAt(customerDTO.getBornAt());
        // set other fields
        return customer;
    }
}

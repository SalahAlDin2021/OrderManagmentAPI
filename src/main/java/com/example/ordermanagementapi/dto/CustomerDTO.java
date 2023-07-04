/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/19/2023
 * Time: 11:50 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO extends RepresentationModel<CustomerDTO> {

    public CustomerDTO(String firstName,String lastName,LocalDate bornAt){
        this.firstName=firstName;
        this.lastName=lastName;
        this.bornAt=bornAt;
    }
    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate bornAt;
}
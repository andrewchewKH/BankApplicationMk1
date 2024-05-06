package net.javaguides.banking.mapper;

import net.javaguides.banking.dto.AccountDto;
import net.javaguides.banking.dto.CustomerDto;
import net.javaguides.banking.entity.Account;
import net.javaguides.banking.entity.Customer;

public class CustomerMapper {

    public static Customer mapToCustomer(CustomerDto customerDto){

        Customer customer = new Customer(
                customerDto.getCustomerId(),
                customerDto.getCustomerName(),
                customerDto.getPassword(),
                customerDto.getEmail(),
                customerDto.getAddress(),
                customerDto.getPhoneNumber(),
                customerDto.getAccount()
        );

        return customer;
    }

    public static CustomerDto mapToCustomerDto(Customer customer){
        customer.getCustomerId();
        CustomerDto customerDto = new CustomerDto(
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getPassword(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getPhoneNumber(),
                customer.getAccount()
        );

        return customerDto;
    }
}

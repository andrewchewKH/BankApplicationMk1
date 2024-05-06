package net.javaguides.banking.service;

import net.javaguides.banking.dto.CustomerDto;
import net.javaguides.banking.entity.Customer;

import java.util.List;

public interface CustomerService {

    void createCustomerScreen(CustomerDto customerDto);

    CustomerDto createCustomer(CustomerDto customerDto);

    CustomerDto getCustomerByName(String customerName);

    CustomerDto getCustomerById(Long customerId);

    CustomerDto updatePassword(String name, String password);

    CustomerDto updateUpload(Long customerId, String password, String address, String email,String phoneNumber);

    List<CustomerDto> getAllCustomers();

    void updateCustomer(CustomerDto customerDto);

    void deleteCustomer(String name);

    void deleteCustomerById(Long customerId);

    List<CustomerDto> getAllCustomerById(Long customerId);
}

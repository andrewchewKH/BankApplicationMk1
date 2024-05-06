package net.javaguides.banking.service.impl;
import net.javaguides.banking.dto.AccountDto;
import net.javaguides.banking.dto.CustomerDto;
import net.javaguides.banking.entity.Account;
import net.javaguides.banking.entity.Customer;
import net.javaguides.banking.mapper.AccountMapper;
import net.javaguides.banking.mapper.CustomerMapper;
import net.javaguides.banking.repository.CustomerRepository;

import net.javaguides.banking.service.AccountService;
import net.javaguides.banking.service.CustomerService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountService accountService;


    public CustomerServiceImpl(CustomerRepository customerRepository, AccountService accountService) {
        this.customerRepository = customerRepository;
        this.accountService = accountService;
    }
	
	@Override
    public void createCustomerScreen(CustomerDto customerDto) {

        Customer customer = CustomerMapper.mapToCustomer(customerDto);
        customerRepository.save(customer);
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {

        Customer customer = CustomerMapper.mapToCustomer(customerDto);
        Customer savedCustomer = customerRepository.save(customer);

        // Create an account for the user
        Account account = accountService.createAccountNext(savedCustomer);

        savedCustomer.setAccount(account);
        customerRepository.save(savedCustomer);

        //System.out.println(savedCustomer.getAccount().getAccountNumber());
        //System.out.println(account.getCustomer().getCustomerName());

        return CustomerMapper.mapToCustomerDto(savedCustomer);
    }

    @Override
    public CustomerDto getCustomerByName(String name) {

        //if(customerRepository
                //.findByCustomerName(name) == null){
            //throw new RuntimeException("The name never exist.");
        //}
        Customer customer = customerRepository
                .findByCustomerName(name);

        return CustomerMapper.mapToCustomerDto(customer);
    }

    @Override
    public CustomerDto getCustomerById(Long customerId) {

        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer does not exist"));

        return CustomerMapper.mapToCustomerDto(customer);
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map((customer) -> CustomerMapper.mapToCustomerDto(customer))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto updatePassword(String name, String password) {

        if(customerRepository
                .findByCustomerName(name) == null){
            throw new RuntimeException("The name never exist.");
        }
        Customer customer = customerRepository
                .findByCustomerName(name);

        String newPassword = password;
        customer.setPassword(newPassword);
        Customer savedCustomer = customerRepository.save(customer);

        return CustomerMapper.mapToCustomerDto(savedCustomer);
    }

    @Override
    public CustomerDto updateUpload(Long customerId, String password, String address, String email,String phoneNumber){

        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer does not exists"));

        customer.setPassword(password);
        customer.setAddress(address);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);

        Customer savedCustomer = customerRepository.save(customer);

        return CustomerMapper.mapToCustomerDto(savedCustomer);
    }
    @Override
    public void updateCustomer(CustomerDto customerDto) {
        customerRepository.save(CustomerMapper.mapToCustomer(customerDto));
    }
    @Override
    public void deleteCustomer(String name) {

        if(customerRepository
                .findByCustomerName(name) == null){
            throw new RuntimeException("The name never exist.");
        }
        Customer customer = customerRepository
                .findByCustomerName(name);

        Long id =customer.getCustomerId();

        customerRepository.deleteById(id);
    }

    @Override
    public void deleteCustomerById(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<CustomerDto> getAllCustomerById(Long customerId) {
        List<Customer> customers = customerRepository.findByCustomerId(customerId);
        return customers.stream().map((customer) -> CustomerMapper.mapToCustomerDto(customer))
                .collect(Collectors.toList());
    }


}

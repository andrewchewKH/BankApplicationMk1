package net.javaguides.banking.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import net.javaguides.banking.dto.AccountDto;
import net.javaguides.banking.dto.CustomerDto;
import net.javaguides.banking.entity.Customer;
import net.javaguides.banking.service.CustomerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
//@Controller
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    //Springboot
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto){
        return new ResponseEntity<>(customerService.createCustomer(customerDto), HttpStatus.CREATED);
    }



    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers(){
        List<CustomerDto> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    // Get Account REST API
    @GetMapping("/{customerName}")
    public ResponseEntity<CustomerDto> getCustomerByName(@PathVariable String customerName){
        CustomerDto customerDto = customerService.getCustomerByName(customerName);
        return ResponseEntity.ok(customerDto);
    }

    @PutMapping("/{customerId}/update")
    public ResponseEntity<CustomerDto> updateUpload(@PathVariable Long customerId,
                                                      @RequestBody Map<String, String> request){

        CustomerDto originalCustomerDto = customerService.getCustomerById(customerId);
        String password;
        if (request.get("password") != null )
            password = request.get("password");

        else {
            password = originalCustomerDto.getPassword();
        }

        String email;
        if (request.get("email") != null )
            email = request.get("email");

        else
            email = originalCustomerDto.getEmail();

        String address;
        if (request.get("address") != null )
            address = request.get("address");

        else
            address = originalCustomerDto.getAddress();

        String phoneNumber;
        if (request.get("phoneNumber") != null )
            phoneNumber = request.get("phoneNumber");

        else
            phoneNumber = originalCustomerDto.getPhoneNumber();

        CustomerDto customerDto = customerService.updateUpload(customerId, password, address, email,phoneNumber);
        return ResponseEntity.ok(customerDto);
    }

    @DeleteMapping("/{customerName}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String customerName){
        customerService.deleteCustomer(customerName);
        return ResponseEntity.ok("Customer profile is deleted successfully!");
    }
}

package net.javaguides.banking.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import net.javaguides.banking.dto.AccountDto;
import net.javaguides.banking.dto.CustomerDto;
import net.javaguides.banking.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@RestController
@Controller
//@RequestMapping("/api/customers")
public class CustomerWebController {
    private final CustomerService customerService;

    public CustomerWebController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //MVC

    @GetMapping({"/customers"})
    public String listCustomer(Model model){
        List<CustomerDto> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customers";
    }


    //Create Customer Starts
    @GetMapping("/customers/create")
    public String createCustomerScreen(Model model){
        // student model object to store student form data
        CustomerDto customerDto = new CustomerDto();
        model.addAttribute("customer", customerDto);
        return "create_customer";
    }

    @PostMapping("/customers")
    public String saveCustomers(@Valid @ModelAttribute("customer") CustomerDto customer,
                              BindingResult result,
                              Model model){
        if(result.hasErrors()){
            model.addAttribute("customer", customer);
            return "create_customer";
        }

        customerService.createCustomerScreen(customer);
        return "redirect:/customers";
    }

    @GetMapping("/customers/search")
    public String searchCustomerAdmin(
            Model model){
        return "customers_search";
    }
    @PostMapping("/customers/search")
    public String searchCustomerPost(@Valid @ModelAttribute("customerName") String customerName,
                                     BindingResult result,
                                     Model model) {
        if (customerService.getCustomerByName(customerName) == null)
            return "customers";

        CustomerDto customerDto = customerService.getCustomerByName(customerName);
        Long refCustomerId = customerDto.getCustomerId();
        StringBuilder str = new StringBuilder("redirect:/customers/");
        str.append(refCustomerId.toString());
        String path = str.toString();
        System.out.println("Path: "+path);
        return path;
    }

    @GetMapping({"/customers/{customerId}"})
    public String listCustomersById(@PathVariable("customerId") Long customerId,
                                    Model model){
        List<CustomerDto> customers = customerService.getAllCustomerById(customerId);
        model.addAttribute("customers", customers);
        return "customer_filter";
    }
    //End Create Customers


    @GetMapping("/customers/{customerId}/edit")
    public String editCustomer(@PathVariable("customerId") Long customerId,
                              Model model){
        CustomerDto customer = customerService.getCustomerById(customerId);
        model.addAttribute("customer", customer);
        return "edit_customer";
    }

    // handler method to handle edit student form submit request
    @PostMapping("/customers/{customerId}/edit")
    public String updateCustomer(@PathVariable("customerId") Long customerId,
                                @Valid @ModelAttribute("customer") CustomerDto customerDto,
                                BindingResult result,
                                Model model){
        if(result.hasErrors()){
            model.addAttribute("customer", customerDto);
            return "edit_customer";
        }
        customerDto.setCustomerId(customerId);
        customerService.updateCustomer(customerDto);
        return "redirect:/customers";
    }

    // Handler method to handle delete student request
    @GetMapping("/customers/{customerId}/delete")
    public String deleteCustomer(@PathVariable("customerId") Long customerId){
        customerService.deleteCustomerById(customerId);
        return "redirect:/customers";
    }
    @GetMapping("/customers/{customerId}/view")
    public String viewCustomer(@PathVariable("customerId") Long customerId,
                              Model model){
        CustomerDto customerDto = customerService.getCustomerById(customerId);
        model.addAttribute("customer", customerDto);
        return "view_customer";
    }

}

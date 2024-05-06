package net.javaguides.banking.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import net.javaguides.banking.dto.AccountDto;
import net.javaguides.banking.dto.CustomerDto;
import net.javaguides.banking.service.AccountService;
import net.javaguides.banking.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@Controller
//@RequestMapping("/api/accounts")
public class AccountWebCustomerController implements ErrorController {
    private final AccountService accountService;
    private final CustomerService customerService;

    private static final String PATH = "redirect:/userHome";


    @GetMapping(value = PATH)
    public String error() {
        return "Error handling";
    }


    public String getErrorPath() {
        return PATH;
    }

    public AccountWebCustomerController(AccountService accountService, CustomerService customerService) {
        this.accountService = accountService;
        this.customerService = customerService;
    }

    //MVC

    @GetMapping("/userHome")
    public String loginCustomer(
                          Model model){
        return "userHome";
    }

    // handler method to handle edit student form submit request
    @PostMapping("/userHome")
    public String loginCustomer(@Valid @ModelAttribute("customerName") String customerName,
                                @Valid @ModelAttribute("password") String password,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("customerName", customerName);
            model.addAttribute("password", password);
            return "userHome";
        }
        System.out.println("Input Customer Name " + customerName);
        System.out.println("Input Password " + password);
        if (customerService.getCustomerByName(customerName) != null){
            CustomerDto customerDto = customerService.getCustomerByName(customerName);
        System.out.println("Referred customer ID: " + customerDto.getCustomerId());

        System.out.println("Lookup Password " + customerDto.getPassword());
        if (password.contains(customerDto.getPassword())) {
            Long accountCustomerId = customerDto.getCustomerId();

            StringBuilder str = new StringBuilder("redirect:/userHome/");
            str.append(accountCustomerId.toString());
            String path = str.toString();
            return path;
        }
    }

        return "redirect:/userHome";
    }

    @GetMapping({"/userHome/{accountCustomerId}"})
    public String listAccount(@PathVariable("accountCustomerId") Long accountCustomerId,
                              Model model){
        List<AccountDto> accounts = accountService.getAllAccountsByAccountCustomerId(accountCustomerId);
        model.addAttribute("accounts", accounts);
        return "user_accounts";
    }

    @GetMapping("/userHome/{accountCustomerId}/{accountId}")
    public String viewAccount(@PathVariable("accountId") Long accountId,
                              Model model){
        AccountDto accountDto = accountService.getAccountById(accountId);
        model.addAttribute("account", accountDto);
        return "view_account_user";
    }

    @GetMapping("/userHome/{accountCustomerId}/{accountId}/withdraw")
    public String withdraw(@PathVariable("accountId") Long accountId,
                              Model model){
        AccountDto account = accountService.getAccountById(accountId);
        model.addAttribute("account", account);
        return "withdraw";
    }

    // handler method to handle edit student form submit request
    @PostMapping("/userHome/{accountCustomerId}/{accountId}/withdraw")
    public String withdrawPost(@PathVariable("accountId") Long accountId,
                                @Valid @ModelAttribute("withdrawAmount") double withdrawAmount,
                               BindingResult result,
                               Model model){
        if(result.hasErrors()){
            model.addAttribute("withdrawAmount", withdrawAmount);
            return "withdraw";
        }
        //AccountDto accountDto = accountService.getAccountById(accountId);
        accountService.withdrawMVC(accountId,withdrawAmount);
        return "redirect:/userHome/{accountCustomerId}/{accountId}";
    }

    @GetMapping("/userHome/{accountCustomerId}/{accountId}/deposit")
    public String deposit(@PathVariable("accountId") Long accountId,
                           Model model){
        AccountDto account = accountService.getAccountById(accountId);
        model.addAttribute("account", account);
        return "deposit";
    }

    // handler method to handle edit student form submit request
    @PostMapping("/userHome/{accountCustomerId}/{accountId}/deposit")
    public String depositPost(@PathVariable("accountId") Long accountId,
                               @Valid @ModelAttribute("depositAmount") double depositAmount,
                              @ModelAttribute("account") AccountDto accountDto,
                               BindingResult result,
                               Model model){
        if(result.hasErrors()){
            model.addAttribute("depositAmount", depositAmount);
            return "deposit";
        }
        accountDto.setAccountId(accountId);
        accountService.depositMVC(accountId,depositAmount);
        return "redirect:/userHome/{accountCustomerId}/{accountId}";
    }
}

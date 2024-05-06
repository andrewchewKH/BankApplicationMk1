package net.javaguides.banking.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import net.javaguides.banking.dto.AccountDto;
import net.javaguides.banking.dto.CustomerDto;
import net.javaguides.banking.service.AccountService;
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
//@RequestMapping("/api/accounts")
public class AccountWebController {
    private final AccountService accountService;

    public AccountWebController(AccountService accountService) {
        this.accountService = accountService;
    }

    //MVC

    @GetMapping({"/accounts"})
    public String listAccount(Model model){
        List<AccountDto> accounts = accountService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        return "accounts";
    }
    //Create Account Starts
    @GetMapping("/accounts/create")
    public String createAccount(Model model){
        // student model object to store student form data
        AccountDto accountDto = new AccountDto();
        model.addAttribute("account", accountDto);
        return "create_account";
    }

    @PostMapping("/accounts")
    public String saveAccounts(@Valid @ModelAttribute("account") AccountDto account,
                              BindingResult result,
                              Model model){
        if(result.hasErrors()){
            model.addAttribute("account", account);
            return "create_account";
        }

        accountService.createAccount(account);
        return "redirect:/accounts";
    }
    //End Create Accounts

    @GetMapping(value = "/accounts/search")
    public String searchAccountsAdmin(
            Model model){
        return "accounts_search";
    }
    @PostMapping("/accounts/search")
    public String searchAccountsAdminPost(@Valid @ModelAttribute("accountNumber") String accountNumber,
                                     BindingResult result,
                                     Model model) {
        if (accountService.getAccountByAccountNumber(accountNumber) == null)
            return "accounts";

        AccountDto accountDto = accountService.getAccountByAccountNumber(accountNumber);
        Long refAccountId = accountDto.getAccountId();
        StringBuilder str = new StringBuilder("redirect:/accounts/");
        str.append(refAccountId.toString());
        String path = str.toString();
        System.out.println("Path: "+path);
        return path;
    }

    @GetMapping({"/accounts/{accountId}"})
    public String listAccount(@PathVariable("accountId") Long accountId,
                              Model model){
        AccountDto accounts = accountService.getAccountById(accountId);
        model.addAttribute("accounts", accounts);
        return "account_filter";
    }
    @GetMapping("/accounts/{accountId}/edit")
    public String editAccount(@PathVariable("accountId") Long accountId,
                              Model model){
        AccountDto account = accountService.getAccountById(accountId);
        model.addAttribute("account", account);
        return "edit_account";
    }

    // handler method to handle edit student form submit request
    @PostMapping("/accounts/{accountId}/edit")
    public String updateAccount(@PathVariable("accountId") Long accountId,
                                @Valid @ModelAttribute("account") AccountDto accountDto,
                                BindingResult result,
                                Model model){
        if(result.hasErrors()){
            model.addAttribute("account", accountDto);
            return "edit_account";
        }
        AccountDto originalAccountDto = accountService.getAccountById(accountId);
        accountDto.setAccountNumber(originalAccountDto.getAccountNumber());
        accountDto.setAccountCustomerId(originalAccountDto.getAccountCustomerId());
        accountDto.setStatus(originalAccountDto.getStatus());
        accountDto.setCustomerName(originalAccountDto.getCustomerName());
        accountDto.setAccountCustomerId(originalAccountDto.getAccountCustomerId());
        accountDto.setCustomer(originalAccountDto.getCustomer());

        accountDto.setAccountId(accountId);
        accountService.updateAccount(accountDto);
        return "redirect:/accounts";
    }

    // Handler method to handle delete student request
    @GetMapping("/accounts/{accountId}/delete")
    public String deleteAccount(@PathVariable("accountId") Long accountId){
        accountService.deleteAccountById(accountId);
        return "redirect:/accounts";
    }

    @GetMapping("/accounts/{accountId}/close")
    public String closeAccount(@PathVariable("accountId") Long accountId,
                               Model model){
        AccountDto accountDto = accountService.closeAccountById(accountId);
        model.addAttribute("account", accountDto);
        return "close_account";
    }

    @GetMapping("/accounts/{accountId}/view")
    public String viewStudent(@PathVariable("accountId") Long accountId,
                              Model model){
        AccountDto accountDto = accountService.getAccountById(accountId);
        model.addAttribute("account", accountDto);
        return "view_account";
    }

}

package net.javaguides.banking.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.Valid;
import net.javaguides.banking.dto.AccountDto;
import net.javaguides.banking.entity.Account;
import net.javaguides.banking.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Add Account REST API
    @PostMapping
    public ResponseEntity<Account> addAccount(@RequestBody AccountDto accountDto){
        System.out.println("Customer Name: " +accountDto.getCustomerName());
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    // Get Account REST API
    //@GetMapping("/{accountId}")
    //public ResponseEntity<AccountDto> getAccountById(@PathVariable Long accountId){
        //AccountDto accountDto = accountService.getAccountById(accountId);
        //return ResponseEntity.ok(accountDto);
   // }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDto> getAccountByAccountNumber(@PathVariable String accountNumber){
        AccountDto accountDto = accountService.getAccountByAccountNumber(accountNumber);
        return ResponseEntity.ok(accountDto);
    }

    // Deposit REST API
    @PutMapping("/{accountId}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long accountId,
                                              @RequestBody Map<String, Double> request){

        Double amount = request.get("amount");
        AccountDto accountDto = accountService.deposit(accountId, amount);
        return ResponseEntity.ok(accountDto);
    }

    // Withdraw REST API
    @PutMapping("/{accountId}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long accountId,
                                               @RequestBody Map<String, Double> request){

        double amount = request.get("amount");
        AccountDto accountDto = accountService.withdraw(accountId, amount);
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/{accountId}/update")
    public ResponseEntity<AccountDto> updateAccountUpload(@PathVariable Long accountId,
                                                       @RequestBody Map<String, String> request){

        AccountDto originalAccountDto = accountService.getAccountById(accountId);

        double balance = 0;
        if (request.get("balance") != null )
            balance = Double. valueOf(request.get("balance"));

        else
            balance = originalAccountDto.getBalance();

        String password;
        if (request.get("password") != null )
            password = request.get("password");

        else
            password = originalAccountDto.getPassword();

        String pin;
        if (request.get("pin") != null )
            pin = request.get("pin");

        else
            pin = originalAccountDto.getPin();

        System.out.println("Password "+password);
        System.out.println("Pin "+pin);

        AccountDto accountDto = accountService.updateAccountUpload(accountId, balance, password, pin);
        return ResponseEntity.ok(accountDto);
    }

    // Get All Accounts REST API
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        List<AccountDto> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    // Delete Account REST API
    @PutMapping("/{accountNumber}/close")
    public ResponseEntity<AccountDto> closeAccount(@PathVariable String accountNumber){
        //String refAccountNumber = request.get("accountNumber");
        AccountDto accountDto = accountService.closeAccount(accountNumber);
        return ResponseEntity.ok(accountDto);
    }

    // Delete Account REST API
    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<String> deleteAccount(@PathVariable String accountNumber){
        accountService.deleteAccount(accountNumber);
        return ResponseEntity.ok("Account is deleted successfully!");
    }
}

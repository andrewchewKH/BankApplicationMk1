package net.javaguides.banking.service;

import net.javaguides.banking.dto.AccountDto;
import net.javaguides.banking.entity.Account;
import net.javaguides.banking.entity.Customer;

import java.util.List;

public interface AccountService {

    Account createAccountNext (Customer Customer);
    Account createAccount(AccountDto accountDto);

    AccountDto getAccountById(Long accountId);
    AccountDto getAccountByAccountNumber(String accountNumber);

    AccountDto getAccountByCustomerName(String customerName);

    AccountDto deposit(Long id, double amount);

    AccountDto withdraw(Long id, double amount);

    List<AccountDto> getAllAccounts();

    AccountDto closeAccount(String accountNumber);

    AccountDto closeAccountById(Long accountId);
    void deleteAccount(String accountNumber);

    AccountDto updateAccountUpload(Long accountId,double balance, String password, String pin);
    AccountDto updateAccountPin(Long accountId, double balance);

    void updateAccount(AccountDto AccountDto);
    void deleteAccountById(Long accountId);

    //MVC
    List <AccountDto> getAllAccountsByAccountCustomerId(Long customerId);


    void withdrawMVC(Long accountId, double amountWithdraw);

    void depositMVC(Long accountId, double depositAmount);

}

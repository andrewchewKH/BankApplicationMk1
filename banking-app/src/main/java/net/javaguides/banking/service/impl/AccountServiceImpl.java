package net.javaguides.banking.service.impl;

import net.javaguides.banking.dto.AccountDto;
import net.javaguides.banking.dto.CustomerDto;
import net.javaguides.banking.entity.Account;
import net.javaguides.banking.entity.Customer;
import net.javaguides.banking.mapper.AccountMapper;
import net.javaguides.banking.mapper.CustomerMapper;
import net.javaguides.banking.repository.AccountRepository;
import net.javaguides.banking.service.AccountService;
import net.javaguides.banking.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    public AccountServiceImpl(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Account createAccountNext(Customer customer) {
        String accountNumber = generateUniqueAccountNumber();
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setStatus("Active");
        account.setAccountCustomerId(customer.getCustomerId());
        account.setCustomerName(customer.getCustomerName());
        account.setPassword(customer.getPassword());
        account.setBalance(0.0);
        return accountRepository.save(account);
    }
    @Override
    public Account createAccount(AccountDto accountDto) {
        String accountNumber = generateUniqueAccountNumber();

        if(customerRepository.findByCustomerName(accountDto.getCustomerName()) == null){
            throw new RuntimeException("The customer profile has not created yet.");
        }

        //Account refaccount = accountRepository
                //.findByCustomerName(accountDto.getCustomerName());
        Customer refcustomer = customerRepository.findByCustomerName(accountDto.getCustomerName());
        Account account = AccountMapper.mapToAccount(accountDto);
        account.setAccountCustomerId(refcustomer.getCustomerId());
        account.setPassword(refcustomer.getPassword());

        account.setAccountNumber(accountNumber);
        account.setStatus("Active");

        //Set two decimals
        double d = account.getBalance();
        DecimalFormat df = new DecimalFormat("#.##");
        double savedBalance = Double.valueOf(df.format(d));
        account.setBalance(savedBalance);

        return accountRepository.save(account);
    }

    @Override
    public AccountDto getAccountById(Long accountId) {

        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account does not exists"));
        System.out.println("Account ID is: " +account.getAccountId());
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto getAccountByAccountNumber(String accountNumber) {

        if(accountRepository
                .findByAccountNumber(accountNumber) == null){
            throw new RuntimeException("The account number never exist in getAccountByAccountNumber.");
        }
        Account account = accountRepository
                .findByAccountNumber(accountNumber);

        return AccountMapper.mapToAccountDto(account);
    }

    public AccountDto getAccountByCustomerName(String customerName) {

       // if(accountRepository
                //.findByCustomerName(customerName) == null){
            //throw new RuntimeException("The account Customer Name never exist.");
        //}
        Account account = accountRepository
                .findByCustomerName(customerName);

        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {

        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exists"));

        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {

        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exists"));

        if(account.getBalance() < amount){
            throw new RuntimeException("Insufficient amount");
        }

        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account))
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountDto> getAllAccountsByAccountCustomerId(Long accountCustomerId) {
        List<Account> accounts = accountRepository.findByAccountCustomerId(accountCustomerId);
        return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account))
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto closeAccount(String accountNumber) {

        if(accountRepository
                .findByAccountNumber(accountNumber) == null){
            throw new RuntimeException("The inputted account number never exist.");
        }
        Account account = accountRepository
                .findByAccountNumber(accountNumber);


        account.setStatus("Inactive");
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto closeAccountById(Long accountId) {
        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account does not exists"));


        account.setStatus("Inactive");
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public void deleteAccount(String accountNumber) {

        if(accountRepository
                .findByAccountNumber(accountNumber) == null){
            throw new RuntimeException("The inputted account number never exist.");
        }
        Account account = accountRepository
                .findByAccountNumber(accountNumber);

        Long id =account.getAccountId();

        accountRepository.deleteById(id);
    }

    @Override
    public AccountDto updateAccountPin(Long accountId, double balance ) {
        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account does not exists"));

        account.setBalance(balance);
        Account savedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDto(savedAccount);
    }
    @Override
    public AccountDto updateAccountUpload(Long accountId,double balance, String password, String pin ) {
        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account does not exists"));

        account.setBalance(balance);
        account.setPassword(password);
        account.setPin(pin);

        Account savedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDto(savedAccount);
    }
    @Override
    public void updateAccount(AccountDto accountDto) {
        System.out.println("Account ID is: " +accountDto.getAccountId());
        accountRepository.save(AccountMapper.mapToAccount(accountDto));
    }

    @Override
    public void deleteAccountById(Long accountId) {
        accountRepository.deleteById(accountId);
    }

    @Override
    public void depositMVC(Long id, double amount) {

        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exists"));

        double total = account.getBalance() + amount;
        account.setBalance(total);
        accountRepository.save(account);

    }

    @Override
    public void withdrawMVC(Long id, double amount) {

        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exists"));

        if(account.getBalance() < amount){
            throw new RuntimeException("Insufficient amount");
        }

        double total = account.getBalance() - amount;
        account.setBalance(total);
        accountRepository.save(account);
    }
    private String generateUniqueAccountNumber() {
        String accountNumber;
        do {
            // Generate a UUID as the account number
            Random rnd = new Random();
            int number = rnd.nextInt(999999999);

            // this will convert any number sequence into 6 character.
            accountNumber = String.format("%09d", number);
        } while (accountRepository.findByAccountNumber(accountNumber) != null);

        return accountNumber;
    }


}

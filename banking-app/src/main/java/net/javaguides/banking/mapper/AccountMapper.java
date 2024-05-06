package net.javaguides.banking.mapper;

import net.javaguides.banking.dto.AccountDto;
import net.javaguides.banking.entity.Account;
import net.javaguides.banking.entity.Customer;

public class AccountMapper {

    public static Account mapToAccount(AccountDto accountDto){

        Account account = new Account(
                accountDto.getAccountId(),
                accountDto.getAccountNumber(),
                accountDto.getStatus(),
                accountDto.getBalance(),
                accountDto.getAccountCustomerId(),
                accountDto.getCustomerName(),
                accountDto.getPassword(),
                accountDto.getPin(),
                accountDto.getCustomer()
        );

        return account;
    }

    public static AccountDto mapToAccountDto(Account account){
        AccountDto accountDto = new AccountDto(
          account.getAccountId(),
          account.getAccountNumber(),
          account.getStatus(),
          account.getBalance(),
          account.getAccountCustomerId(),
          account.getCustomerName(),
          account.getPassword(),
          account.getPin(),
          account.getCustomer()
        );
        return accountDto;
    }
}

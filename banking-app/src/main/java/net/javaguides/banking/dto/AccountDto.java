package net.javaguides.banking.dto;

import lombok.*;
import net.javaguides.banking.entity.Account;
import net.javaguides.banking.entity.Customer;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountDto {
    private Long accountId;
    private String accountNumber;
    private String status;
    private double balance;
    //private Customer customer;
    private Long accountCustomerId;
    private String customerName;
    private String password;
    private String pin;

    private Customer customer;

    //public Customer getCustomer() {
       //return customer;
   // }


   //public void setCustomer(Customer customer) {
        //this.customer = customer;
   //}

   /* public Account toAccount(){
        return Account.builder()
                .accountId(this.accountId)
                .accountNumber(this.accountNumber)
                .status(this.status)
                .balance(this.balance)
                .customerId(this.customerId)
                .customerName(this.customerName)
                .password(this.password)
                .pin(this.pin)
                .build();
    }*/
}

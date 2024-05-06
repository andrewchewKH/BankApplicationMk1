package net.javaguides.banking.entity;

import jakarta.persistence.*;
import lombok.*;
import net.javaguides.banking.dto.AccountDto;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    private String accountNumber;
    private String status;
    private double balance;
    private Long accountCustomerId;
    private String customerName;
    private String password;
    private String pin;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id",referencedColumnName = "customerId")
    private Customer customer;

    //public Customer getCustomer() {
    //return customer;
    //}

    //public void setCustomer(Customer customer) {
     //this.customer = customer;
    // }
    /*public AccountDto accountDto(){
        return AccountDto.builder()
                .accountId(this.accountId)
                .accountNumber(this.accountNumber)
                .status(this.status)
                .balance(this.balance)
                .customerName(this.customerName)
                .pin(this.pin)
                .build();
    }*/
}

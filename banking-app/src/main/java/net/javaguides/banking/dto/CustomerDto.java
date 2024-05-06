package net.javaguides.banking.dto;
import lombok.*;
import jakarta.validation.*;
import net.javaguides.banking.entity.Account;
import net.javaguides.banking.entity.Customer;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private long customerId;
    //@NotNull(message = "Name cannot be blank")
    private String customerName;
    //@NotNull(message = "Sur Name cannot be blank")
    private String password;
    private String email;

    private String address;

    private String phoneNumber;
    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}

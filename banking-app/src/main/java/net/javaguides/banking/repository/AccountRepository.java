package net.javaguides.banking.repository;

import net.javaguides.banking.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountNumber(String accountNumber);
    Account findByCustomerName(String customerName);
    //@Query("SELECT a FROM Account a WHERE a.accountCustomerId LIKE %?1%")
    List<Account> findByAccountCustomerId (Long accountCustomerId);
}

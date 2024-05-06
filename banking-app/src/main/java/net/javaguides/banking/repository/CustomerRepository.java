package net.javaguides.banking.repository;

import net.javaguides.banking.entity.Account;
import net.javaguides.banking.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByCustomerName(String customerName);

    List<Customer> findByCustomerId(Long customerId);

    //@Query("SELECT c FROM Customer c WHERE c.customerName LIKE %?1%")
    //List<Customer> searchByCustomerName(String customerName);
}

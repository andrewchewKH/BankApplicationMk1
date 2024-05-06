package net.javaguides.banking.entity;

import jakarta.persistence.*;
import lombok.*;
import net.javaguides.banking.dto.CustomerDto;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter
@Table(name = "customers")
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customerId;
    //@Column(name = "name")
    private String customerName;
    private String password;

    private String email;

    private String address;

    private String phoneNumber;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Account account;


}

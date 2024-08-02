package by.ladzislau.gusakov.accountmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uniqueIban",
                        columnNames = "iban"
                )
        }
)
@NoArgsConstructor
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    private String iban;

    private BigDecimal balance;

    private boolean blocked;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Account(String iban, BigDecimal balance, boolean blocked, User user) {
        this.iban = iban;
        this.balance = balance;
        this.blocked = blocked;
        this.user = user;
    }
}



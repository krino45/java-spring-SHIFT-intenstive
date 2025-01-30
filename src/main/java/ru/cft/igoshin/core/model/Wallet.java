package ru.cft.igoshin.core.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wallets")
public class Wallet {
    @Id
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    private User user;
    private int balance;
}

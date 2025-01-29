package ru.cft.igoshin.core.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "money_transfers")
public class MoneyTransfer {
    @Id
    private UUID transferId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime transferDate;
    private int transferAmount;
    private Timestamp createdAt;
    @OneToOne
    @JoinColumn(name = "sender_wallet_id", referencedColumnName = "id")
    private Wallet senderWallet;
    @OneToOne
    @JoinColumn(name = "recipient_wallet_id", referencedColumnName = "id")
    private Wallet recipientWallet;
}

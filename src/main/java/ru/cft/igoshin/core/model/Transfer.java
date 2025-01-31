package ru.cft.igoshin.core.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import ru.cft.igoshin.core.model.enums.Status;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transfer_id")
    private UUID transferId;
    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Column(name = "amount")
    private Long amount;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "sender_wallet_id", referencedColumnName = "id")
    private Wallet senderWallet;
    @ManyToOne
    @JoinColumn(name = "recipient_wallet_id", referencedColumnName = "id")
    private Wallet recipientWallet;

    @PrePersist
    protected void onCreate() {
        creationTime = LocalDateTime.now();
    }
}

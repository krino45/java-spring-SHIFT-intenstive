package ru.cft.igoshin.core.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
    @Column(name = "transfer_id")
    private UUID transferId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Column(name = "amount")
    private int amount;

    @Column(name = "transfer_type")
    private String transferType;

    @Column(name = "status")
    private String status;

    @OneToOne
    @JoinColumn(name = "sender_wallet_id", referencedColumnName = "id")
    private Wallet senderWallet;
    @OneToOne
    @JoinColumn(name = "recipient_wallet_id", referencedColumnName = "id")
    private Wallet recipientWallet;
}

/*
 * TODO:
 *  - Make transferType and status fields enums
 */

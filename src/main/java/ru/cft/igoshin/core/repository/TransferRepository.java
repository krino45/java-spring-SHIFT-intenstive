package ru.cft.igoshin.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.cft.igoshin.core.model.Transfer;
import ru.cft.igoshin.core.model.Wallet;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, UUID> {
    List<Transfer> findAllBySenderWallet(Wallet senderWallet);

    List<Transfer> findAllByRecipientWallet(Wallet recipientWallet);

    List<Transfer> findAllBySenderWalletAndRecipientWallet(Wallet senderWallet, Wallet recipientWallet);
}
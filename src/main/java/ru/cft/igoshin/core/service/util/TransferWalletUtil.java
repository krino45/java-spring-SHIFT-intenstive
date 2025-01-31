package ru.cft.igoshin.core.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.cft.igoshin.core.model.Transfer;
import ru.cft.igoshin.core.model.User;
import ru.cft.igoshin.core.model.Wallet;
import ru.cft.igoshin.core.repository.TransferRepository;
import ru.cft.igoshin.core.repository.WalletRepository;
import ru.cft.igoshin.core.service.CommonServiceUtil;
import ru.cft.igoshin.core.service.exception.CustomServiceException;

import java.util.List;
import java.util.UUID;

@Component
public class TransferWalletUtil implements CommonServiceUtil {
    private final TransferRepository transferRepository;
    private final WalletRepository walletRepository;

    @Autowired
    public TransferWalletUtil(TransferRepository transferRepository,
                              WalletRepository walletRepository) {
        this.transferRepository = transferRepository;
        this.walletRepository = walletRepository;
    }


    public Wallet getWalletById(Long id){
        return walletRepository.findById(id).orElseThrow(
                () -> new CustomServiceException("No wallets found for id: " + id));
    }
    public Wallet getWalletFromUser(User user){
        return walletRepository.findByUser_Id(user.getId()).orElseThrow(
                () -> new CustomServiceException("No wallets found for user: " + user));
    }
    public Wallet getWalletFromUser_Id(UUID userId){
        return walletRepository.findByUser_Id(userId).orElseThrow(
                () -> new CustomServiceException("No wallets found for userId: " + userId));
    }

    public List<Transfer> getTransfersFromSenderWallet(Wallet senderWallet) {
        List<Transfer> transferList = transferRepository.findAllBySenderWallet(senderWallet);
        if (transferList.isEmpty()) {
            throw new CustomServiceException("No transfers found for wallet "+senderWallet.getId());
        }
        return transferList;
    }

    public List<Transfer> getTransfersFromRecipientWallet(Wallet recipientWallet) {
        List<Transfer> transferList = transferRepository.findAllByRecipientWallet(recipientWallet);
        if (transferList.isEmpty()) {
            throw new CustomServiceException("No transfers found for wallet "+recipientWallet.getId());
        }
        return transferList;
    }

    public List<Transfer> getTransfersFromSenderToRecipient(Wallet senderWallet, Wallet recipientWallet) {
        List<Transfer> transferList = transferRepository
                .findAllBySenderWalletAndRecipientWallet(senderWallet, recipientWallet);
        if (transferList.isEmpty()) {
            throw new CustomServiceException("No transfers found between wallets "+
                    senderWallet.getId()+" and "+recipientWallet.getId());
        }
        return transferList;
    }

    public List<Transfer> getAllTransfersBetweenWallets(Wallet senderWallet, Wallet recipientWallet) {
        List<Transfer> transferList = transferRepository
                .findAllBySenderWalletAndRecipientWallet(senderWallet, recipientWallet);
        // Second pass for getting the inverse
        transferList.addAll(transferRepository
                .findAllBySenderWalletAndRecipientWallet(recipientWallet, senderWallet));
        if (transferList.isEmpty()) {
            throw new CustomServiceException("No transfers found between wallets "+
                    senderWallet.getId()+" and "+recipientWallet.getId());
        }
        return transferList;
    }
}

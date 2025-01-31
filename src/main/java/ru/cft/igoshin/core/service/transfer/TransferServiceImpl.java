package ru.cft.igoshin.core.service.transfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.igoshin.api.dto.transfer.TransferCreateRequest;
import ru.cft.igoshin.api.dto.transfer.TransferResponse;
import ru.cft.igoshin.api.dto.transfer.enums.TransferType;
import ru.cft.igoshin.core.model.Transfer;
import ru.cft.igoshin.core.model.User;
import ru.cft.igoshin.core.model.Wallet;
import ru.cft.igoshin.core.model.enums.Status;
import ru.cft.igoshin.core.repository.TransferRepository;
import ru.cft.igoshin.core.service.CommonServiceUtilFactory;
import ru.cft.igoshin.core.service.exception.CustomServiceException;
import ru.cft.igoshin.core.service.util.TransferWalletUtil;
import ru.cft.igoshin.core.service.util.UserSessionUtil;
import ru.cft.igoshin.core.service.TransferService;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class TransferServiceImpl implements TransferService {
    private final TransferRepository transferRepository;
    private final CommonServiceUtilFactory commonServiceUtilFactory;
    private final TransferMapper transferMapper;

    @Autowired
    public TransferServiceImpl(TransferRepository transferRepository, CommonServiceUtilFactory commonServiceUtilFactory,
                               TransferMapper transferMapper) {
        this.transferRepository = transferRepository;
        this.commonServiceUtilFactory = commonServiceUtilFactory;
        this.transferMapper = transferMapper;
    }

    @Override
    public TransferResponse createTransfer(TransferCreateRequest request, UUID sessionId) {
        UserSessionUtil userUtil = commonServiceUtilFactory.createUserSessionUtil();
        TransferWalletUtil walletUtil = commonServiceUtilFactory.createTransferWalletUtil();
        if (userUtil.isSessionExpired(sessionId)) {
            throw new CustomServiceException("Session expired.");
        }
        User user = userUtil.getSessionById(sessionId).getUser();
        Wallet userWallet = user.getWallet();
        if (userWallet.getBalance() < request.amount()) {
            throw new CustomServiceException("Not enough money");
        }
        Wallet recepientWallet;
        Transfer transfer;
        if(request.phone() == null) {
            recepientWallet = walletUtil.getWalletById(request.wallet_id());
        } else {
            User recipient = userUtil.getUserByPhone(request.phone());
            recepientWallet = walletUtil.getWalletFromUser(recipient);
        }
        if (userWallet == recepientWallet) {
            throw new CustomServiceException("Can't create a transfer with yourself");
        }
        userWallet.setBalance(userWallet.getBalance() - request.amount());
        recepientWallet.setBalance(recepientWallet.getBalance() + request.amount());
        transfer = Transfer.builder()
                .amount(request.amount())
                .status(Status.PAID)
                .senderWallet(userWallet)
                .recipientWallet(recepientWallet).build();
        transferRepository.save(transfer);
        return transferMapper.toTransferResponse(transfer);
    }

    @Override
    public List<TransferResponse> getTransfers(UUID userId, TransferType transferType, UUID sessionId) {
        UserSessionUtil userUtil = commonServiceUtilFactory.createUserSessionUtil();
        TransferWalletUtil walletUtil = commonServiceUtilFactory.createTransferWalletUtil();

        if (userUtil.isSessionExpired(sessionId)) {
            throw new CustomServiceException("Session expired.");
        }
        User user = userUtil.getSessionById(sessionId).getUser();
        User other_user;
        Wallet userWallet = user.getWallet();
        if(userId == null && transferType == null) {
            return transferMapper.toListTransferResponse(walletUtil.getAllTransfersForWallet(userWallet));
        } else if(userId != null && transferType == null) {
            other_user = userUtil.findUserById(userId);
            return transferMapper.toListTransferResponse(walletUtil
                    .getAllTransfersBetweenWallets(userWallet, other_user.getWallet()));
        }
        if(transferType == TransferType.IN){
            if(userId == null || userUtil.validateUser(userId, sessionId)) {
                return transferMapper.toListTransferResponse(walletUtil.getTransfersFromRecipientWallet(userWallet));
            }
            other_user = userUtil.findUserById(userId);
            return transferMapper.toListTransferResponse(walletUtil
                    .getTransfersFromSenderToRecipient(other_user.getWallet(), userWallet));
        } else {
            if(userId == null || userUtil.validateUser(userId, sessionId)) {
                return transferMapper.toListTransferResponse(walletUtil.getTransfersFromSenderWallet(userWallet));
            }
            other_user = userUtil.findUserById(userId);
            return transferMapper.toListTransferResponse(walletUtil
                    .getTransfersFromSenderToRecipient(userWallet, other_user.getWallet()));

        }

    }

    @Override
    public TransferResponse getTransferById(UUID transferId, UUID sessionId) {
        UserSessionUtil userUtil = commonServiceUtilFactory.createUserSessionUtil();

        if (userUtil.isSessionExpired(sessionId)) {
            throw new CustomServiceException("Session expired.");
        }
        Wallet user_wallet = userUtil.getSessionById(sessionId).getUser().getWallet();
        // reverse-engineerable :(
        Transfer transfer = transferRepository.findById(transferId)
                .orElseThrow(()-> new CustomServiceException("No transfer found"));
        if (user_wallet == transfer.getRecipientWallet() || user_wallet == transfer.getSenderWallet()) {
            return transferMapper.toTransferResponse(transfer);
        }
        throw new CustomServiceException("Can't view transfers of other people!");
    }
}

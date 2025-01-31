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
        User user = userUtil.getSessionById(sessionId).getUser();
        Wallet userWallet = user.getWallet();
        Wallet recepientWallet;
        Transfer transfer;
        if(request.phone() == null) {
            recepientWallet = walletUtil.getWalletById(request.wallet_id());
        } else {
            User recipient = userUtil.getUserByPhone(request.phone());
            recepientWallet = walletUtil.getWalletFromUser(recipient);
        }
        if (userWallet != recepientWallet) {
            transfer = Transfer.builder()
                    .amount(request.amount())
                    .senderWallet(userWallet)
                    .recipientWallet(recepientWallet).build();
        } else {
            throw new CustomServiceException("Can't create a transfer with yourself");
        }
        return transferMapper.toTransferResponse(transfer);
    }

    @Override
    public List<TransferResponse> getTransfers(UUID userId, TransferType transferType, UUID sessionId) {

    }

    @Override
    public TransferResponse getTransferById(UUID transferId, UUID sessionId) {

    }
}

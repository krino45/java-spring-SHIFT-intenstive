package ru.cft.igoshin.core.service.transfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.igoshin.api.dto.transfer.TransferCreateRequest;
import ru.cft.igoshin.api.dto.transfer.TransferResponse;
import ru.cft.igoshin.api.dto.transfer.enums.TransferType;
import ru.cft.igoshin.core.model.Transfer;
import ru.cft.igoshin.core.repository.TransferRepository;
import ru.cft.igoshin.core.service.util.UserSessionUtil;
import ru.cft.igoshin.core.service.TransferService;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class TransferServiceImpl implements TransferService {
    private final TransferRepository transferRepository;
    private final UserSessionUtil userSessionUtil;

    @Autowired
    public TransferServiceImpl(TransferRepository transferRepository, UserSessionUtil userSessionUtil) {
        this.transferRepository = transferRepository;
        this.userSessionUtil = userSessionUtil;
    }

    @Override
    public TransferResponse createTransfer(TransferCreateRequest request, UUID sessionId) {
        Transfer transfer = Transfer.builder()
                .amount(request.amount())
                .recipientWallet(request.wallet_id()).build();
    }

    @Override
    public List<TransferResponse> getTransfers(UUID userId, TransferType transferType, UUID sessionId) {

    }

    @Override
    public TransferResponse getTransferById(UUID transferId, UUID sessionId) {

    }
}

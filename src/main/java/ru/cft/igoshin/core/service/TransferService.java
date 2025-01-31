package ru.cft.igoshin.core.service;

import ru.cft.igoshin.api.dto.transfer.TransferCreateRequest;
import ru.cft.igoshin.api.dto.transfer.TransferResponse;
import ru.cft.igoshin.api.dto.transfer.enums.TransferType;

import java.util.List;
import java.util.UUID;

public interface TransferService {
    TransferResponse createTransfer(TransferCreateRequest request, UUID sessionId);

    List<TransferResponse> getTransfers(UUID userId, TransferType transferType, UUID sessionId);

    TransferResponse getTransferById(UUID transferId, UUID sessionId);
}

package ru.cft.igoshin.core.service.transfer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.cft.igoshin.api.dto.transfer.TransferResponse;
import ru.cft.igoshin.core.model.Transfer;

import java.util.List;


@Mapper(componentModel = "spring")
public interface TransferMapper {
    @Mapping(target = "senderWalletId", source = "senderWallet")
    @Mapping(target = "recipientWalletId", source = "recipientWallet")
    TransferResponse toTransferResponse(Transfer transfer);

    List<TransferResponse> toListTransferResponse(List<Transfer> transferList);
}

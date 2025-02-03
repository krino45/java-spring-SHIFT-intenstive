package ru.cft.igoshin.core.service.wallet;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.cft.igoshin.api.dto.wallet.WalletResponse;
import ru.cft.igoshin.core.model.Wallet;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WalletMapper {
    @Mapping(target = "number", source = "id")
    WalletResponse toWalletResponse(Wallet wallet);
}

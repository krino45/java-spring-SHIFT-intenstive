package ru.cft.igoshin.core.model.converter.dto;

import org.springframework.core.convert.converter.Converter;
import ru.cft.igoshin.api.dto.transfer.enums.TransferType;

public class StringToTransferTypeConverter implements Converter<String, TransferType> {
    @Override
    public TransferType convert(String source) {
        return TransferType.valueOf(source.toUpperCase());
    }
}
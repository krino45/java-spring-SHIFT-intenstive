package ru.cft.igoshin.core.model.converter.dto;

import org.springframework.core.convert.converter.Converter;
import ru.cft.igoshin.core.model.enums.Status;

public class StringToStatusConverter implements Converter<String, Status> {
    @Override
    public Status convert(String source) {
        return Status.valueOf(source.toUpperCase());
    }
}
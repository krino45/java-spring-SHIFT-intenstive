package ru.cft.igoshin.core.service.session;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.cft.igoshin.api.dto.session.SessionResponse;
import ru.cft.igoshin.core.model.Session;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SessionMapper {
    SessionResponse toSessionCreateResponse(Session session);
}

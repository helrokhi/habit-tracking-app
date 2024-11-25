package ru.ylab.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.ylab.dto.HabitDto;
import ru.ylab.dto.NotificationDto;
import ru.ylab.dto.PersonDto;

@Mapper
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sentTime", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "personId", source = "habitDto.personId")
    @Mapping(target = "habitId", source = "habitDto.id")
    @Mapping(target = "contact", source = "personDto.email")
    @Mapping(target = "isRead", constant = "false")
    NotificationDto habitToNoticesDtoForCreate(HabitDto habitDto, PersonDto personDto);
}

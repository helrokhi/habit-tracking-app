package ru.ylab.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.ylab.dto.HabitDto;
import ru.ylab.dto.RegHabit;

@Mapper
public interface HabitMapper {
    HabitMapper INSTANCE = Mappers.getMapper(HabitMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "title", target = "title")
    @Mapping(source = "text", target = "text")
    @Mapping(target = "time", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(source = "frequency", target = "frequency")
    HabitDto regToDtoForCreate(RegHabit regHabit);
}

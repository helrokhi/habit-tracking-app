package ru.ylab.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.ylab.dto.HabitDto;
import ru.ylab.dto.RegHabit;

class HabitMapperTest {
    private final HabitMapper habitMapper = Mappers.getMapper(HabitMapper.class);
    @Test
    void regToDtoForCreate() {
        RegHabit regHabit = new RegHabit("title", "text", "DAILY");

        HabitDto habitDto = habitMapper.regToDtoForCreate(regHabit);

        Assertions.assertEquals(regHabit.getTitle(), habitDto.getTitle());
        Assertions.assertEquals(regHabit.getText(), habitDto.getText());
        Assertions.assertEquals(regHabit.getFrequency(), habitDto.getFrequency());
    }
}
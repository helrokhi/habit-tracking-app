package ru.ylab.repository;

import ru.ylab.dto.PersonDto;
import ru.ylab.dto.UserAuthDto;

import java.util.ArrayList;

public interface PersonRepository {
    PersonDto getPersonDto(String email, String password);

    PersonDto getPersonDtoById(Long personId);

    PersonDto createPerson(UserAuthDto userAuthDto);

    PersonDto updatePersonName(Long personId, String name);

    PersonDto updatePerson(PersonDto personDto);

    void deletePerson(PersonDto personDto);

    ArrayList<PersonDto> getAllPersonDtos(PersonDto personDto);
}

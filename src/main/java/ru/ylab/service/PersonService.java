package ru.ylab.service;

import lombok.NoArgsConstructor;
import ru.ylab.dto.PersonDto;
import ru.ylab.repository.PersonRepository;
import ru.ylab.repository.impl.PersonRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class PersonService {
    /**
     * Обновление сведений о пользователе
     *
     * @param personDto dto пользователя
     */
    public void update(PersonDto personDto) {
        PersonRepository personRepository = new PersonRepositoryImpl();
        personRepository.updatePerson(personDto);
        System.out.println("Данные пользователя обновлены" + personDto);
    }

    /**
     * Удаление пользователя
     *
     * @param personDto dto пользователя
     */
    public void delete(PersonDto personDto) {
        PersonRepository personRepository = new PersonRepositoryImpl();
        personRepository.deletePerson(personDto);
    }

    /**
     * Получить пользователя по id
     *
     * @param personId id пользователя
     * @return PersonDto dto пользователя для профиля
     */
    public PersonDto getPersonById(Long personId) {
        PersonRepository personRepository = new PersonRepositoryImpl();
        return personRepository.getPersonDtoById(personId);
    }

    /**
     * Получить список всех пользователей
     * для меню пользователя с ролью admin
     *
     * @param personDto dto пользователя
     * @return ArrayList<PersonDto> список всех пользователей для администратора
     */
    public ArrayList<PersonDto> getAllPersons(PersonDto personDto) {
        PersonRepository personRepository = new PersonRepositoryImpl();
        return personRepository.getAllPersonDtos(personDto);
    }

    /**
     * Вывод списка пользователь в консоль
     * для меню пользователя с ролью admin
     *
     * @param personDtos список пользователей
     */
    public void toStringListPersons(List<PersonDto> personDtos) {
        if (personDtos.isEmpty()) {
            System.out.println("Нет пользователей");
        } else {
            System.out.println("Список пользователей");
            for (PersonDto personDto : personDtos) {
                System.out.println("\t" + personDto.getId() + ": " + personDto);
            }
        }
    }
}

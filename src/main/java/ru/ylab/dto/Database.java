package ru.ylab.dto;

import java.util.HashSet;

public class Database {
    private final HashSet<User> users;
    private final HashSet<Person> people;

    private final HashSet<String> emailSet;

    public Database() {
        users = new HashSet<>();
        people = new HashSet<>();
        emailSet = new HashSet<>();
    }

    public void addUser(User user) {
        users.add(user);
        emailSet.add(user.getEmail());
    }

    public void addPerson(Person person) {
        people.add(person);
    }

    public boolean isSuchUser(String email) {
        return emailSet.contains(email);
    }

    public Person getPerson(User user) {
        for (Person person : people) {
            if (person.getUser().equals(user)) {
                return person;
            }
        }
        return null;
    }

    public boolean removeUser(User user) {
        return emailSet.remove(user.getEmail()) && users.remove(user);
    }

    public boolean removePerson(Person person) {
        return people.remove(person);
    }


}

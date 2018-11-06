package com.nibado.example.streambench;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

public class StreamCollectionOperations implements CollectionOperations {
    public List<Integer> doubleNumbers(List<Integer> numbers) {
        return numbers.stream()
            .map(n -> n * 2)
            .collect(toList());
    }

    @Override
    public List<Integer> squareNumbers(List<Integer> numbers) {
        return numbers.stream()
            .map(n -> n * n)
            .collect(toList());
    }

    @Override
    public List<Integer> onlyEvenNumbers(List<Integer> numbers) {
        return numbers.stream()
            .filter(n -> n % 2 == 0)
            .collect(toList());
    }

    @Override
    public String join(List<?> elements) {
        return elements.stream()
            .map(Object::toString)
            .collect(joining());
    }

    @Override
    public int sumCsv(List<String> rows) {
        return rows.stream()
            .flatMapToInt(r -> Stream
                .of(r.split(","))
                .mapToInt(Integer::parseInt))
            .sum();
    }

    @Override
    public List<String> mapStringsToUserToJson(List<String[]> users) {
        return users
            .stream()
            .map(a -> from(a[0], a[1], a[2], a[3], a[4], a[5], a[6]))
            .map(User::toJson)
            .collect(toList());
    }

    private static User from(String firstName, String lastName, String eMail, String yearOfBirth, String roles, String registered, String lastLogin) {
        return new User(
            firstName,
            lastName,
            eMail,
            Integer.parseInt(yearOfBirth),
            of(roles.split(",")).map(role -> User.Role.valueOf(role.toUpperCase())).collect(toList()),
            LocalDateTime.parse(registered, ISO_FORMAT),
            LocalDateTime.parse(lastLogin, ISO_FORMAT)
        );
    }

    private static DateTimeFormatter ISO_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
}

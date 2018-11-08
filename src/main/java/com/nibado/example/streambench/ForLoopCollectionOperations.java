package com.nibado.example.streambench;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

public class ForLoopCollectionOperations implements CollectionOperations {
    @Override
    public List<Integer> squareNumbers(List<Integer> numbers) {
        List<Integer> result = new ArrayList<>(numbers.size());

        for(int i : numbers) {
            result.add(i * i);
        }

        return unmodifiableList(result);
    }

    @Override
    public List<Integer> onlyEvenNumbers(List<Integer> numbers) {
        List<Integer> result = new ArrayList<>(numbers.size() / 2); //Assume an even distribution

        for(int i : numbers) {
            if(i % 2 == 0) {
                result.add(i);
            }
        }

        return result;
    }

    @Override
    public String join(List<?> elements) {
        StringBuilder builder = new StringBuilder();

        for(Object e : elements) {
            builder.append(e);
        }

        return builder.toString();
    }

    @Override
    public int sumCsv(List<String> rows) {
        int result = 0;

        for(String row : rows) {
            for(String v : row.split(",")) {
                result += Integer.parseInt(v);
            }
        }

        return result;
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
        String[] splitRoles = roles.split(",");
        List<User.Role> roleList = new ArrayList<>(splitRoles.length);

        for(String role : splitRoles) {
            roleList.add(User.Role.valueOf(role.toUpperCase()));
        }

        return new User(
            firstName,
            lastName,
            eMail,
            Integer.parseInt(yearOfBirth),
            roleList,
            LocalDateTime.parse(registered, ISO_FORMAT),
            LocalDateTime.parse(lastLogin, ISO_FORMAT)
        );
    }

    private static DateTimeFormatter ISO_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
}

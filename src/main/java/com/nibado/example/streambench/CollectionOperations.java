package com.nibado.example.streambench;

import java.util.List;

public interface CollectionOperations {
    List<Integer> doubleNumbers(List<Integer> numbers);
    List<Integer> squareNumbers(List<Integer> numbers);
    List<Integer> onlyEvenNumbers(List<Integer> numbers);
    String join(List<?> elements);
    int sumCsv(List<String> rows);
    List<String> mapStringsToUserToJson(List<String[]> users);
}

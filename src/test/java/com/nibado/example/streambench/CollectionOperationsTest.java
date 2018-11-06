package com.nibado.example.streambench;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/*
 Test the correctness of the algorithms so we don't have to deal with that in the benchmarks.
 */
class CollectionOperationsTest {

    @TestFactory
    Stream<DynamicTest> doubleNumbers() {
        var input = List.of(1, 2, 3, 4);
        var expected = List.of(2, 4, 6, 8);

        return implementations().map(o ->
            DynamicTest.dynamicTest(o.getClass().getSimpleName(), () -> assertThat(o.doubleNumbers(input)).isEqualTo(expected))
        );
    }

    @TestFactory
    Stream<DynamicTest> squareNumbers() {
        var input = List.of(1, 2, 3, 4);
        var expected = List.of(1, 4, 9, 16);

        return implementations().map(o ->
            DynamicTest.dynamicTest(o.getClass().getSimpleName(), () -> assertThat(o.squareNumbers(input)).isEqualTo(expected))
        );
    }

    @TestFactory
    Stream<DynamicTest> onlyEvenNumbers() {
        var input = List.of(1, 2, 3, 4);
        var expected = List.of(2, 4);

        return implementations().map(o ->
            DynamicTest.dynamicTest(o.getClass().getSimpleName(), () -> assertThat(o.onlyEvenNumbers(input)).isEqualTo(expected))
        );
    }

    @TestFactory
    Stream<DynamicTest> join() {
        var input = List.of("a", "b", "c", "d");
        var expected = "abcd";

        return implementations().map(o ->
            DynamicTest.dynamicTest(o.getClass().getSimpleName(), () -> assertThat(o.join(input)).isEqualTo(expected))
        );
    }

    @TestFactory
    Stream<DynamicTest> sumCsv() {
        var input = List.of("1,2,3", "4,5,6", "7,8,9");
        var expected = 45;

        return implementations().map(o ->
            DynamicTest.dynamicTest(o.getClass().getSimpleName(), () -> assertThat(o.sumCsv(input)).isEqualTo(expected))
        );
    }

    @TestFactory
    Stream<DynamicTest> mapStringToUserToJson() {
        var input = List.<String[]>of(new String[] {
            "Jill",
            "Johnson",
            "jill.johnson@example.com",
            "1980",
            "user,admin",
            "2018-01-01T12:34:56",
            "2018-11-07T10:11:12"});
        var expected = "{\"firstName\":\"Jill\",\"lastName\":\"Johnson\",\"eMail\":\"jill.johnson@example.com\",\"yearOfBirth\":1980,\"roles\":[\"USER\",\"ADMIN\"],\"registered\":\"2018-01-01T12:34:56\",\"lastLogin\":\"2018-11-07T10:11:12\"}";

        return implementations().map(o ->
            DynamicTest.dynamicTest(o.getClass().getSimpleName(), () -> assertThat(o.mapStringsToUserToJson(input)).containsExactly(expected))
        );
    }

    private static Stream<CollectionOperations> implementations() {
        return Stream.of(new ForLoopCollectionOperations(), new StreamCollectionOperations());
    }
}

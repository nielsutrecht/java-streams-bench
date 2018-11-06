package com.nibado.example.streambench;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@State(Scope.Benchmark)
public class ImplementationBenchmark {
    private CollectionOperations implementations[] = {new ForLoopCollectionOperations(), new StreamCollectionOperations() };

    @Param({"0", "1"})
    public int impl;

    @Benchmark
    public void doubleNumbers1000() {
        implementations[impl].doubleNumbers(Data.integers1000);
    }

    @Benchmark
    public void doubleNumbers1M() {
        implementations[impl].doubleNumbers(Data.integers1000000);
    }

    @Benchmark
    public void squareNumbers1000() {
        implementations[impl].squareNumbers(Data.integers1000);
    }

    @Benchmark
    public void squareNumbers1M() {
        implementations[impl].squareNumbers(Data.integers1000000);
    }

    @Benchmark
    public void onlyEvenNumbers1000() {
        implementations[impl].onlyEvenNumbers(Data.integers1000);
    }

    @Benchmark
    public void onlyEvenNumbers1M() {
        implementations[impl].onlyEvenNumbers(Data.integers1000000);
    }

    @Benchmark
    public void join1000() {
        implementations[impl].join(Data.integers1000);
    }

    @Benchmark
    public void join1M() {
        implementations[impl].join(Data.integers1000000);
    }

    @Benchmark
    public void sumCsv1000() {
        implementations[impl].sumCsv(Data.csv1000);
    }

    @Benchmark
    public void sumCsv1M() {
        implementations[impl].sumCsv(Data.csv100000);
    }

    @Benchmark
    public void mapStringsToUserToJson1000() {
        implementations[impl].mapStringsToUserToJson(Data.user1000);
    }

    @Benchmark
    public void mapStringsToUserToJson1000000() {
        implementations[impl].mapStringsToUserToJson(Data.user100000);
    }

    static class Data {
        static final String CSV = IntStream.range(0, 100).mapToObj(Integer::toString).collect(Collectors.joining(","));
        static final String[] USER = new String[] {
            "Jill",
                "Johnson",
                "jill.johnson@example.com",
                "1980",
                "user,admin",
                "2018-01-01T12:34:56",
                "2018-11-07T10:11:12"};

        static final List<Integer> integers1000 = intList(1000);
        static final List<Integer> integers1000000 = intList(1000000);
        static final List<String> csv1000 = csvList(1000);
        static final List<String> csv100000 = csvList(100_000);
        static final List<String[]> user1000 = userList(1000);
        static final List<String[]> user100000 = userList(100_000);
        
        private static List<Integer> intList(int length) {
            return IntStream.range(0, length).boxed().collect(Collectors.toList());
        }

        private static List<String> csvList(int length) {
            return IntStream.range(0, length)
                .mapToObj(i -> CSV)
                .collect(Collectors.toList());
        }

        private static List<String[]> userList(int length) {
            return IntStream.range(0, length)
                .mapToObj(i -> USER)
                .collect(Collectors.toList());
        }
    }

    public static void main(final String[] args) throws RunnerException {
        final Options opt = new OptionsBuilder()
            .include(".*" + ImplementationBenchmark.class.getSimpleName() + ".*")
            .forks(1)
            .threads(1)
            .mode(Mode.Throughput)
            .timeUnit(TimeUnit.SECONDS)
            .warmupIterations(3)
            .measurementIterations(5)
            .build();
        new Runner(opt).run();
    }
}

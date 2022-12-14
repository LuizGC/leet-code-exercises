package com.leetcode.exercises.sudoku;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PossibilitiesReducerTest {

    @Test
    void testReduceHorizontally() {
        // Given
        int x = 0, y = 5;
        PossibilitiesReducer reducer = new PossibilitiesReducer();
        reducer.add(0, 0, List.of('1', '3'));
        reducer.add(0, 1, List.of('4', '6', '8'));
        reducer.add(0, 2, List.of('1', '2', '7'));
        reducer.add(0, 3, List.of('5', '7', '8'));
        reducer.add(0, 4, List.of('3'));
        reducer.add(0, 5, List.of('1', '2', '6', '7', '9'));
        reducer.add(0, 6, List.of('5', '7'));
        reducer.add(0, 7, List.of('2', '3', '8'));
        reducer.add(0, 8, List.of('6'));

        // When
        List<Character> result = reducer.reduceHorizontally(x, y);

        // Then
        assertIterableEquals(List.of('9'), result);
    }

    @Test
    void testReduceVertically() {
        // Given
        int x = 5, y = 0;
        PossibilitiesReducer reducer = new PossibilitiesReducer();
        reducer.add(0, 0, List.of('1', '3'));
        reducer.add(1, 0, List.of('4', '6', '8'));
        reducer.add(2, 0, List.of('1', '2', '7'));
        reducer.add(3, 0, List.of('5', '7', '8'));
        reducer.add(4, 0, List.of('3'));
        reducer.add(5, 0, List.of('1', '2', '6', '7', '9'));
        reducer.add(6, 0, List.of('5', '7'));
        reducer.add(7, 0, List.of('2', '3', '8'));
        reducer.add(8, 0, List.of('6'));

        // When
        List<Character> result = reducer.reduceVertically(x, y);

        // Then
        assertIterableEquals(List.of('9'), result);
    }

    @Test
    void testReduceBox() {
        // Given
        int x = 1, y = 2;
        PossibilitiesReducer reducer = new PossibilitiesReducer();
        reducer.add(0, 0, List.of('1', '3'));
        reducer.add(0, 1, List.of('4', '6', '8'));
        reducer.add(0, 2, List.of('1', '2', '7'));
        reducer.add(1, 0, List.of('5', '7', '8'));
        reducer.add(1, 1, List.of('3'));
        reducer.add(1, 2, List.of('1', '2', '6', '7', '9'));
        reducer.add(2, 0, List.of('5', '7'));
        reducer.add(2, 1, List.of('2', '3', '8'));
        reducer.add(2, 2, List.of('6'));

        // When
        List<Character> result = reducer.reduceBox(x, y);

        // Then
        assertIterableEquals(List.of('9'), result);
    }

    @ParameterizedTest
    @MethodSource("inputProvider")
    void reduce(char[][] input, int x, int y, List<Character> expected) {
        // Given
        input = Arrays.stream(input)
        .map(arr -> Arrays.copyOf(arr, arr.length))
        .toArray(char[][]::new);
        PossibilitiesMapper mapper = new PossibilitiesMapper();
        PossibilitiesReducer reducer = new PossibilitiesReducer();
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input.length; j++) {
                reducer.add(i, j, mapper.mapPossibilities(i, j, input));
            }
        }

        // When
        List<Character> result = reducer.reduce(x, y);

        // Then
        assertIterableEquals(expected, result);
    }

    private static Stream<Arguments> inputProvider() {
        char[][] inputInitial = new char[][] {
            { '5', '3', '.', '.', '7', '.', '.', '.', '.' },
            { '6', '.', '.', '1', '9', '5', '.', '.', '.' },
            { '.', '9', '8', '.', '.', '.', '.', '6', '.' },
            { '8', '.', '.', '.', '6', '.', '.', '.', '3' },
            { '4', '.', '.', '8', '.', '3', '.', '.', '1' },
            { '7', '.', '.', '.', '2', '.', '.', '.', '6' },
            { '.', '6', '.', '.', '.', '.', '2', '8', '.' },
            { '.', '.', '.', '4', '1', '9', '.', '.', '5' },
            { '.', '.', '.', '.', '8', '.', '.', '7', '9' } };
        char[][] input12Interaction = new char[][] {
            {'5', '3', '4', '6', '7', '8', '.', '.', '2'},
            {'6', '7', '2', '1', '9', '5', '3', '4', '8'},
            {'1', '9', '8', '.', '.', '.', '5', '6', '7'},
            {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
            {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
            {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
            {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
            {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
            {'.', '.', '.', '.', '8', '.', '.', '7', '9'} };

        return Stream.of(
                Arguments.of(inputInitial, 0, 2, List.of()),
                Arguments.of(inputInitial, 0, 3, List.of()),
                Arguments.of(input12Interaction, 3, 1, List.of())
                );
    }

    @Test
    void testReduceHorizontallyWhenThereIsOnlyOnePossibility() {
        // Given
        int x = 0, y = 5;
        PossibilitiesReducer reducer = new PossibilitiesReducer();
        reducer.add(0, 5, List.of('9'));
        // When
        List<Character> result = reducer.reduceHorizontally(x, y);

        // Then
        assertIterableEquals(List.of('9'), result);
    }
}

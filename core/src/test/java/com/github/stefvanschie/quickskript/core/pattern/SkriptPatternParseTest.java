package com.github.stefvanschie.quickskript.core.pattern;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
class SkriptPatternParseTest {

    @ParameterizedTest
    @ValueSource(strings = {
        "x",
        "x y",
        "x [y]",
        "[x] y",
        "[x] [y]",
        "(x|y)",
        "x (y|z)",
        "(x|y) z",
        "<.+>",
        "x <.+>",
        "<.+> y",
        "<.+> <.+>",
        "%object%",
        "x %object%",
        "%object% y",
        "[x|y]",
        "x [y|z]",
        "[x|y] z",
        "(1¦x|2¦y)",
        "x (1¦y|2¦z)",
        "(1¦x|2¦y) z",
        "[1¦x|2¦y]",
        "x [1¦y|2¦z]",
        "[1¦x|2¦y] z",
        "[x [y] z]",
        "(x|1¦y)",
        "[w (x|1¦y) z]",
        "w [x [y] z]",
        "w (x|y|z)"
    })
    void testPatternParsing(String pattern) {
        assertDoesNotThrow(() -> SkriptPattern.parse(pattern));
    }
}

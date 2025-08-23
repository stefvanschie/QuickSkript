package com.github.stefvanschie.quickskript.core.pattern;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
class SkriptPatternParseTest {

    private final Set<String> patterns = Set.of(
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
        "(1¦x|2¦y)", //broken bar characters
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
    );

    @Test
    void testPatternParsing() {
        long start = System.nanoTime();

        patterns.forEach(SkriptPattern::parse);

        long end = System.nanoTime();

        System.out.println(patterns.size() + " patterns parsed successfully in " + (end - start) / 1000000 + "ms - Avg: " + (end - start) / 1000000 / patterns.size() + "ms");
    }
}

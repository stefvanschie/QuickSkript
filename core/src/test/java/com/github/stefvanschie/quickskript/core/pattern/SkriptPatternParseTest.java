package com.github.stefvanschie.quickskript.core.pattern;

import org.junit.jupiter.api.Test;

import java.util.Set;

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
        "%x%",
        "x %y%",
        "%x% y",
        "[x|y]",
        "x [y|z]",
        "[x|y] z",
        "(1\u00A6x|2\u00A6y)", //broken bar characters
        "x (1\u00A6y|2\u00A6z)",
        "(1\u00A6x|2\u00A6y) z",
        "[1\u00A6x|2\u00A6y]",
        "x [1\u00A6y|2\u00A6z]",
        "[1\u00A6x|2\u00A6y] z",
        "[x [y] z]",
        "(x|1\u00A6y)",
        "[w (x|1\u00A6y) z]",
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

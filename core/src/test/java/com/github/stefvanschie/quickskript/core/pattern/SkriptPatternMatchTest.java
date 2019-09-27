package com.github.stefvanschie.quickskript.core.pattern;

import com.github.stefvanschie.quickskript.core.util.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SkriptPatternMatchTest {

    /**
     * A map containing skript patterns and text which should match those
     */
    private static final Map<SkriptPattern, String[]> CORRECT_PATTERNS = new HashMap<>();

    /**
     * A map containing skript patterns and text which shouldn't match those
     */
    private static final Map<SkriptPattern, String[]> INCORRECT_PATTERNS = new HashMap<>();

    /**
     * A map containing skript patterns and a correct match with the expected parse mark
     */
    private static final Map<SkriptPattern, Pair<String, Integer>> PARSE_MARKS_PATTERNS = new HashMap<>();

    @BeforeAll
    static void init() {
        //correct
        CORRECT_PATTERNS.put(SkriptPattern.parse("x"), new String[] {
            "x"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("x y"), new String[] {
            "x y"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("x[y]"), new String[] {
            "x", "xy"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("x [y]"), new String[] {
            "x", "x ", "x y"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("[x]y"), new String[] {
            "y", "xy"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("[x] y"), new String[] {
            "y", " y", "x y"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("[x][y]"), new String[] {
            "", "x", "y", "xy"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("[x] [y]"), new String[] {
            "", " ", "x", "y", "x y"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("[x|y]"), new String[] {
            "", "x", "y"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("x[y|z]"), new String[] {
            "x", "xy", "xz"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("x [y|z]"), new String[] {
            "x", "x ", "x y", "x z"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("[x|y]z"), new String[] {
            "z", "xz", "yz"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("[x|y] z"), new String[] {
            "z", " z", "x z", "y z"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("(x|y)"), new String[] {
            "x", "y"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("x(y|z)"), new String[] {
            "xy", "xz"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("x (y|z)"), new String[] {
            "x y", "x z"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("(x|y)z"), new String[] {
            "xz", "yz"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("(x|y) z"), new String[] {
            "x z", "y z"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("(w|x)(y|z)"), new String[] {
            "wy", "wz", "xy", "xz"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("(w|x) (y|z)"), new String[] {
            "w y", "w z", "x y", "x z"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("(x|y|z)"), new String[] {
            "x", "y", "z"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("%x%"), new String[] {
            "x", "y", "z"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("%x%y"), new String[] {
            "xy", "yy", "zy"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("%x% y"), new String[] {
            "x y", "y y", "z y"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("x%y%"), new String[] {
            "xx", "xy", "xz"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("x %y%"), new String[] {
            "x x", "x y", "x z"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("<.>"), new String[] {
            "x", "y", "z"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("<.>x"), new String[] {
            "xx", "yx", "zx"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("<.> x"), new String[] {
            "x x", "y x", "z x"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("x<.>"), new String[] {
            "xx", "xy", "xz"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("x <.>"), new String[] {
            "x x", "x y", "x z"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("%x% [y]"), new String[] {
            "x y", "x", "y"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("x [y] z"), new String[] {
            "x y z", "x z"
        });
        //noinspection HardcodedFileSeparator
        CORRECT_PATTERNS.put(SkriptPattern.parse("%w% (x|y) \\[<.+>\\]"), new String[] {
            "w x [z]", "w y [z]"
        });
        CORRECT_PATTERNS.put(SkriptPattern.parse("[%*type%] input"), new String[] {
            "input"
        });

        //incorrect
        INCORRECT_PATTERNS.put(SkriptPattern.parse("x"), new String[] {
            " ", "y"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("x y"), new String[] {
            "xy", "x", "y"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("x[y]"), new String[] {
            "x y", "y"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("x [y]"), new String[] {
            "xy", "y"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("[x]y"), new String[] {
            "x y", "x"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("[x] y"), new String[] {
            "xy", "x"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("[x][y]"), new String[] {
            "x y"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("[x] [y]"), new String[] {
            "xy"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("[x|y]"), new String[] {
            "xy"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("x[y|z]"), new String[] {
            "xyz", "x yz", "x y", "x z"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("x [y|z]"), new String[] {
            "xyz", "x yz", "xy", "xz"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("[x|y]z"), new String[] {
            "xyz", "xy z", "x z", "y z"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("[x|y] z"), new String[] {
            "xyz", "xy z", "xz", "yz"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("(x|y)"), new String[] {
            "", "xy"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("x(y|z)"), new String[] {
            "xyz", "x yz", "x y", "x z"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("x (y|z)"), new String[] {
            "xyz", "x yz", "xy", "xz"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("(x|y)z"), new String[] {
            "xyz", "xy z", "x z", "y z"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("(x|y) z"), new String[] {
            "xyz", "xy z", "xz", "yz"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("(w|x)(y|z)"), new String[] {
            "wxyz", "wxy", "wxz", "wyz", "xyz", "wx yz", "wx y", "wx z", "w yz", "x yz", "w y", "w z", "x y", "x z"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("(w|x) (y|z)"), new String[] {
            "wxyz", "wxy", "wxz", "wyz", "xyz", "wx yz", "wx y", "wx z", "w yz", "x yz", "wy", "wz", "xy", "xz"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("(x|y|z)"), new String[] {
            "xyz", "xy", "xz", "yz"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("%x%y"), new String[] {
            "x", "z"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("%x% y"), new String[] {
            "xy", "yy", "zy"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("x%y%"), new String[] {
            "y", "z"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("x %y%"), new String[] {
            "xx", "xy", "xz"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("<.>"), new String[] {
            "", "xx"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("<.>x"), new String[] {
            "y", "z", "xxx"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("<.> x"), new String[] {
            "xx", "yx", "zx", "x xx"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("x<.>"), new String[] {
            "x x", "x y", "x z", "xxx"
        });
        INCORRECT_PATTERNS.put(SkriptPattern.parse("x <.>"), new String[] {
            "xx", "xy", "xz", "xx x"
        });

        //parse marks
        PARSE_MARKS_PATTERNS.put(SkriptPattern.parse("[1\u00A6x|2\u00A6y]"), new Pair<>("", 0));
        PARSE_MARKS_PATTERNS.put(SkriptPattern.parse("[1\u00A6x|2\u00A6y]"), new Pair<>("x", 1));
        PARSE_MARKS_PATTERNS.put(SkriptPattern.parse("[1\u00A6x|2\u00A6y]"), new Pair<>("y", 2));
        PARSE_MARKS_PATTERNS.put(SkriptPattern.parse("[1\u00A6w|2\u00A6x][3\u00A6y|4\u00A6z]"), new Pair<>("wy", 1 ^ 3));
        PARSE_MARKS_PATTERNS.put(SkriptPattern.parse("[1\u00A6w|2\u00A6x][3\u00A6y|4\u00A6z]"), new Pair<>("wz", 1 ^ 4));
        PARSE_MARKS_PATTERNS.put(SkriptPattern.parse("[1\u00A6w|2\u00A6x][3\u00A6y|4\u00A6z]"), new Pair<>("xy", 2 ^ 3));
        PARSE_MARKS_PATTERNS.put(SkriptPattern.parse("[1\u00A6w|2\u00A6x][3\u00A6y|4\u00A6z]"), new Pair<>("xz", 2 ^ 4));
        PARSE_MARKS_PATTERNS.put(SkriptPattern.parse("(1\u00A6x|2\u00A6y)"), new Pair<>("x", 1));
        PARSE_MARKS_PATTERNS.put(SkriptPattern.parse("(1\u00A6x|2\u00A6y)"), new Pair<>("y", 2));
        PARSE_MARKS_PATTERNS.put(SkriptPattern.parse("(1\u00A6w|2\u00A6x)(3\u00A6y|4\u00A6z)"), new Pair<>("wy", 1 ^ 3));
        PARSE_MARKS_PATTERNS.put(SkriptPattern.parse("(1\u00A6w|2\u00A6x)(3\u00A6y|4\u00A6z)"), new Pair<>("wz", 1 ^ 4));
        PARSE_MARKS_PATTERNS.put(SkriptPattern.parse("(1\u00A6w|2\u00A6x)(3\u00A6y|4\u00A6z)"), new Pair<>("xy", 2 ^ 3));
        PARSE_MARKS_PATTERNS.put(SkriptPattern.parse("(1\u00A6w|2\u00A6x)(3\u00A6y|4\u00A6z)"), new Pair<>("xz", 2 ^ 4));
    }

    @Test
    void testCorrectPatternMatching() {
        CORRECT_PATTERNS.forEach((pattern, correct) -> {
            for (String string : correct) {
                assertTrue(pattern.match(string).stream().anyMatch(match -> !match.hasUnmatchedParts()));
            }
        });
    }

    @Test
    void testFailingPatternMatching() {
        INCORRECT_PATTERNS.forEach((pattern, correct) -> {
            for (String string : correct) {
                assertTrue(pattern.match(string).stream().allMatch(SkriptMatchResult::hasUnmatchedParts));
            }
        });
    }

    @Test
    void testParseMarks() {
        PARSE_MARKS_PATTERNS.forEach((pattern, pair) -> assertTrue(pattern.match(pair.getX())
                .stream().anyMatch(match -> match.getParseMark() == pair.getY())));
    }
}

package com.github.stefvanschie.quickskript.core.pattern;

import com.github.stefvanschie.quickskript.core.SkriptPatternConverter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
class SkriptPatternMatchTest {

    /**
     * A collection containing skript patterns and text which should match those
     */
    private static final Collection<Arguments> CORRECT_PATTERNS = new HashSet<>();

    /**
     * A collection containing skript patterns and text which shouldn't match those
     */
    private static final Collection<Arguments> INCORRECT_PATTERNS = new HashSet<>();

    /**
     * A collection containing skript patterns and a correct match with the expected parse mark
     */
    private static final Collection<Arguments> PARSE_MARKS_PATTERNS = new HashSet<>();

    @BeforeAll
    static void init() {
        //correct
        CORRECT_PATTERNS.add(Arguments.of("x", new String[] {
            "x"
        }));
        CORRECT_PATTERNS.add(Arguments.of("x y", new String[] {
            "x y"
        }));
        CORRECT_PATTERNS.add(Arguments.of("x[y]", new String[] {
            "x", "xy"
        }));
        CORRECT_PATTERNS.add(Arguments.of("x [y]", new String[] {
            "x", "x ", "x y"
        }));
        CORRECT_PATTERNS.add(Arguments.of("[x]y", new String[] {
            "y", "xy"
        }));
        CORRECT_PATTERNS.add(Arguments.of("[x] y", new String[] {
            "y", " y", "x y"
        }));
        CORRECT_PATTERNS.add(Arguments.of("[x][y]", new String[] {
            "", "x", "y", "xy"
        }));
        CORRECT_PATTERNS.add(Arguments.of("[x] [y]", new String[] {
            "", " ", "x", "y", "x y"
        }));
        CORRECT_PATTERNS.add(Arguments.of("[x|y]", new String[] {
            "", "x", "y"
        }));
        CORRECT_PATTERNS.add(Arguments.of("x[y|z]", new String[] {
            "x", "xy", "xz"
        }));
        CORRECT_PATTERNS.add(Arguments.of("x [y|z]", new String[] {
            "x", "x ", "x y", "x z"
        }));
        CORRECT_PATTERNS.add(Arguments.of("[x|y]z", new String[] {
            "z", "xz", "yz"
        }));
        CORRECT_PATTERNS.add(Arguments.of("[x|y] z", new String[] {
            "z", " z", "x z", "y z"
        }));
        CORRECT_PATTERNS.add(Arguments.of("(x|y)", new String[] {
            "x", "y"
        }));
        CORRECT_PATTERNS.add(Arguments.of("x(y|z)", new String[] {
            "xy", "xz"
        }));
        CORRECT_PATTERNS.add(Arguments.of("x (y|z)", new String[] {
            "x y", "x z"
        }));
        CORRECT_PATTERNS.add(Arguments.of("(x|y)z", new String[] {
            "xz", "yz"
        }));
        CORRECT_PATTERNS.add(Arguments.of("(x|y) z", new String[] {
            "x z", "y z"
        }));
        CORRECT_PATTERNS.add(Arguments.of("(w|x)(y|z)", new String[] {
            "wy", "wz", "xy", "xz"
        }));
        CORRECT_PATTERNS.add(Arguments.of("(w|x) (y|z)", new String[] {
            "w y", "w z", "x y", "x z"
        }));
        CORRECT_PATTERNS.add(Arguments.of("(x|y|z)", new String[] {
            "x", "y", "z"
        }));
        CORRECT_PATTERNS.add(Arguments.of("%object%", new String[] {
            "x", "y", "z"
        }));
        CORRECT_PATTERNS.add(Arguments.of("%object%y", new String[] {
            "xy", "yy", "zy"
        }));
        CORRECT_PATTERNS.add(Arguments.of("%object% y", new String[] {
            "x y", "y y", "z y"
        }));
        CORRECT_PATTERNS.add(Arguments.of("x%object%", new String[] {
            "xx", "xy", "xz"
        }));
        CORRECT_PATTERNS.add(Arguments.of("x %object%", new String[] {
            "x x", "x y", "x z"
        }));
        CORRECT_PATTERNS.add(Arguments.of("<.>", new String[] {
            "x", "y", "z"
        }));
        CORRECT_PATTERNS.add(Arguments.of("<.>x", new String[] {
            "xx", "yx", "zx"
        }));
        CORRECT_PATTERNS.add(Arguments.of("<.> x", new String[] {
            "x x", "y x", "z x"
        }));
        CORRECT_PATTERNS.add(Arguments.of("x<.>", new String[] {
            "xx", "xy", "xz"
        }));
        CORRECT_PATTERNS.add(Arguments.of("x <.>", new String[] {
            "x x", "x y", "x z"
        }));
        CORRECT_PATTERNS.add(Arguments.of("%object% [y]", new String[] {
            "x y", "x", "y"
        }));
        CORRECT_PATTERNS.add(Arguments.of("x [y] z", new String[] {
            "x y z", "x z"
        }));
        //noinspection HardcodedFileSeparator
        CORRECT_PATTERNS.add(Arguments.of("%object% (x|y) \\[<.+>\\]", new String[] {
            "w x [z]", "w y [z]"
        }));
        CORRECT_PATTERNS.add(Arguments.of("[%*object%] input", new String[] {
            "input"
        }));
        CORRECT_PATTERNS.add(Arguments.of("([a]|[b]) c", new String[] {
            "a c", "b c", "c"
        }));

        //incorrect
        INCORRECT_PATTERNS.add(Arguments.of("x", new String[] {
            " ", "y"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("x y", new String[] {
            "xy", "x", "y"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("x[y]", new String[] {
            "x y", "y"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("x [y]", new String[] {
            "xy", "y"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("[x]y", new String[] {
            "x y", "x"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("[x] y", new String[] {
            "xy", "x"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("[x][y]", new String[] {
            "x y"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("[x] [y]", new String[] {
            "xy"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("[x|y]", new String[] {
            "xy"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("x[y|z]", new String[] {
            "xyz", "x yz", "x y", "x z"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("x [y|z]", new String[] {
            "xyz", "x yz", "xy", "xz"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("[x|y]z", new String[] {
            "xyz", "xy z", "x z", "y z"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("[x|y] z", new String[] {
            "xyz", "xy z", "xz", "yz"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("(x|y)", new String[] {
            "", "xy"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("x(y|z)", new String[] {
            "xyz", "x yz", "x y", "x z"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("x (y|z)", new String[] {
            "xyz", "x yz", "xy", "xz"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("(x|y)z", new String[] {
            "xyz", "xy z", "x z", "y z"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("(x|y) z", new String[] {
            "xyz", "xy z", "xz", "yz"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("(w|x)(y|z)", new String[] {
            "wxyz", "wxy", "wxz", "wyz", "xyz", "wx yz", "wx y", "wx z", "w yz", "x yz", "w y", "w z", "x y", "x z"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("(w|x) (y|z)", new String[] {
            "wxyz", "wxy", "wxz", "wyz", "xyz", "wx yz", "wx y", "wx z", "w yz", "x yz", "wy", "wz", "xy", "xz"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("(x|y|z)", new String[] {
            "xyz", "xy", "xz", "yz"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("%object%y", new String[] {
            "x", "z"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("%object% y", new String[] {
            "xy", "yy", "zy"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("x%object%", new String[] {
            "y", "z"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("x %object%", new String[] {
            "xx", "xy", "xz"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("<.>", new String[] {
            "", "xx"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("<.>x", new String[] {
            "y", "z", "xxx"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("<.> x", new String[] {
            "xx", "yx", "zx", "x xx"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("x<.>", new String[] {
            "x x", "x y", "x z", "xxx"
        }));
        INCORRECT_PATTERNS.add(Arguments.of("x <.>", new String[] {
            "xx", "xy", "xz", "xx x"
        }));

        //parse marks
        PARSE_MARKS_PATTERNS.add(Arguments.of("[1¦x|2¦y]", "", 0));
        PARSE_MARKS_PATTERNS.add(Arguments.of("[1¦x|2¦y]", "x", 1));
        PARSE_MARKS_PATTERNS.add(Arguments.of("[1¦x|2¦y]", "y", 2));
        PARSE_MARKS_PATTERNS.add(Arguments.of("[1¦w|2¦x][3¦y|4¦z]", "wy", 1 ^ 3));
        PARSE_MARKS_PATTERNS.add(Arguments.of("[1¦w|2¦x][3¦y|4¦z]", "wz", 1 ^ 4));
        PARSE_MARKS_PATTERNS.add(Arguments.of("[1¦w|2¦x][3¦y|4¦z]", "xy", 2 ^ 3));
        PARSE_MARKS_PATTERNS.add(Arguments.of("[1¦w|2¦x][3¦y|4¦z]", "xz", 2 ^ 4));
        PARSE_MARKS_PATTERNS.add(Arguments.of("(1¦x|2¦y)", "x", 1));
        PARSE_MARKS_PATTERNS.add(Arguments.of("(1¦x|2¦y)", "y", 2));
        PARSE_MARKS_PATTERNS.add(Arguments.of("(1¦w|2¦x)(3¦y|4¦z)", "wy", 1 ^ 3));
        PARSE_MARKS_PATTERNS.add(Arguments.of("(1¦w|2¦x)(3¦y|4¦z)", "wz", 1 ^ 4));
        PARSE_MARKS_PATTERNS.add(Arguments.of("(1¦w|2¦x)(3¦y|4¦z)", "xy", 2 ^ 3));
        PARSE_MARKS_PATTERNS.add(Arguments.of("(1¦w|2¦x)(3¦y|4¦z)", "xz", 2 ^ 4));
    }

    @ParameterizedTest
    @FieldSource("CORRECT_PATTERNS")
    void testCorrectPatternMatching(
        @ConvertWith(SkriptPatternConverter.class) SkriptPattern pattern,
        String[] correct
    ) {
        for (String string : correct) {
            assertTrue(pattern.match(string).stream().anyMatch(match -> !match.hasUnmatchedParts()));
        }
    }

    @ParameterizedTest
    @FieldSource("INCORRECT_PATTERNS")
    void testFailingPatternMatching(
        @ConvertWith(SkriptPatternConverter.class) SkriptPattern pattern,
        String[] correct
    ) {
        for (String string : correct) {
            assertTrue(pattern.match(string).stream().allMatch(SkriptMatchResult::hasUnmatchedParts));
        }
    }

    @ParameterizedTest
    @FieldSource("PARSE_MARKS_PATTERNS")
    void testParseMarks(@ConvertWith(SkriptPatternConverter.class) SkriptPattern pattern, String input, int parseMark) {
        assertTrue(pattern.match(input).stream().anyMatch(match -> match.getParseMark() == parseMark));
    }
}

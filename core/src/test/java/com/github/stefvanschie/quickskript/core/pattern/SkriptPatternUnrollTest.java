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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
class SkriptPatternUnrollTest {

    private static final Collection<Arguments> PATTERNS = new HashSet<>();

    @BeforeAll
    static void init() {
        PATTERNS.add(Arguments.of("x", Set.of("x")));
        PATTERNS.add(Arguments.of("(x|y)", Set.of("x", "y")));
        PATTERNS.add(Arguments.of("[x]", Set.of("x", "")));
        PATTERNS.add(Arguments.of(" ", Set.of(" ")));
        PATTERNS.add(Arguments.of("x [y]", Set.of("x y", "x")));
        PATTERNS.add(Arguments.of("[x] y", Set.of("x y", "y")));
        PATTERNS.add(Arguments.of("a[ ]b", Set.of("ab", "a b")));
        PATTERNS.add(Arguments.of("x[y] [z]", Set.of("x", "xy", "xy z", "x z")));
        PATTERNS.add(Arguments.of("w[x] [y] z", Set.of("w z", "wx z", "w y z", "wx y z")));
        PATTERNS.add(Arguments.of("[a] b c [d] e", Set.of("a b c d e", "a b c e", "b c d e", "b c e")));
        PATTERNS.add(Arguments.of("[a] [b] c", Set.of("a b c", "a c", "b c", "c")));
        PATTERNS.add(Arguments.of("([a]|[b]) c", Set.of("a c", "b c", "c")));
        PATTERNS.add(Arguments.of("a [b] c", Set.of("a b c", "a c")));
    }

    @ParameterizedTest
    @FieldSource("PATTERNS")
    void test(@ConvertWith(SkriptPatternConverter.class) SkriptPattern pattern, Set<?> correct) {
        Collection<?> actual = pattern.unrollFully();

        assertTrue(actual.containsAll(correct) && correct.containsAll(actual));
    }
}

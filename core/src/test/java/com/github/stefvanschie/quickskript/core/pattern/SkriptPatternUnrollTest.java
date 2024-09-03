package com.github.stefvanschie.quickskript.core.pattern;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
class SkriptPatternUnrollTest {

    @Test
    void test() {
        assertTrue(collectionsEqual(Set.of("x"), SkriptPattern.parse("x").unrollFully()));
        assertTrue(collectionsEqual(Set.of("x", "y"), SkriptPattern.parse("(x|y)").unrollFully()));
        assertTrue(collectionsEqual(Set.of("x", ""), SkriptPattern.parse("[x]").unrollFully()));
        assertTrue(collectionsEqual(Set.of(" "), SkriptPattern.parse(" ").unrollFully()));

        assertTrue(collectionsEqual(Set.of("x y", "x"), SkriptPattern.parse("x [y]").unrollFully()));
        assertTrue(collectionsEqual(Set.of("x y", "y"), SkriptPattern.parse("[x] y").unrollFully()));

        assertTrue(collectionsEqual(Set.of("ab", "a b"), SkriptPattern.parse("a[ ]b").unrollFully()));

        assertTrue(collectionsEqual(Set.of("x", "xy", "xy z", "x z"), SkriptPattern.parse("x[y] [z]").unrollFully()));

        assertTrue(collectionsEqual(Set.of(
            "w z",
            "wx z",
            "w y z",
            "wx y z"
        ), SkriptPattern.parse("w[x] [y] z").unrollFully()));

        assertTrue(collectionsEqual(Set.of(
            "a b c d e",
            "a b c e",
            "b c d e",
            "b c e"
        ), SkriptPattern.parse("[a] b c [d] e").unrollFully()));

        assertTrue(collectionsEqual(Set.of(
            "a b c",
            "a c",
            "b c",
            "c"
        ), SkriptPattern.parse("[a] [b] c").unrollFully()));

        assertTrue(collectionsEqual(Set.of(
            "a c", "b c", "c"
        ), SkriptPattern.parse("([a]|[b]) c").unrollFully()));
    }

    boolean collectionsEqual(Collection<?> setA, Collection<?> setB) {
        for (Object object : setA) {
            if (!setB.contains(object)) {
                return false;
            }
        }

        return true;
    }
}

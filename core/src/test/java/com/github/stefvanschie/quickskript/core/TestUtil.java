package com.github.stefvanschie.quickskript.core;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Utility class for testing.
 */
public class TestUtil {

    /**
     * Private constructor to prevent creation.
     */
    private TestUtil() {}

    /**
     * Asserts that the provided value is within a specified range. The range is inclusive on both ends. If the value is
     * not within the range, the test from which this method is called will fail.
     *
     * @param minimum the minimum value
     * @param actual the actual value to test
     * @param maximum the maximum value
     * @since 0.1.0
     */
    public static void assertInRange(double minimum, double actual, double maximum) {
        assertTrue(minimum <= actual);
        assertTrue(actual <= maximum);
    }
}

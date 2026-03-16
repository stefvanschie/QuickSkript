package com.github.stefvanschie.quickskript.core.psi.util;

import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.connective.Conjunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MultiResultTest {

    @Test
    void emptyConstructor() {
        assertEquals(0, new MultiResult<>().getSize());
    }

    @Test
    void singletonConstructor() {
        assertEquals(1, new MultiResult<>(0).getSize());
    }

    @Test
    void arrayConstructor() {
        assertEquals(0, new MultiResult<>(new Integer[0], Conjunction.INSTANCE).getSize());
    }

    @Test
    void zip() {
        MultiResult<Integer> zipped1 = new MultiResult<>(0).zip(new MultiResult<>(0), (a, b) -> a);

        assertEquals(1, zipped1.getSize());
        assertTrue(zipped1.contains(0));

        MultiResult<Integer> zipped2 = new MultiResult<>(0).zip(new MultiResult<>(0), (a, b) -> b);

        assertEquals(1, zipped2.getSize());
        assertTrue(zipped2.contains(0));
    }

    @Test
    void map() {
        MultiResult<Integer> mapped = new MultiResult<>(0).map(a -> a + 1);

        assertEquals(1, mapped.getSize());
        assertTrue(mapped.contains(1));
    }

    @Test
    void test() {
        assertTrue(new MultiResult<>(0).test(a -> a == 0));
    }

    @Test
    void contains() {
        assertTrue(new MultiResult<>(0).contains(0));
    }

    @Test
    void getSize() {
        assertEquals(0, new MultiResult<>().getSize());
    }
}

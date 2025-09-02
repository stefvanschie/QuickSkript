package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import com.github.stefvanschie.quickskript.core.util.literal.direction.AbsoluteDirection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PsiAbsoluteDirectionExpressionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @MethodSource("provideValues")
    void test(String input, Object result) {
        PsiElement<?> element = loader.tryParseElement(input, -1);

        assertNotNull(element);
        assertInstanceOf(PsiAbsoluteDirectionExpression.class, element);
        assertEquals(result, element.execute(null, null));
    }

    private static Stream<Arguments> provideValues() {
        return Stream.of(
            Arguments.of("eastward of", new AbsoluteDirection(1, 0, 0)),
            Arguments.of("south over 6 block downward", new AbsoluteDirection(0, -5, 1)),
            Arguments.of("2 over", new AbsoluteDirection(0, 2, 0)),
            Arguments.of("8 beneath over", new AbsoluteDirection(0, -7, 0))
        );
    }
}

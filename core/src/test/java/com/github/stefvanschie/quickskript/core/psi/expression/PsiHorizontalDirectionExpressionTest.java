package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import com.github.stefvanschie.quickskript.core.util.literal.direction.HorizontalRelativeDirection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PsiHorizontalDirectionExpressionTest {

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
        assertInstanceOf(PsiHorizontalDirectionExpression.class, element);
        assertEquals(result, element.execute(null, null));
    }

    private static Stream<Arguments> provideValues() {
        return Stream.of(
            Arguments.of("9 horizontal to the left", new HorizontalRelativeDirection(-90, 9)),
            Arguments.of("horizontally in front", new HorizontalRelativeDirection(0, 1))
        );
    }
}

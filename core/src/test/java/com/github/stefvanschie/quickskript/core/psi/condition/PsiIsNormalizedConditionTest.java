package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsNormalizedConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "vector(0, 0, 0) is normalized",
        "vector(0, 0, 0) are normalized",
        "vector(0, 0, 0) isn't normalized",
        "vector(0, 0, 0) is not normalized",
        "vector(0, 0, 0) aren't normalized",
        "vector(0, 0, 0) are not normalized"
    })
    void test(String input) {
        assertInstanceOf(PsiIsNormalizedCondition.class, loader.tryParseElement(input, -1));
    }
}

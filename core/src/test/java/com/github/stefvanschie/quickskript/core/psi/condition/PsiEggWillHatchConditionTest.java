package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiEggWillHatchConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void test() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "the egg will hatch",
        "egg will hatch",
        "the egg will not hatch",
        "the egg won't hatch",
        "egg will not hatch",
        "egg won't hatch"
    })
    void test(String input) {
        assertInstanceOf(PsiEggWillHatchCondition.class, loader.tryParseElement(input, -1));
    }
}

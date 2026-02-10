package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsInfiniteConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "44:35 is infinite",
        "27:48 are infinite",
        "59:10 isn't infinite",
        "28:07 is not infinite",
        "30:36 aren't infinite",
        "21:48 are not infinite"
    })
    void test(String input) {
        assertInstanceOf(PsiIsInfiniteCondition.class, loader.tryParseElement(input, -1));
    }
}

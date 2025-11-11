package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsFishhookInOpenWaterConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "fish hook is in open waters",
        "fish hook is in open water",
        "fish hook are in open waters",
        "fish hook are in open water",
        "fish hook isn't in open waters",
        "fish hook isn't in open water",
        "fish hook is not in open waters",
        "fish hook is not in open water",
        "fish hook aren't in open waters",
        "fish hook aren't in open water",
        "fish hook are not in open waters",
        "fish hook are not in open water",
    })
    void test(String input) {
        assertInstanceOf(PsiIsFishhookInOpenWaterCondition.class, loader.tryParseElement(input, -1));
    }
}

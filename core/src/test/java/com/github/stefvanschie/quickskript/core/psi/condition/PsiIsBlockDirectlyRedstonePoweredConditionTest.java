package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsBlockDirectlyRedstonePoweredConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "block is redstone powered",
        "block are redstone powered",
        "block isn't redstone powered",
        "block is not redstone powered",
        "block aren't redstone powered",
        "block are not redstone powered"
    })
    void test(String input) {
        Object element = loader.tryParseElement(input, -1);

        assertInstanceOf(PsiIsBlockDirectlyRedstonePoweredCondition.class, element);
    }
}

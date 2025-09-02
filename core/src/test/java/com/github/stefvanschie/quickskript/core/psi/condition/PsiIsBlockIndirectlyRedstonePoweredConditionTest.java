package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsBlockIndirectlyRedstonePoweredConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "block is indirectly redstone powered",
        "block are indirectly redstone powered",
        "block isn't indirectly redstone powered",
        "block is not indirectly redstone powered",
        "block aren't indirectly redstone powered",
        "block are not indirectly redstone powered"
    })
    void test(String input) {
        Object element = loader.tryParseElement(input, -1);

        assertInstanceOf(PsiIsBlockIndirectlyRedstonePoweredCondition.class, element);
    }
}

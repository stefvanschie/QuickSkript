package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiBlockIsPersistentConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "block is persistent",
        "block are persistent",
        "block isn't persistent",
        "block is not persistent",
        "block aren't persistent",
        "block are not persistent"
    })
    void test(String input) {
        assertInstanceOf(PsiBlockIsPersistentCondition.class, loader.tryParseElement(input, -1));
    }
}

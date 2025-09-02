package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsSlotEmptyConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "hotbar slot is empty",
        "hotbar slot are empty",
        "hotbar slot isn't empty",
        "hotbar slot is not empty",
        "hotbar slot aren't empty",
        "hotbar slot are not empty"
    })
    void test(String input) {
        assertInstanceOf(PsiIsSlotEmptyCondition.class, loader.tryParseElement(input, -1));
    }
}

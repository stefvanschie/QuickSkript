package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsInventoryEmptyConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "clicked inventory is empty",
        "clicked inventory are empty",
        "clicked inventory isn't empty",
        "clicked inventory is not empty",
        "clicked inventory aren't empty",
        "clicked inventory are not empty"
    })
    void test(String input) {
        assertInstanceOf(PsiIsInventoryEmptyCondition.class, loader.tryParseElement(input, -1));
    }
}

package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiCanPickUpItemsConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "player can pick up items",
        "player can pickup items",
        "player can pick items up",
        "player can't pick up items",
        "player cannot pick up items",
        "player can not pick up items",
        "player can't pickup items",
        "player cannot pickup items",
        "player can not pickup items",
        "player can't pick items up",
        "player cannot pick items up",
        "player can not pick items up"
    })
    void test(String input) {
        assertInstanceOf(PsiCanPickUpItemsCondition.class, loader.tryParseElement(input, -1));
    }
}

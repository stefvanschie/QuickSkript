package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsEdibleConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "flint is edible",
        "iron pickaxe are edible",
        "potions isn't edible",
        "potted jungle sapling is not edible",
        "birch planks aren't edible",
        "leather horse armor are not edible"
    })
    void test(String input) {
        assertInstanceOf(PsiIsEdibleCondition.class, loader.tryParseElement(input, -1));
    }
}

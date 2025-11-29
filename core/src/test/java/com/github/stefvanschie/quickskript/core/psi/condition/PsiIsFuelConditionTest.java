package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsFuelConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "blaze rod is furnace fuel",
        "cherry fence gate is fuel",
        "diamond pickaxe are furnace fuel",
        "white concrete are fuel",
        "nautilus shell isn't furnace fuel",
        "turtle scute isn't fuel",
        "prize pottery sherd is not furnace fuel",
        "yellow shulker box is not fuel",
        "ghast spawn egg aren't furnace fuel",
        "spruce trapdoor aren't fuel",
        "shelter pottery sherd are not furnace fuel",
        "disc fragment 5 are not fuel"
    })
    void test(String input) {
        assertInstanceOf(PsiIsFuelCondition.class, loader.tryParseElement(input, -1));
    }
}

package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiHotbarSlotExpressionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "the currently selected hotbar slots of the player",
        "the currently selected hotbar slots",
        "the currently selected hotbar slot of the player",
        "the currently selected hotbar slot",
        "the selected hotbar slots of the player",
        "the selected hotbar slots",
        "the selected hotbar slot of the player",
        "the selected hotbar slot",
        "the current hotbar slots of the player",
        "the current hotbar slots",
        "the current hotbar slot of the player",
        "the current hotbar slot",
        "the hotbar slots of the player",
        "the hotbar slots",
        "the hotbar slot of the player",
        "the hotbar slot",
        "currently selected hotbar slots of the player",
        "currently selected hotbar slots",
        "currently selected hotbar slot of the player",
        "currently selected hotbar slot",
        "selected hotbar slots of the player",
        "selected hotbar slots",
        "selected hotbar slot of the player",
        "selected hotbar slot",
        "current hotbar slots of the player",
        "current hotbar slots",
        "current hotbar slot of the player",
        "current hotbar slot",
        "hotbar slots of the player",
        "hotbar slots",
        "hotbar slot of the player",
        "hotbar slot",
        "the player's currently selected hotbar slots",
        "the player's currently selected hotbar slot",
        "the player's selected hotbar slots",
        "the player's selected hotbar slot",
        "the player's current hotbar slots",
        "the player's current hotbar slot",
        "the player's hotbar slots",
        "the player's hotbar slot",
        "the player' currently selected hotbar slots",
        "the player' currently selected hotbar slot",
        "the player' selected hotbar slots",
        "the player' selected hotbar slot",
        "the player' current hotbar slots",
        "the player' current hotbar slot",
        "the player' hotbar slots",
        "the player' hotbar slot"
    })
    void test(String input) {
        assertInstanceOf(PsiHotbarSlotExpression.class, loader.tryParseElement(input, -1));
    }
}

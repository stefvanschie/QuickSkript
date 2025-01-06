package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiHotbarSlotExpressionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "the currently selected hotbar slots of the player");
        test(skriptLoader, "the currently selected hotbar slots");
        test(skriptLoader, "the currently selected hotbar slot of the player");
        test(skriptLoader, "the currently selected hotbar slot");
        test(skriptLoader, "the selected hotbar slots of the player");
        test(skriptLoader, "the selected hotbar slots");
        test(skriptLoader, "the selected hotbar slot of the player");
        test(skriptLoader, "the selected hotbar slot");
        test(skriptLoader, "the current hotbar slots of the player");
        test(skriptLoader, "the current hotbar slots");
        test(skriptLoader, "the current hotbar slot of the player");
        test(skriptLoader, "the current hotbar slot");
        test(skriptLoader, "the hotbar slots of the player");
        test(skriptLoader, "the hotbar slots");
        test(skriptLoader, "the hotbar slot of the player");
        test(skriptLoader, "the hotbar slot");
        test(skriptLoader, "currently selected hotbar slots of the player");
        test(skriptLoader, "currently selected hotbar slots");
        test(skriptLoader, "currently selected hotbar slot of the player");
        test(skriptLoader, "currently selected hotbar slot");
        test(skriptLoader, "selected hotbar slots of the player");
        test(skriptLoader, "selected hotbar slots");
        test(skriptLoader, "selected hotbar slot of the player");
        test(skriptLoader, "selected hotbar slot");
        test(skriptLoader, "current hotbar slots of the player");
        test(skriptLoader, "current hotbar slots");
        test(skriptLoader, "current hotbar slot of the player");
        test(skriptLoader, "current hotbar slot");
        test(skriptLoader, "hotbar slots of the player");
        test(skriptLoader, "hotbar slots");
        test(skriptLoader, "hotbar slot of the player");
        test(skriptLoader, "hotbar slot");

        test(skriptLoader, "the player's currently selected hotbar slots");
        test(skriptLoader, "the player's currently selected hotbar slot");
        test(skriptLoader, "the player's selected hotbar slots");
        test(skriptLoader, "the player's selected hotbar slot");
        test(skriptLoader, "the player's current hotbar slots");
        test(skriptLoader, "the player's current hotbar slot");
        test(skriptLoader, "the player's hotbar slots");
        test(skriptLoader, "the player's hotbar slot");
        test(skriptLoader, "the player' currently selected hotbar slots");
        test(skriptLoader, "the player' currently selected hotbar slot");
        test(skriptLoader, "the player' selected hotbar slots");
        test(skriptLoader, "the player' selected hotbar slot");
        test(skriptLoader, "the player' current hotbar slots");
        test(skriptLoader, "the player' current hotbar slot");
        test(skriptLoader, "the player' hotbar slots");
        test(skriptLoader, "the player' hotbar slot");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiHotbarSlotExpression);
    }
}

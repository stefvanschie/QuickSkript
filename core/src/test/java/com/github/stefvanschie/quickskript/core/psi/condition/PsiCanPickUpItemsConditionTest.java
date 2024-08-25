package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiCanPickUpItemsConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "player can pick up items");
        test(skriptLoader, "player can pickup items");
        test(skriptLoader, "player can pick items up");

        test(skriptLoader, "player can't pick up items");
        test(skriptLoader, "player cannot pick up items");
        test(skriptLoader, "player can not pick up items");
        test(skriptLoader, "player can't pickup items");
        test(skriptLoader, "player cannot pickup items");
        test(skriptLoader, "player can not pickup items");
        test(skriptLoader, "player can't pick items up");
        test(skriptLoader, "player cannot pick items up");
        test(skriptLoader, "player can not pick items up");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiCanPickUpItemsCondition);
    }
}

package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiIsSlotEmptyConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "hotbar slot is empty");
        test(skriptLoader, "hotbar slot are empty");

        test(skriptLoader, "hotbar slot isn't empty");
        test(skriptLoader, "hotbar slot is not empty");
        test(skriptLoader, "hotbar slot aren't empty");
        test(skriptLoader, "hotbar slot are not empty");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiIsSlotEmptyCondition);
    }
}

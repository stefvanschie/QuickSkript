package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiIsEdibleConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "flint is edible");
        test(skriptLoader, "iron pickaxe are edible");

        test(skriptLoader, "potions isn't edible");
        test(skriptLoader, "potted jungle sapling is not edible");
        test(skriptLoader, "birch planks aren't edible");
        test(skriptLoader, "leather horse armor are not edible");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiIsEdibleCondition);
    }
}

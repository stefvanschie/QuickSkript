package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiIsBlockDirectlyRedstonePoweredConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "block is redstone powered");
        test(skriptLoader, "block are redstone powered");

        test(skriptLoader, "block isn't redstone powered");
        test(skriptLoader, "block is not redstone powered");
        test(skriptLoader, "block aren't redstone powered");
        test(skriptLoader, "block are not redstone powered");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiIsBlockDirectlyRedstonePoweredCondition);
    }
}

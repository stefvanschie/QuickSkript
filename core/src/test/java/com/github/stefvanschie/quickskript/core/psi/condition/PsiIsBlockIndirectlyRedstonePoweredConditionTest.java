package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiIsBlockIndirectlyRedstonePoweredConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "block is indirectly redstone powered");
        test(skriptLoader, "block are indirectly redstone powered");

        test(skriptLoader, "block isn't indirectly redstone powered");
        test(skriptLoader, "block is not indirectly redstone powered");
        test(skriptLoader, "block aren't indirectly redstone powered");
        test(skriptLoader, "block are not indirectly redstone powered");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiIsBlockIndirectlyRedstonePoweredCondition);
    }
}

package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiHasAIConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "the player has ai");
        test(skriptLoader, "the player has artificial intelligence");
        test(skriptLoader, "the player have ai");
        test(skriptLoader, "the player have artificial intelligence");

        test(skriptLoader, "the player doesn't have ai");
        test(skriptLoader, "the player doesn't have artificial intelligence");
        test(skriptLoader, "the player does not have ai");
        test(skriptLoader, "the player does not have artificial intelligence");
        test(skriptLoader, "the player do not have ai");
        test(skriptLoader, "the player do not have artificial intelligence");
        test(skriptLoader, "the player don't have ai");
        test(skriptLoader, "the player don't have artificial intelligence");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiHasAICondition);
    }
}

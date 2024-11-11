package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiHasChatFilteringConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "the player has chat filtering on");
        test(skriptLoader, "the player has chat filtering enabled");
        test(skriptLoader, "the player has text filtering on");
        test(skriptLoader, "the player has text filtering enabled");
        test(skriptLoader, "the player have chat filtering on");
        test(skriptLoader, "the player have chat filtering enabled");
        test(skriptLoader, "the player have text filtering on");
        test(skriptLoader, "the player have text filtering enabled");

        test(skriptLoader, "the player doesn't have chat filtering on");
        test(skriptLoader, "the player doesn't have chat filtering enabled");
        test(skriptLoader, "the player doesn't have text filtering on");
        test(skriptLoader, "the player doesn't have text filtering enabled");
        test(skriptLoader, "the player does not have chat filtering on");
        test(skriptLoader, "the player does not have chat filtering enabled");
        test(skriptLoader, "the player does not have text filtering on");
        test(skriptLoader, "the player does not have text filtering enabled");
        test(skriptLoader, "the player do not have chat filtering on");
        test(skriptLoader, "the player do not have chat filtering enabled");
        test(skriptLoader, "the player do not have text filtering on");
        test(skriptLoader, "the player do not have text filtering enabled");
        test(skriptLoader, "the player don't have chat filtering on");
        test(skriptLoader, "the player don't have chat filtering enabled");
        test(skriptLoader, "the player don't have text filtering on");
        test(skriptLoader, "the player don't have text filtering enabled");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiHasChatFilteringCondition);
    }
}

package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiIsBabyConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "the player is a child");
        test(skriptLoader, "the player is a baby");
        test(skriptLoader, "the player are a child");
        test(skriptLoader, "the player are a baby");

        test(skriptLoader, "the player isn't a child");
        test(skriptLoader, "the player isn't a baby");
        test(skriptLoader, "the player is not a child");
        test(skriptLoader, "the player is not a baby");
        test(skriptLoader, "the player aren't a child");
        test(skriptLoader, "the player aren't a baby");
        test(skriptLoader, "the player are not a child");
        test(skriptLoader, "the player are not a baby");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiIsBabyCondition);
    }
}

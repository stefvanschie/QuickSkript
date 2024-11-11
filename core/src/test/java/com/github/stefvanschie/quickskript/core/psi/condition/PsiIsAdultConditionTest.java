package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiIsAdultConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "the player is an adult");
        test(skriptLoader, "the player is adult");
        test(skriptLoader, "the player are an adult");
        test(skriptLoader, "the player are adult");

        test(skriptLoader, "the player isn't an adult");
        test(skriptLoader, "the player isn't adult");
        test(skriptLoader, "the player is not an adult");
        test(skriptLoader, "the player is not adult");
        test(skriptLoader, "the player aren't an adult");
        test(skriptLoader, "the player aren't adult");
        test(skriptLoader, "the player are not an adult");
        test(skriptLoader, "the player are not adult");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiIsAdultCondition);
    }
}

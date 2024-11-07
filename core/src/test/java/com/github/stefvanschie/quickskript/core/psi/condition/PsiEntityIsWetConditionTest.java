package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiEntityIsWetConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "the player is wet");
        test(skriptLoader, "the player are wet");

        test(skriptLoader, "the player isn't wet");
        test(skriptLoader, "the player is not wet");
        test(skriptLoader, "the player aren't wet");
        test(skriptLoader, "the player are not wet");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiEntityIsWetCondition);
    }
}

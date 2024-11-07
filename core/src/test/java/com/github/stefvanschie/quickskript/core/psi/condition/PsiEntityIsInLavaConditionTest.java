package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiEntityIsInLavaConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "the player is in lava");
        test(skriptLoader, "the player are in lava");

        test(skriptLoader, "the player isn't in lava");
        test(skriptLoader, "the player is not in lava");
        test(skriptLoader, "the player aren't in lava");
        test(skriptLoader, "the player are not in lava");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiEntityIsInLavaCondition);
    }
}

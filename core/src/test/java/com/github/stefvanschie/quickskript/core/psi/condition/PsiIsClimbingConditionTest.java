package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiIsClimbingConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "the player is climbing");
        test(skriptLoader, "the player are climbing");

        test(skriptLoader, "the player isn't climbing");
        test(skriptLoader, "the player is not climbing");
        test(skriptLoader, "the player aren't climbing");
        test(skriptLoader, "the player are not climbing");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiIsClimbingCondition);
    }
}

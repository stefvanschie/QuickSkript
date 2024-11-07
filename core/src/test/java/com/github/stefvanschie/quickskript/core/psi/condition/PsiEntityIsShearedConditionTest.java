package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiEntityIsShearedConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "the player is sheared");
        test(skriptLoader, "the player is shorn");
        test(skriptLoader, "the player are sheared");
        test(skriptLoader, "the player are shorn");

        test(skriptLoader, "the player isn't sheared");
        test(skriptLoader, "the player isn't shorn");
        test(skriptLoader, "the player is not sheared");
        test(skriptLoader, "the player is not shorn");
        test(skriptLoader, "the player aren't sheared");
        test(skriptLoader, "the player aren't shorn");
        test(skriptLoader, "the player are not sheared");
        test(skriptLoader, "the player are not shorn");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiEntityIsShearedCondition);
    }
}

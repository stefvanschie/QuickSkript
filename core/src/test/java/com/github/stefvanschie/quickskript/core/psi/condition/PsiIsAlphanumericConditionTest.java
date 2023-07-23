package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PsiIsAlphanumericConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "\"abc123\" is alphanumeric", true);
        test(skriptLoader, "\"!@#\" is alphanumeric", false);
        test(skriptLoader, "\"abc123\" isn't alphanumeric", false);
        test(skriptLoader, "\"!@#\" isn't alphanumeric", true);
    }

    private void test(SkriptLoader loader, String input, boolean result) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiIsAlphanumericCondition);
        assertEquals(result, psiElement.execute(null, null));
    }
}

package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiContainsObjectConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "6, 8 contains 6, and 6", true);
        test(skriptLoader, "3, 2 contain 1, or 4", false);

        test(skriptLoader, "5 doesn't contain 5, 3, and 7", true);
        test(skriptLoader, "4, 6, 7 does not contain 7, 7, or 10", false);
        test(skriptLoader, "8, 10, 1 do not contain 1, and 7", true);
        test(skriptLoader, "7, 7, 9 don't contain 6, or 7", false);
    }

    private void test(SkriptLoader loader, String input, boolean result) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiContainsObjectCondition);
        assertEquals(result, psiElement.execute(null, null));
    }
}

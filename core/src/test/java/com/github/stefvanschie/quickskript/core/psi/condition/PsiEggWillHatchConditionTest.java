package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiEggWillHatchConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "the egg will hatch");
        test(skriptLoader, "egg will hatch");

        test(skriptLoader, "the egg will not hatch");
        test(skriptLoader, "the egg won't hatch");
        test(skriptLoader, "egg will not hatch");
        test(skriptLoader, "egg won't hatch");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiEggWillHatchCondition);
    }
}

package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiIsTextEmptyConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "\"\" is empty");
        test(skriptLoader, "\"\" are empty");

        test(skriptLoader, "\"a\" isn't empty");
        test(skriptLoader, "\"b\" is not empty");
        test(skriptLoader, "\"c\" aren't empty");
        test(skriptLoader, "\"d\" are not empty");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiIsTextEmptyCondition);
        assertTrue(psiElement.isPreComputed());
        assertTrue(psiElement.execute(null, null, Boolean.class));
    }
}

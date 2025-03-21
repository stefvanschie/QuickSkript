package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PsiIsItemEmptyConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "0 of enchanted book is empty");
        test(skriptLoader, "0 of painting are empty");

        test(skriptLoader, "4 of redstone lamp isn't empty");
        test(skriptLoader, "1 of tadpole bucket is not empty");
        test(skriptLoader, "1 of diamond boots aren't empty");
        test(skriptLoader, "5 of tropical fish spawn egg are not empty");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiIsItemEmptyCondition);
        assertTrue(psiElement.isPreComputed());
        assertEquals(true, ((PsiIsItemEmptyCondition) psiElement).execute(null, null));
    }
}

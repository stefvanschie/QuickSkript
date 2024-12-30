package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiIsInventoryEmptyConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "clicked inventory is empty");
        test(skriptLoader, "clicked inventory are empty");

        test(skriptLoader, "clicked inventory isn't empty");
        test(skriptLoader, "clicked inventory is not empty");
        test(skriptLoader, "clicked inventory aren't empty");
        test(skriptLoader, "clicked inventory are not empty");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiIsInventoryEmptyCondition);
    }
}

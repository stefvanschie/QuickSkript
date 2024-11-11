package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiHasPassedConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "date(2024, 11, 11) is in the past");
        test(skriptLoader, "date(2024, 11, 11) are in the past");

        test(skriptLoader, "date(2024, 11, 11) isn't in the future");
        test(skriptLoader, "date(2024, 11, 11) is not in the future");
        test(skriptLoader, "date(2024, 11, 11) aren't in the future");
        test(skriptLoader, "date(2024, 11, 11) are not in the future");

        test(skriptLoader, "date(2024, 11, 11) has passed");
        test(skriptLoader, "date(2024, 11, 11) have passed");

        test(skriptLoader, "date(2024, 11, 11) is in the future");
        test(skriptLoader, "date(2024, 11, 11) are in the future");

        test(skriptLoader, "date(2024, 11, 11) isn't in the past");
        test(skriptLoader, "date(2024, 11, 11) is not in the past");
        test(skriptLoader, "date(2024, 11, 11) aren't in the past");
        test(skriptLoader, "date(2024, 11, 11) are not in the past");

        test(skriptLoader, "date(2024, 11, 11) hasn't passed");
        test(skriptLoader, "date(2024, 11, 11) has not passed");
        test(skriptLoader, "date(2024, 11, 11) haven't passed");
        test(skriptLoader, "date(2024, 11, 11) have not passed");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiHasPassed);
        assertTrue(psiElement.isPreComputed());
    }
}

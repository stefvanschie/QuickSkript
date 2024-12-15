package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiIsChargedConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "affected entities is charged");
        test(skriptLoader, "affected entities is powered");
        test(skriptLoader, "affected entities are powered");
        test(skriptLoader, "affected entities are charged");

        test(skriptLoader, "affected entities isn't charged");
        test(skriptLoader, "affected entities isn't powered");
        test(skriptLoader, "affected entities is not charged");
        test(skriptLoader, "affected entities is not powered");
        test(skriptLoader, "affected entities aren't charged");
        test(skriptLoader, "affected entities aren't powered");
        test(skriptLoader, "affected entities are not charged");
        test(skriptLoader, "affected entities are not powered");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiIsChargedCondition);
    }
}

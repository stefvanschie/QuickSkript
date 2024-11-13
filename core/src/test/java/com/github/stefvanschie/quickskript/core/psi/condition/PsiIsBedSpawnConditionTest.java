package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiIsBedSpawnConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "the respawn location was a bed");
        test(skriptLoader, "the respawn location was bed");
        test(skriptLoader, "the respawn location is a bed");
        test(skriptLoader, "the respawn location is bed");
        test(skriptLoader, "respawn location was a bed");
        test(skriptLoader, "respawn location was bed");
        test(skriptLoader, "respawn location is a bed");
        test(skriptLoader, "respawn location is bed");

        test(skriptLoader, "the respawn location wasn't a bed");
        test(skriptLoader, "the respawn location wasn't bed");
        test(skriptLoader, "the respawn location was not a bed");
        test(skriptLoader, "the respawn location was not bed");
        test(skriptLoader, "the respawn location isn't a bed");
        test(skriptLoader, "the respawn location isn't bed");
        test(skriptLoader, "the respawn location is not a bed");
        test(skriptLoader, "the respawn location is not bed");
        test(skriptLoader, "respawn location wasn't a bed");
        test(skriptLoader, "respawn location wasn't bed");
        test(skriptLoader, "respawn location was not a bed");
        test(skriptLoader, "respawn location was not bed");
        test(skriptLoader, "respawn location isn't a bed");
        test(skriptLoader, "respawn location isn't bed");
        test(skriptLoader, "respawn location is not a bed");
        test(skriptLoader, "respawn location is not bed");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiIsBedSpawnCondition);
    }
}

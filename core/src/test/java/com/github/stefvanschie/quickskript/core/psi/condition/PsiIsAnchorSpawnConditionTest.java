package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiIsAnchorSpawnConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "the respawn location was a respawn anchor");
        test(skriptLoader, "the respawn location was respawn anchor");
        test(skriptLoader, "the respawn location is a respawn anchor");
        test(skriptLoader, "the respawn location is respawn anchor");
        test(skriptLoader, "respawn location was a respawn anchor");
        test(skriptLoader, "respawn location was respawn anchor");
        test(skriptLoader, "respawn location is a respawn anchor");
        test(skriptLoader, "respawn location is respawn anchor");

        test(skriptLoader, "the respawn location wasn't a respawn anchor");
        test(skriptLoader, "the respawn location wasn't respawn anchor");
        test(skriptLoader, "the respawn location was not a respawn anchor");
        test(skriptLoader, "the respawn location was not respawn anchor");
        test(skriptLoader, "the respawn location isn't a respawn anchor");
        test(skriptLoader, "the respawn location isn't respawn anchor");
        test(skriptLoader, "the respawn location is not a respawn anchor");
        test(skriptLoader, "the respawn location is not respawn anchor");
        test(skriptLoader, "respawn location wasn't a respawn anchor");
        test(skriptLoader, "respawn location wasn't respawn anchor");
        test(skriptLoader, "respawn location was not a respawn anchor");
        test(skriptLoader, "respawn location was not respawn anchor");
        test(skriptLoader, "respawn location isn't a respawn anchor");
        test(skriptLoader, "respawn location isn't respawn anchor");
        test(skriptLoader, "respawn location is not a respawn anchor");
        test(skriptLoader, "respawn location is not respawn anchor");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiIsAnchorSpawnCondition);
    }
}

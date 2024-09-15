package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiDoRespawnAnchorsWorkConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "respawn anchors do work in \"test\"");
        test(skriptLoader, "respawn anchors work in \"test\"");

        test(skriptLoader, "respawn anchors don't work in \"test\"");
        test(skriptLoader, "respawn anchors do not work in \"test\"");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiDoRespawnAnchorsWorkCondition);
    }
}

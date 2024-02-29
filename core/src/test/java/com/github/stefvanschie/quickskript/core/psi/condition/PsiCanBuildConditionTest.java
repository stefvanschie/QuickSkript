package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiCanBuildConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "player can build at the location at (31, 87, -73) in \"test\"");
        test(skriptLoader, "player is allowed to build at the location at (-24, -43, -48) in \"test\"");
        test(skriptLoader, "player are allowed to build at the location at (-89, 40, 96) in \"test\"");

        test(skriptLoader, "player can't build at the location at (-29, 0, -23) in \"test\"");
        test(skriptLoader, "player isn't allowed to build at the location at (-62, 57, 61) in \"test\"");
        test(skriptLoader, "player aren't allowed to build at the location at (-75, 65, -77) in \"test\"");
        test(skriptLoader, "player cannot build at the location at (20, -62, -86) in \"test\"");
        test(skriptLoader, "player is not allowed to build at the location at (76, -76, 61) in \"test\"");
        test(skriptLoader, "player are not allowed to build at the location at (-100, 28, -45) in \"test\"");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiCanBuildCondition);
    }
}

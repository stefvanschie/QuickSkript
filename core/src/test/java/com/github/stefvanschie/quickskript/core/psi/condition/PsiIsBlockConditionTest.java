package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiIsBlockConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "light gray concrete is a block");
        test(skriptLoader, "purple candle cake is block");
        test(skriptLoader, "stone shovels is blocks");
        test(skriptLoader, "spawn ravager are a block");
        test(skriptLoader, "pumpkin seeds are block");
        test(skriptLoader, "carrot are blocks");

        test(skriptLoader, "dry east facing dead bubble coral wall fan isn't a block");
        test(skriptLoader, "horse spawn egg isn't block");
        test(skriptLoader, "gold carrot isn't blocks");
        test(skriptLoader, "unpowered bamboo fence gates is not a block");
        test(skriptLoader, "long invisibility splash potion is not block");
        test(skriptLoader, "nether quartz wayfinder netherite leggings is not blocks");
        test(skriptLoader, "dry nether brick wall aren't a block");
        test(skriptLoader, "spyglasses aren't block");
        test(skriptLoader, "arrow aren't blocks");
        test(skriptLoader, "rotten flesh are not a block");
        test(skriptLoader, "pufferfish are not block");
        test(skriptLoader, "magenta wool are not blocks");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiIsBlockCondition);
    }
}

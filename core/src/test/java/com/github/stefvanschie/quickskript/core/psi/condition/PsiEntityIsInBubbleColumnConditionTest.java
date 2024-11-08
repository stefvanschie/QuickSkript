package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiEntityIsInBubbleColumnConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "the player is in a bubble column");
        test(skriptLoader, "the player is in a bubblecolumn");
        test(skriptLoader, "the player is in bubble column");
        test(skriptLoader, "the player is in bubblecolumn");
        test(skriptLoader, "the player are in a bubble column");
        test(skriptLoader, "the player are in a bubblecolumn");
        test(skriptLoader, "the player are in bubble column");
        test(skriptLoader, "the player are in bubblecolumn");

        test(skriptLoader, "the player isn't in a bubble column");
        test(skriptLoader, "the player isn't in a bubblecolumn");
        test(skriptLoader, "the player isn't in bubble column");
        test(skriptLoader, "the player isn't in bubblecolumn");
        test(skriptLoader, "the player is not in a bubble column");
        test(skriptLoader, "the player is not in a bubblecolumn");
        test(skriptLoader, "the player is not in bubble column");
        test(skriptLoader, "the player is not in bubblecolumn");
        test(skriptLoader, "the player aren't in a bubble column");
        test(skriptLoader, "the player aren't in a bubblecolumn");
        test(skriptLoader, "the player aren't in bubble column");
        test(skriptLoader, "the player aren't in bubblecolumn");
        test(skriptLoader, "the player are not in a bubble column");
        test(skriptLoader, "the player are not in a bubblecolumn");
        test(skriptLoader, "the player are not in bubble column");
        test(skriptLoader, "the player are not in bubblecolumn");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiEntityIsInBubbleColumnCondition);
    }
}

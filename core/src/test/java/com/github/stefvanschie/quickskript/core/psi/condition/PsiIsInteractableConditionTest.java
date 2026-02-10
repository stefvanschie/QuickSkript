package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsInteractableConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "music disc 13 is interactable",
        "netherite scrap are interactable",
        "mangrove shelf isn't interactable",
        "heart of the sea is not interactable",
        "chorus plant aren't interactable",
        "purple harness are not interactable"
    })
    void test(String input) {
        assertInstanceOf(PsiIsInteractableCondition.class, loader.tryParseElement(input, -1));
    }
}

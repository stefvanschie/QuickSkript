package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PsiHasPassedConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "date(2024, 11, 11) is in the past",
        "date(2024, 11, 11) are in the past",
        "date(2024, 11, 11) isn't in the future",
        "date(2024, 11, 11) is not in the future",
        "date(2024, 11, 11) aren't in the future",
        "date(2024, 11, 11) are not in the future",
        "date(2024, 11, 11) has passed",
        "date(2024, 11, 11) have passed",
        "date(2024, 11, 11) is in the future",
        "date(2024, 11, 11) are in the future",
        "date(2024, 11, 11) isn't in the past",
        "date(2024, 11, 11) is not in the past",
        "date(2024, 11, 11) aren't in the past",
        "date(2024, 11, 11) are not in the past",
        "date(2024, 11, 11) hasn't passed",
        "date(2024, 11, 11) has not passed",
        "date(2024, 11, 11) haven't passed",
        "date(2024, 11, 11) have not passed"
    })
    void test(String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertNotNull(psiElement);
        assertInstanceOf(PsiHasPassed.class, psiElement);
        assertTrue(psiElement.isPreComputed());
    }
}

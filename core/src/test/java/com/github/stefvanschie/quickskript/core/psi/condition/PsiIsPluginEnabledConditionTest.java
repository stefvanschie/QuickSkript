package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsPluginEnabledConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "plugins \"QuickSkript\" is enabled",
        "plugins \"QuickSkript\" are enabled",
        "plugin \"QuickSkript\" is enabled",
        "plugin \"QuickSkript\" are enabled",
        "plugins \"QuickSkript\" isn't enabled",
        "plugins \"QuickSkript\" is not enabled",
        "plugins \"QuickSkript\" aren't enabled",
        "plugins \"QuickSkript\" are not enabled",
        "plugin \"QuickSkript\" isn't enabled",
        "plugin \"QuickSkript\" is not enabled",
        "plugin \"QuickSkript\" aren't enabled",
        "plugin \"QuickSkript\" are not enabled",
        "plugins \"QuickSkript\" is disabled",
        "plugins \"QuickSkript\" are disabled",
        "plugin \"QuickSkript\" is disabled",
        "plugin \"QuickSkript\" are disabled"
    })
    void test(String input) {
        assertInstanceOf(PsiIsPluginEnabledCondition.class, loader.tryParseElement(input, -1));
    }
}

package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiDamageCauseConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void test() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "the damage was been caused by dragon's breath",
        "the damage was been done by drowning",
        "the damage was been made by dryout",
        "the damage was caused by magma",
        "the damage was done by falling block",
        "the damage was made by poison",
        "the damage is been caused by suffocation",
        "the damage is been done by melting",
        "the damage is been made by starvation",
        "the damage is caused by poison",
        "the damage is done by poison",
        "the damage is made by starvation",
        "the damage has been caused by wither",
        "the damage has been done by entity explosion",
        "the damage has been made by attack",
        "the damage has caused by attack",
        "the damage has done by cramming",
        "the damage has made by block explosion",
        "damage was been caused by lava",
        "damage was been done by block explosion",
        "damage was been made by sweep attack",
        "damage was caused by cramming",
        "damage was done by block explosion",
        "damage was made by entity explosion",
        "damage is been caused by magma",
        "damage is been done by dragon's breath",
        "damage is been made by dryout",
        "damage is caused by suffocation",
        "damage is done by poison",
        "damage is made by potion",
        "damage has been caused by wither",
        "damage has been done by lava",
        "damage has been made by suicide",
        "damage has caused by thorns",
        "damage has done by contact",
        "damage has made by attack",
        "the damage wasn't been caused by dragon's breath",
        "the damage wasn't been done by thorns",
        "the damage wasn't been made by falling block",
        "the damage wasn't caused by fall",
        "the damage wasn't done by unknown",
        "the damage wasn't made by entity explosion",
        "the damage wasnot been caused by block explosion",
        "the damage wasnot been done by potion",
        "the damage wasnot been made by void",
        "the damage wasnot caused by wither",
        "the damage wasnot done by dragon's breath",
        "the damage wasnot made by fall",
        "the damage isn't been caused by attack",
        "the damage isn't been done by void",
        "the damage isn't been made by unknown",
        "the damage isn't caused by poison",
        "the damage isn't done by thorns",
        "the damage isn't made by lightning",
        "the damage isnot been caused by burning",
        "the damage isnot been done by suffocation",
        "the damage isnot been made by drowning",
        "the damage isnot caused by thorns",
        "the damage isnot done by melting",
        "the damage isnot made by contact",
        "the damage hasn't been caused by fall",
        "the damage hasn't been done by sweep attack",
        "the damage hasn't been made by lava",
        "the damage hasn't caused by burning",
        "the damage hasn't done by attack",
        "the damage hasn't made by thorns",
        "the damage hasnot been caused by drowning",
        "the damage hasnot been done by magma",
        "the damage hasnot been made by fall",
        "the damage hasnot caused by suicide",
        "the damage hasnot done by cramming",
        "the damage hasnot made by wither",
        "damage wasn't been caused by suicide",
        "damage wasn't been done by sweep attack",
        "damage wasn't been made by hitting wall while flying",
        "damage wasn't caused by dragon's breath",
        "damage wasn't done by void",
        "damage wasn't made by drowning",
        "damage wasnot been caused by falling block",
        "damage wasnot been done by cramming",
        "damage wasnot been made by fire",
        "damage wasnot caused by fire",
        "damage wasnot done by drowning",
        "damage wasnot made by fire",
        "damage isn't been caused by fire",
        "damage isn't been done by block explosion",
        "damage isn't been made by block explosion",
        "damage isn't caused by suicide",
        "damage isn't done by cramming",
        "damage isn't made by attack",
        "damage isnot been caused by falling block",
        "damage isnot been done by projectile",
        "damage isnot been made by melting",
        "damage isnot caused by fall",
        "damage isnot done by lightning",
        "damage isnot made by poison",
        "damage hasn't been caused by potion",
        "damage hasn't been done by lightning",
        "damage hasn't been made by dryout",
        "damage hasn't caused by sweep attack",
        "damage hasn't done by sweep attack",
        "damage hasn't made by fall",
        "damage hasnot been caused by wither",
        "damage hasnot been done by falling block",
        "damage hasnot been made by starvation",
        "damage hasnot caused by lightning",
        "damage hasnot done by dryout",
        "damage hasnot made by burning"
    })
    void test(String input) {
        assertInstanceOf(PsiDamageCauseCondition.class, loader.tryParseElement(input, -1));
    }
}

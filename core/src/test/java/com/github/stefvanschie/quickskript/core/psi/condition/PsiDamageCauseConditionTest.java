package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiDamageCauseConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "the damage was been caused by dragon's breath");
        test(skriptLoader, "the damage was been done by drowning");
        test(skriptLoader, "the damage was been made by dryout");
        test(skriptLoader, "the damage was caused by magma");
        test(skriptLoader, "the damage was done by falling block");
        test(skriptLoader, "the damage was made by poison");
        test(skriptLoader, "the damage is been caused by suffocation");
        test(skriptLoader, "the damage is been done by melting");
        test(skriptLoader, "the damage is been made by starvation");
        test(skriptLoader, "the damage is caused by poison");
        test(skriptLoader, "the damage is done by poison");
        test(skriptLoader, "the damage is made by starvation");
        test(skriptLoader, "the damage has been caused by wither");
        test(skriptLoader, "the damage has been done by entity explosion");
        test(skriptLoader, "the damage has been made by attack");
        test(skriptLoader, "the damage has caused by attack");
        test(skriptLoader, "the damage has done by cramming");
        test(skriptLoader, "the damage has made by block explosion");
        test(skriptLoader, "damage was been caused by lava");
        test(skriptLoader, "damage was been done by block explosion");
        test(skriptLoader, "damage was been made by sweep attack");
        test(skriptLoader, "damage was caused by cramming");
        test(skriptLoader, "damage was done by block explosion");
        test(skriptLoader, "damage was made by entity explosion");
        test(skriptLoader, "damage is been caused by magma");
        test(skriptLoader, "damage is been done by dragon's breath");
        test(skriptLoader, "damage is been made by dryout");
        test(skriptLoader, "damage is caused by suffocation");
        test(skriptLoader, "damage is done by poison");
        test(skriptLoader, "damage is made by potion");
        test(skriptLoader, "damage has been caused by wither");
        test(skriptLoader, "damage has been done by lava");
        test(skriptLoader, "damage has been made by suicide");
        test(skriptLoader, "damage has caused by thorns");
        test(skriptLoader, "damage has done by contact");
        test(skriptLoader, "damage has made by attack");

        test(skriptLoader, "the damage wasn't been caused by dragon's breath");
        test(skriptLoader, "the damage wasn't been done by thorns");
        test(skriptLoader, "the damage wasn't been made by falling block");
        test(skriptLoader, "the damage wasn't caused by fall");
        test(skriptLoader, "the damage wasn't done by unknown");
        test(skriptLoader, "the damage wasn't made by entity explosion");
        test(skriptLoader, "the damage wasnot been caused by block explosion");
        test(skriptLoader, "the damage wasnot been done by potion");
        test(skriptLoader, "the damage wasnot been made by void");
        test(skriptLoader, "the damage wasnot caused by wither");
        test(skriptLoader, "the damage wasnot done by dragon's breath");
        test(skriptLoader, "the damage wasnot made by fall");
        test(skriptLoader, "the damage isn't been caused by attack");
        test(skriptLoader, "the damage isn't been done by void");
        test(skriptLoader, "the damage isn't been made by unknown");
        test(skriptLoader, "the damage isn't caused by poison");
        test(skriptLoader, "the damage isn't done by thorns");
        test(skriptLoader, "the damage isn't made by lightning");
        test(skriptLoader, "the damage isnot been caused by burning");
        test(skriptLoader, "the damage isnot been done by suffocation");
        test(skriptLoader, "the damage isnot been made by drowning");
        test(skriptLoader, "the damage isnot caused by thorns");
        test(skriptLoader, "the damage isnot done by melting");
        test(skriptLoader, "the damage isnot made by contact");
        test(skriptLoader, "the damage hasn't been caused by fall");
        test(skriptLoader, "the damage hasn't been done by sweep attack");
        test(skriptLoader, "the damage hasn't been made by lava");
        test(skriptLoader, "the damage hasn't caused by burning");
        test(skriptLoader, "the damage hasn't done by attack");
        test(skriptLoader, "the damage hasn't made by thorns");
        test(skriptLoader, "the damage hasnot been caused by drowning");
        test(skriptLoader, "the damage hasnot been done by magma");
        test(skriptLoader, "the damage hasnot been made by fall");
        test(skriptLoader, "the damage hasnot caused by suicide");
        test(skriptLoader, "the damage hasnot done by cramming");
        test(skriptLoader, "the damage hasnot made by wither");
        test(skriptLoader, "damage wasn't been caused by suicide");
        test(skriptLoader, "damage wasn't been done by sweep attack");
        test(skriptLoader, "damage wasn't been made by hitting wall while flying");
        test(skriptLoader, "damage wasn't caused by dragon's breath");
        test(skriptLoader, "damage wasn't done by void");
        test(skriptLoader, "damage wasn't made by drowning");
        test(skriptLoader, "damage wasnot been caused by falling block");
        test(skriptLoader, "damage wasnot been done by cramming");
        test(skriptLoader, "damage wasnot been made by fire");
        test(skriptLoader, "damage wasnot caused by fire");
        test(skriptLoader, "damage wasnot done by drowning");
        test(skriptLoader, "damage wasnot made by fire");
        test(skriptLoader, "damage isn't been caused by fire");
        test(skriptLoader, "damage isn't been done by block explosion");
        test(skriptLoader, "damage isn't been made by block explosion");
        test(skriptLoader, "damage isn't caused by suicide");
        test(skriptLoader, "damage isn't done by cramming");
        test(skriptLoader, "damage isn't made by attack");
        test(skriptLoader, "damage isnot been caused by falling block");
        test(skriptLoader, "damage isnot been done by projectile");
        test(skriptLoader, "damage isnot been made by melting");
        test(skriptLoader, "damage isnot caused by fall");
        test(skriptLoader, "damage isnot done by lightning");
        test(skriptLoader, "damage isnot made by poison");
        test(skriptLoader, "damage hasn't been caused by potion");
        test(skriptLoader, "damage hasn't been done by lightning");
        test(skriptLoader, "damage hasn't been made by dryout");
        test(skriptLoader, "damage hasn't caused by sweep attack");
        test(skriptLoader, "damage hasn't done by sweep attack");
        test(skriptLoader, "damage hasn't made by fall");
        test(skriptLoader, "damage hasnot been caused by wither");
        test(skriptLoader, "damage hasnot been done by falling block");
        test(skriptLoader, "damage hasnot been made by starvation");
        test(skriptLoader, "damage hasnot caused by lightning");
        test(skriptLoader, "damage hasnot done by dryout");
        test(skriptLoader, "damage hasnot made by burning");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiDamageCauseCondition);
    }
}

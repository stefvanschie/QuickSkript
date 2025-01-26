package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiCanHoldConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "initiator can hold wooden axe");
        test(skriptLoader, "initiator has space for poisonous potato");
        test(skriptLoader, "initiator has space to hold mangrove wood");
        test(skriptLoader, "initiator has enough space for jungle chest boats");
        test(skriptLoader, "initiator has enough space to hold diamond ores");
        test(skriptLoader, "initiator have space for chicken");
        test(skriptLoader, "initiator have space to hold magenta candle cake");
        test(skriptLoader, "initiator have enough space for porkchop");
        test(skriptLoader, "initiator have enough space to hold acacia stairs");

        test(skriptLoader, "initiator cannot hold lever");
        test(skriptLoader, "initiator can't hold magenta candle");
        test(skriptLoader, "initiator has not space for map");
        test(skriptLoader, "initiator has not space to hold andesite wall");
        test(skriptLoader, "initiator has not enough space for seagrasss");
        test(skriptLoader, "initiator has not enough space to hold dragon breath");
        test(skriptLoader, "initiator have not space for cauldrons");
        test(skriptLoader, "initiator have not space to hold end stone bricks");
        test(skriptLoader, "initiator have not enough space for golden carrot");
        test(skriptLoader, "initiator have not enough space to hold furnace minecarts");
        test(skriptLoader, "initiator hasn't space for exposed cut copper stairs");
        test(skriptLoader, "initiator hasn't space to hold phantom membrane");
        test(skriptLoader, "initiator hasn't enough space for warped doors");
        test(skriptLoader, "initiator hasn't enough space to hold acacia buttons");
        test(skriptLoader, "initiator haven't space for deepslate lapis ores");
        test(skriptLoader, "initiator haven't space to hold ghast tear");
        test(skriptLoader, "initiator haven't enough space for spruce boat");
        test(skriptLoader, "initiator haven't enough space to hold pitcher pods");
        test(skriptLoader, "initiator don't have space for light gray candle cake");
        test(skriptLoader, "initiator don't have space to hold raw gold block");
        test(skriptLoader, "initiator don't have enough space for light blue candle cakes");
        test(skriptLoader, "initiator don't have enough space to hold apple");
        test(skriptLoader, "initiator doesn't have space for bamboo fence gates");
        test(skriptLoader, "initiator doesn't have space to hold light blue concrete powder");
        test(skriptLoader, "initiator doesn't have enough space for fermented spider eyes");
        test(skriptLoader, "initiator doesn't have enough space to hold player heads");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiCanHoldCondition);
    }
}

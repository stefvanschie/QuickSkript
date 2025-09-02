package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiCanHoldConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "initiator can hold wooden axe",
        "initiator has space for poisonous potato",
        "initiator has space to hold mangrove wood",
        "initiator has enough space for jungle chest boats",
        "initiator has enough space to hold diamond ores",
        "initiator have space for chicken",
        "initiator have space to hold magenta candle cake",
        "initiator have enough space for porkchop",
        "initiator have enough space to hold acacia stairs",
        "initiator cannot hold lever",
        "initiator can't hold magenta candle",
        "initiator has not space for map",
        "initiator has not space to hold andesite wall",
        "initiator has not enough space for seagrasss",
        "initiator has not enough space to hold dragon breath",
        "initiator have not space for cauldrons",
        "initiator have not space to hold end stone bricks",
        "initiator have not enough space for golden carrot",
        "initiator have not enough space to hold furnace minecarts",
        "initiator hasn't space for exposed cut copper stairs",
        "initiator hasn't space to hold phantom membrane",
        "initiator hasn't enough space for warped doors",
        "initiator hasn't enough space to hold acacia buttons",
        "initiator haven't space for deepslate lapis ores",
        "initiator haven't space to hold ghast tear",
        "initiator haven't enough space for spruce boat",
        "initiator haven't enough space to hold pitcher pods",
        "initiator don't have space for light gray candle cake",
        "initiator don't have space to hold raw gold block",
        "initiator don't have enough space for light blue candle cakes",
        "initiator don't have enough space to hold apple",
        "initiator doesn't have space for bamboo fence gates",
        "initiator doesn't have space to hold light blue concrete powder",
        "initiator doesn't have enough space for fermented spider eyes",
        "initiator doesn't have enough space to hold player heads"
    })
    void test(String input) {
        assertInstanceOf(PsiCanHoldCondition.class, loader.tryParseElement(input, -1));
    }
}

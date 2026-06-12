package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsPreferredToolConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "sandstone wall is bamboo fence gate's preferred tools",
        "cooked mutton is nether brick stairs's preferred tool",
        "mangrove fence are water's preferred tools",
        "gold nugget are mangrove fence's preferred tool",
        "cat spawn egg is the preferred tools for acacia shelf",
        "cyan dye is the preferred tools of cyan stained glass pane",
        "verdant froglight is the preferred tool for waxed copper lantern",
        "diamond sword is the preferred tool of rail",
        "redstone block is a preferred tools for block",
        "recovery compass is a preferred tools of block",
        "oxidized copper bars is a preferred tool for light gray stained glass pane",
        "gray bundle is a preferred tool of sea pickle",
        "exposed copper door is preferred tools for block",
        "diorite stairs is preferred tools of air",
        "villager spawn egg is preferred tool for block",
        "smooth quartz stairs is preferred tool of fire coral",
        "warped button are the preferred tools for block",
        "tropical fish spawn egg are the preferred tools of waxed exposed copper bars",
        "skull banner pattern are the preferred tool for block",
        "enderman spawn egg are the preferred tool of lightning rod",
        "copper wall torch are a preferred tools for yellow candle",
        "iron nugget are a preferred tools of warped fence",
        "cobbled deepslate stairs are a preferred tool for block",
        "tuff brick stairs are a preferred tool of end stone brick stairs",
        "tipped arrow are preferred tools for block",
        "chiseled bookshelf are preferred tools of warped stem",
        "waxed weathered copper lantern are preferred tool for block",
        "dead brain coral are preferred tool of block",
        "mooshroom spawn egg isn't block's preferred tools",
        "drowned spawn egg isn't cherry hanging sign's preferred tool",
        "experience bottle is not block's preferred tools",
        "iron trapdoor is not powered rail's preferred tool",
        "exposed copper bars aren't block's preferred tools",
        "ward armor trim smithing template aren't dark oak stairs's preferred tool",
        "heart of the sea are not block's preferred tools",
        "yellow bed are not acacia leaves's preferred tool",
        "bamboo wall sign isn't the preferred tools for light gray bed",
        "parched spawn egg isn't the preferred tools of iron trapdoor",
        "lime dye isn't the preferred tool for yellow concrete powder",
        "emerald isn't the preferred tool of test block",
        "gray bundle isn't a preferred tools for block",
        "iron axe isn't a preferred tools of block",
        "rabbit foot isn't a preferred tool for warped stairs",
        "tuff stairs isn't a preferred tool of block",
        "iron trapdoor isn't preferred tools for block",
        "warped fence gate isn't preferred tools of block",
        "exposed lightning rod isn't preferred tool for block",
        "soul lantern isn't preferred tool of block",
        "orange candle is not the preferred tools for cactus",
        "calibrated sculk sensor is not the preferred tools of exposed copper bulb",
        "bee nest is not the preferred tool for block",
        "bamboo chest raft is not the preferred tool of block",
        "blue dye is not a preferred tools for yellow stained glass pane",
        "bamboo shelf is not a preferred tools of block",
        "mud brick wall is not a preferred tool for stripped mangrove log",
        "tropical fish spawn egg is not a preferred tool of block",
        "stripped oak wood is not preferred tools for block",
        "cooked rabbit is not preferred tools of block",
        "axolotl spawn egg is not preferred tool for block",
        "mossy stone brick wall is not preferred tool of block",
        "light blue harness aren't the preferred tools for melon stem",
        "warped door aren't the preferred tools of block",
        "dragon wall head aren't the preferred tool for block",
        "mossy cobblestone stairs aren't the preferred tool of brown concrete powder",
        "oxidized copper chain aren't a preferred tools for block",
        "diorite slab aren't a preferred tools of warped wall sign",
        "warped trapdoor aren't a preferred tool for block",
        "blue shulker box aren't a preferred tool of block",
        "ladder aren't preferred tools for green stained glass",
        "waxed copper trapdoor aren't preferred tools of block",
        "iron axe aren't preferred tool for waxed oxidized copper door",
        "birch sapling aren't preferred tool of jungle shelf",
        "golden boots are not the preferred tools for dark oak wall hanging sign",
        "dead tube coral wall fan are not the preferred tools of moving piston",
        "apple are not the preferred tool for exposed cut copper stairs",
        "prismarine crystals are not the preferred tool of block",
        "mangrove boat are not a preferred tools for orange stained glass pane",
        "wolf spawn egg are not a preferred tools of stripped warped stem",
        "cow spawn egg are not a preferred tool for stone stairs",
        "blue bundle are not a preferred tool of birch sign",
        "music disc relic are not preferred tools for oxidized copper trapdoor",
        "copper nautilus armor are not preferred tools of warped stairs",
        "glow berries are not preferred tool for bamboo fence gate",
        "copper boots are not preferred tool of iron chain"
    })
    void test(String input) {
        assertInstanceOf(PsiIsPreferredToolCondition.class, loader.tryParseElement(input, -1));
    }
}

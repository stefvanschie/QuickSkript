package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsHoldingConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "player has enchanted golden apple in main hand",
        "player has large amethyst bud in hand",
        "player have oak hanging sign in main hand",
        "player have oxidized copper lantern in hand",
        "has allay spawn egg in main hand",
        "has crimson fence gate in hand",
        "have red bed in main hand",
        "have spruce fence gate in hand",
        "player is holding snowball in main hand",
        "player is holding stone brick stairs",
        "player are holding crimson trapdoor in main hand",
        "player are holding purpur slab",
        "is holding redstone wire in main hand",
        "is holding cyan concrete powder",
        "are holding music disc wait in main hand",
        "are holding smooth sandstone slab",
        "player has not light gray dye in main hand",
        "player has not warped slab in hand",
        "player have not warped fence in main hand",
        "player have not silverfish spawn egg in hand",
        "player doesn't have chicken in main hand",
        "player doesn't have waxed copper door in hand",
        "player don't have rabbit stew in main hand",
        "player don't have waxed exposed lightning rod in hand",
        "has not chainmail helmet in main hand",
        "has not cooked cod in hand",
        "have not light weighted pressure plate in main hand",
        "have not dead bush in hand",
        "doesn't have lime dye in main hand",
        "doesn't have enderman spawn egg in hand",
        "don't have happy ghast spawn egg in main hand",
        "don't have deepslate tile slab in hand",
        "player is not holding diamond shovel in main hand",
        "player is not holding light gray bundle",
        "player isn't holding warped hanging sign in main hand",
        "player isn't holding fermented spider eye",
        "is not holding acacia trapdoor in main hand",
        "is not holding iron pickaxe",
        "isn't holding mangrove wall sign in main hand",
        "isn't holding waxed weathered copper chest",
        "player has armadillo scute in off-hand",
        "player has warped shelf in off hand",
        "player has amethyst shard in offhand",
        "player have mangrove wood in off-hand",
        "player have amethyst shard in off hand",
        "player have warped fungus on a stick in offhand",
        "has birch wood in off-hand",
        "has waxed exposed copper bulb in off hand",
        "has lime stained glass pane in offhand",
        "have birch fence in off-hand",
        "have acacia planks in off hand",
        "have waxed exposed copper bulb in offhand",
        "player is holding spruce pressure plate in off-hand",
        "player is holding copper bars in off hand",
        "player is holding salmon in offhand",
        "player are holding lightning rod in off-hand",
        "player are holding warped pressure plate in off hand",
        "player are holding bat spawn egg in offhand",
        "is holding waxed copper lantern in off-hand",
        "is holding light blue stained glass pane in off hand",
        "is holding glow squid spawn egg in offhand",
        "are holding pale oak sign in off-hand",
        "are holding red sandstone slab in off hand",
        "are holding ghast spawn egg in offhand",
        "player has not iron spear in off-hand",
        "player has not orange harness in off hand",
        "player has not waxed copper trapdoor in offhand",
        "player have not mangrove propagule in off-hand",
        "player have not copper trapdoor in off hand",
        "player have not guster pottery sherd in offhand",
        "player doesn't have netherite scrap in off-hand",
        "player doesn't have fermented spider eye in off hand",
        "player doesn't have bamboo trapdoor in offhand",
        "player don't have guster banner pattern in off-hand",
        "player don't have magenta bundle in off hand",
        "player don't have torchflower seeds in offhand",
        "has not blaze powder in off-hand",
        "has not bread in off hand",
        "has not nether star in offhand",
        "have not soul wall torch in off-hand",
        "have not netherite helmet in off hand",
        "have not target in offhand",
        "doesn't have cod bucket in off-hand",
        "doesn't have pale oak sign in off hand",
        "doesn't have music disc otherside in offhand",
        "don't have cherry wall hanging sign in off-hand",
        "don't have blackstone slab in off hand",
        "don't have oak slab in offhand",
        "player is not holding diamond leggings in off-hand",
        "player is not holding acacia sign in off hand",
        "player is not holding oak door in offhand",
        "player isn't holding music disc pigstep in off-hand",
        "player isn't holding magenta candle cake in off hand",
        "player isn't holding netherite scrap in offhand",
        "is not holding armadillo spawn egg in off-hand",
        "is not holding exposed copper golem statue in off hand",
        "is not holding prismarine brick slab in offhand",
        "isn't holding arms up pottery sherd in off-hand",
        "isn't holding black concrete powder in off hand",
        "isn't holding spectral arrow in offhand",
    })
    void test(String input) {
        assertInstanceOf(PsiIsHoldingCondition.class, loader.tryParseElement(input, -1));
    }
}

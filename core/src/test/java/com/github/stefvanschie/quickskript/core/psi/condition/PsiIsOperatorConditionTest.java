package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsOperatorConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "player is a server operators",
        "player is a server operator",
        "player is a server ops",
        "player is a server op",
        "player is server operators",
        "player is server operator",
        "player is server ops",
        "player is server op",
        "player is an operators",
        "player is an operator",
        "player is an ops",
        "player is an op",
        "player is operators",
        "player is operator",
        "player is ops",
        "player is op",
        "player are a server operators",
        "player are a server operator",
        "player are a server ops",
        "player are a server op",
        "player are server operators",
        "player are server operator",
        "player are server ops",
        "player are server op",
        "player are an operators",
        "player are an operator",
        "player are an ops",
        "player are an op",
        "player are operators",
        "player are operator",
        "player are ops",
        "player are op",
        "player isn't a server operators",
        "player isn't a server operator",
        "player isn't a server ops",
        "player isn't a server op",
        "player isn't server operators",
        "player isn't server operator",
        "player isn't server ops",
        "player isn't server op",
        "player isn't an operators",
        "player isn't an operator",
        "player isn't an ops",
        "player isn't an op",
        "player isn't operators",
        "player isn't operator",
        "player isn't ops",
        "player isn't op",
        "player is not a server operators",
        "player is not a server operator",
        "player is not a server ops",
        "player is not a server op",
        "player is not server operators",
        "player is not server operator",
        "player is not server ops",
        "player is not server op",
        "player is not an operators",
        "player is not an operator",
        "player is not an ops",
        "player is not an op",
        "player is not operators",
        "player is not operator",
        "player is not ops",
        "player is not op",
        "player aren't a server operators",
        "player aren't a server operator",
        "player aren't a server ops",
        "player aren't a server op",
        "player aren't server operators",
        "player aren't server operator",
        "player aren't server ops",
        "player aren't server op",
        "player aren't an operators",
        "player aren't an operator",
        "player aren't an ops",
        "player aren't an op",
        "player aren't operators",
        "player aren't operator",
        "player aren't ops",
        "player aren't op",
        "player are not a server operators",
        "player are not a server operator",
        "player are not a server ops",
        "player are not a server op",
        "player are not server operators",
        "player are not server operator",
        "player are not server ops",
        "player are not server op",
        "player are not an operators",
        "player are not an operator",
        "player are not an ops",
        "player are not an op",
        "player are not operators",
        "player are not operator",
        "player are not ops",
        "player are not op"
    })
    void test(String input) {
        assertInstanceOf(PsiIsOperatorCondition.class, loader.tryParseElement(input, -1));
    }
}

package com.github.stefvanschie.quickskript.core.psi.parsing;

import com.github.stefvanschie.quickskript.core.psi.TestClassBase;
import com.github.stefvanschie.quickskript.core.psi.literal.PsiNumberLiteral;
import com.github.stefvanschie.quickskript.core.psi.literal.PsiStringLiteral;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A test which tests whether each literal's parsing is correct:
 * whether the correct ones are parsed and whether the incorrect ones are not.
 */
class PsiLiteralParseTest extends TestClassBase {

    private final Map<Class<? extends PsiPrecomputedHolder<?>>, CaseHolder> inputHolders = new HashMap<>();

    PsiLiteralParseTest() {
        inputHolders.put(PsiNumberLiteral.class, new CaseHolder().setSuccess(
            "3",
            "94",
            "6.8",
            "19.7",
            "62.35",
            "-1",
            "-69",
            "-9.9",
            "-35.9",
            "-44.65"
        ).setFailure(
            "--0",
            "0.",
            ".9",
            "+8"
        ));

        inputHolders.put(PsiStringLiteral.class, new CaseHolder().setSuccess(
            "\"&cTest§f\"",
            "\"&e\"",
            "\" \""
        ).setFailure(
            "&cTest§f",
            "\"\"",
            "\""
        ));
    }


    @Test
    void test() {
        var loader = SkriptLoader.get();

        inputHolders.forEach((clazz, cases) -> {
            cases.getSuccess().forEach(input -> assertTrue(clazz.isInstance(loader.forceParseElement(input, -1))));
            cases.getFailure().forEach(input -> assertNull(loader.tryParseElement(input, -1)));
        });
    }


    private static class CaseHolder {
        private List<String> success;
        private List<String> failure;

        List<String> getSuccess() {
            return success;
        }

        List<String> getFailure() {
            return failure;
        }

        CaseHolder setSuccess(String... inputs) {
            success = Arrays.asList(inputs);
            return this;
        }

        CaseHolder setFailure(String... inputs) {
            failure = Arrays.asList(inputs);
            return this;
        }
    }
}

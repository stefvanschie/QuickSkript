package com.github.stefvanschie.quickskript.psi;

import com.github.stefvanschie.quickskript.TestClassBase;
import com.github.stefvanschie.quickskript.psi.literal.PsiNumberLiteral;
import com.github.stefvanschie.quickskript.psi.literal.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.psi.literal.PsiStringLiteral;
import com.github.stefvanschie.quickskript.skript.SkriptLoader;
import org.junit.jupiter.api.Test;

import java.util.*;

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
        SkriptLoader loader = SkriptLoader.get();

        inputHolders.forEach((clazz, cases) -> {
            cases.getSuccess().forEach(input -> assertTrue(clazz.isInstance(loader.forceParseElement(input))));
            cases.getFailure().forEach(input -> assertNull(loader.tryParseElement(input)));
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

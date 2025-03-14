package com.github.stefvanschie.quickskript.core.psi.parsing;

import com.github.stefvanschie.quickskript.core.TestClassBase;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A test which tests whether each literal's parsing is correct:
 * whether the correct ones are parsed and whether the incorrect ones are not.
 */
class PsiLiteralParseTest extends TestClassBase {

    private final Set<CaseHolder> inputHolders = new HashSet<>();

    PsiLiteralParseTest() {
        inputHolders.add(new CaseHolder().setSuccess(
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
        ));

        inputHolders.add(new CaseHolder().setSuccess(
            "\"&cTest§f\"",
            "\"&e\"",
            "\" \""
        ).setFailure(
            "&cTest§f",
            "\""
        ));
    }


    @Test
    void test() {
        inputHolders.forEach(cases -> {
            cases.getSuccess().forEach(input ->
                assertNotNull(getSkriptLoader().forceParseElement(input, -1)));
            cases.getFailure().forEach(input ->
                assertNull(getSkriptLoader().tryParseElement(input, -1)));
        });
    }


    private static class CaseHolder {
        private List<String> success = new ArrayList<>();
        private List<String> failure = new ArrayList<>();

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

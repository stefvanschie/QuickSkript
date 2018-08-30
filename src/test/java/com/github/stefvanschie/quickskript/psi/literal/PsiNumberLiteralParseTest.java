package com.github.stefvanschie.quickskript.psi.literal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PsiNumberLiteralParseTest {

    private final String[] correctCases = {
            "3",
            "94",
            "6.8",
            "19.7",
            "62.35",
            "-1",
            "-69",
            "-9.9",
            "-35.9",
            "-44.65",
    };

    private final String[] failCases = {
            "--0",
            "0.",
            ".9",
            "+8"
    };

    @Test
    void testParsing() {
        for (String correctCase : correctCases)
            assertNotNull(new PsiNumberLiteral.Factory().tryParse(correctCase));

        for (String failCase : failCases)
            assertNull(new PsiNumberLiteral.Factory().tryParse(failCase));
    }
}

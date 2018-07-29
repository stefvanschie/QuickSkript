package com.github.stefvanschie.quickskript.psi.literal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PsiNumberParseTest {

    private String[] correctCases = {
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

    private String[] failCases = {
            "--0",
            "0.",
            ".9",
            "+8"
    };

    @Test
    void testParsing() {
        for (String correctCase : correctCases)
            assertNotNull(new PsiNumber.Factory().parse(correctCase));

        for (String failCase : failCases)
            assertNull(new PsiNumber.Factory().parse(failCase));
    }
}

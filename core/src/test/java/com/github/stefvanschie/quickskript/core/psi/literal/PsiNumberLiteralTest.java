package com.github.stefvanschie.quickskript.core.psi.literal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class PsiNumberLiteralTest {

    @Test
    void testIncorrectNumberFailure() {
        Assertions.assertNull(new PsiNumberLiteral.Factory().tryParse("1:2", -1));
    }
}

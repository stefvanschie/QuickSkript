package com.github.stefvanschie.quickskript.core.psi.literal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
class PsiNumberLiteralTest {

    @Test
    void testIncorrectNumberFailure() {
        Assertions.assertNull(new PsiNumberLiteral.Factory().tryParse("1:2", -1));
    }
}

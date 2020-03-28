package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Calculates the result of an arithmetic expression
 */
public class PsiArithmeticExpression extends PsiElement<Number> {

    /**
     * The left and right hand side numbers
     */
    private PsiElement<?> left, right;

    /**
     * The operation to be performed on the {@link #left} and {@link #right} numbers
     */
    private Operation operation;

    /**
     * Creates a new element with the given line number
     *
     * @param left the left hand side number, see {@link #left}
     * @param right the right hand side number, see {@link #right}
     * @param operation the operation to perform, see {@link #operation}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiArithmeticExpression(@NotNull PsiElement<?> left, @NotNull PsiElement<?> right,
        @NotNull Operation operation, int lineNumber) {
        super(lineNumber);

        this.left = left;
        this.right = right;
        this.operation = operation;

        if (left.isPreComputed() && right.isPreComputed()) {
            preComputed = executeImpl(null);

            this.left = null;
            this.right = null;
            this.operation = null;
        }
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Number executeImpl(@Nullable Context context) {
        double leftNumber = left.execute(context, Number.class).doubleValue();
        double rightNumber = right.execute(context, Number.class).doubleValue();

        switch (operation) {
            case ADDITION:
                return leftNumber + rightNumber;
            case SUBTRACTION:
                return leftNumber - rightNumber;
            case MULTIPLICATION:
                return leftNumber * rightNumber;
            case DIVISION:
                return leftNumber / rightNumber;
            case EXPONENTIATION:
                return Math.pow(leftNumber, rightNumber);
        }

        throw new ExecutionException("Unknown operation found", lineNumber);
    }

    /**
     * A factory for creating {@link PsiArithmeticExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern to match addition {@link PsiArithmeticExpression}s
         */
        @NotNull
        private final SkriptPattern additionPattern = SkriptPattern.parse("%number%[ ]+[ ]%number%");

        /**
         * The pattern to match subtraction {@link PsiArithmeticExpression}s
         */
        @NotNull
        private final SkriptPattern subtractionPattern = SkriptPattern.parse("%number%[ ]-[ ]%number%");

        /**
         * The pattern to match multiplication {@link PsiArithmeticExpression}s
         */
        @NotNull
        private final SkriptPattern multiplicationPattern = SkriptPattern.parse("%number%[ ]*[ ]%number%");

        /**
         * The pattern to match division {@link PsiArithmeticExpression}s
         */
        @SuppressWarnings("HardcodedFileSeparator")
        @NotNull
        private final SkriptPattern divisionPattern = SkriptPattern.parse("%number%[ ]/[ ]%number%");

        /**
         * The pattern to match exponentiation {@link PsiArithmeticExpression}s
         */
        @NotNull
        private final SkriptPattern exponentiationPattern = SkriptPattern.parse("%number%[ ]^[ ]%number%");

        /**
         * This gets called upon parsing addition expressions
         *
         * @param left the left hand side number, see {@link #left}
         * @param right the right hand side number, see {@link #right}
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("additionPattern")
        public PsiArithmeticExpression parseAddition(@NotNull PsiElement<?> left, @NotNull PsiElement<?> right,
            int lineNumber) {
            return new PsiArithmeticExpression(left, right, Operation.ADDITION, lineNumber);
        }

        /**
         * This gets called upon parsing subtraction expressions
         *
         * @param left the left hand side number, see {@link #left}
         * @param right the right hand side number, see {@link #right}
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("subtractionPattern")
        public PsiArithmeticExpression parseSubtraction(@NotNull PsiElement<?> left, @NotNull PsiElement<?> right,
            int lineNumber) {
            return new PsiArithmeticExpression(left, right, Operation.SUBTRACTION, lineNumber);
        }

        /**
         * This gets called upon parsing multiplication expressions
         *
         * @param left the left hand side number, see {@link #left}
         * @param right the right hand side number, see {@link #right}
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("multiplicationPattern")
        public PsiArithmeticExpression parseMultiplication(@NotNull PsiElement<?> left, @NotNull PsiElement<?> right,
            int lineNumber) {
            return new PsiArithmeticExpression(left, right, Operation.MULTIPLICATION, lineNumber);
        }

        /**
         * This gets called upon parsing division expressions
         *
         * @param left the left hand side number, see {@link #left}
         * @param right the right hand side number, see {@link #right}
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("divisionPattern")
        public PsiArithmeticExpression parseDivision(@NotNull PsiElement<?> left, @NotNull PsiElement<?> right,
            int lineNumber) {
            return new PsiArithmeticExpression(left, right, Operation.DIVISION, lineNumber);
        }

        /**
         * This gets called upon parsing exponentiation expressions
         *
         * @param left the left hand side number, see {@link #left}
         * @param right the right hand side number, see {@link #right}
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("exponentiationPattern")
        public PsiArithmeticExpression parseExponentiation(@NotNull PsiElement<?> left, @NotNull PsiElement<?> right,
            int lineNumber) {
            return new PsiArithmeticExpression(left, right, Operation.EXPONENTIATION, lineNumber);
        }
    }

    /**
     * Represents the operation that should be performed on the two numbers
     *
     * @since 0.1.0
     */
    public enum Operation {

        /**
         * Adds two numbers together
         *
         * @since 0.1.0
         */
        ADDITION,

        /**
         * Subtracts the right number from the left number
         *
         * @since 0.1.0
         */
        SUBTRACTION,

        /**
         * Multiplies the two numbers together
         *
         * @since 0.1.0
         */
        MULTIPLICATION,

        /**
         * Divides the left number by the right number
         *
         * @since 0.1.0
         */
        DIVISION,

        /**
         * Exponentiates the left number by the right number
         *
         * @since 0.1.0
         */
        EXPONENTIATION
    }
}

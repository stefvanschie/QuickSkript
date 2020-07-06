package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.util.literal.Money;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets a certain amount of money. This has no integration with Vault, so it simply considers all numbers with special
 * stuff around it money.
 *
 * @since 0.1.0
 */
public class PsiMoneyLiteral extends PsiPrecomputedHolder<Money> {

    /**
     * Creates a new element with the given line number
     *
     * @param money the money this literal represents
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiMoneyLiteral(@NotNull Money money, int lineNumber) {
        super(money, lineNumber);
    }

    /**
     * A factory for creating {@link PsiMoneyLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * This gets called upon parsing
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiMoneyLiteral parse(@NotNull String text, int lineNumber) {
            if (text.matches("-?\\d+(.\\d+)?")) {
                return null;
            }

            StringBuilder amount = new StringBuilder();

            for (int i = 0; i < text.length(); i++) {
                char character = text.charAt(i);

                if ((character < '0' || character > '9') && character != '-' && character != '.') {
                    continue;
                }

                amount.append(character);
            }

            String amountString = amount.toString();

            if (!amountString.matches("-?\\d+(.\\d+)?")) {
                return null;
            }

            return create(new Money(Double.parseDouble(amountString)), lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiMoneyLiteral create(@NotNull Money money, int lineNumber) {
            return new PsiMoneyLiteral(money, lineNumber);
        }
    }
}

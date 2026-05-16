package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks whether the offline players are operators.
 *
 * @since 0.1.0
 */
public class PsiIsOperatorCondition extends PsiElement<Boolean> {

    /**
     * The offline players to check if they are operators.
     */
    @NotNull
    protected final PsiElement<?> offlinePlayers;

    /**
     * If false, the result is negated.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param offlinePlayers the offline players to check if they are operators
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsOperatorCondition(@NotNull PsiElement<?> offlinePlayers, boolean positive, int lineNumber) {
        super(lineNumber);

        this.offlinePlayers = offlinePlayers;
        this.positive = positive;
    }

    /**
     * A factory for creating instances of {@link PsiIsOperatorCondition}.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param offlinePlayers the offline players to check if they are operators
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%offline players% (is|are) [[a] server|an] op[erator][s]")
        public PsiIsOperatorCondition parsePositive(@NotNull PsiElement<?> offlinePlayers, int lineNumber) {
            return create(offlinePlayers, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param offlinePlayers the offline players to check if they are operators
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%offline players% (isn't|is not|aren't|are not) [[a] server|an] op[erator][s]")
        public PsiIsOperatorCondition parseNegative(@NotNull PsiElement<?> offlinePlayers, int lineNumber) {
            return create(offlinePlayers, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the PsiIsOnlineConditiongiven parameters as
         * constructor parameters.
         *
         * @param offlinePlayers the offline players to check if they are operators
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        public PsiIsOperatorCondition create(@NotNull PsiElement<?> offlinePlayers, boolean positive, int lineNumber) {
            return new PsiIsOperatorCondition(offlinePlayers, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public String getType() {
            return "boolean";
        }
    }
}

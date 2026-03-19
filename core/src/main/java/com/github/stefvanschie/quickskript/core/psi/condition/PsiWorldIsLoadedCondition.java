package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if worlds are loaded.
 *
 * @since 0.1.0
 */
public class PsiWorldIsLoadedCondition extends PsiElement<Boolean> {

    /**
     * The worlds to check if they are loaded.
     */
    @NotNull
    protected final PsiElement<?> worlds;

    /**
     * If false, the result is negated.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param worlds the worlds to check if they are loaded
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiWorldIsLoadedCondition(@NotNull PsiElement<?> worlds, boolean positive, int lineNumber) {
        super(lineNumber);

        this.worlds = worlds;
        this.positive = positive;
    }

    /**
     * A factory for creating instances of {@link PsiWorldIsLoadedCondition}.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param worlds the worlds to check if they are loaded
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("[world[s]] %worlds% (is|are) loaded")
        public PsiWorldIsLoadedCondition parsePositive(@NotNull PsiElement<?> worlds, int lineNumber) {
            return create(worlds, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param worlds the worlds to check if they are loaded
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("[world[s]] %worlds% (is|are)(n't| not) loaded")
        public PsiWorldIsLoadedCondition parseNegative(@NotNull PsiElement<?> worlds, int lineNumber) {
            return create(worlds, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param worlds the worlds to check if they are loaded
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        protected PsiWorldIsLoadedCondition create(@NotNull PsiElement<?> worlds, boolean positive, int lineNumber) {
            return new PsiWorldIsLoadedCondition(worlds, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}

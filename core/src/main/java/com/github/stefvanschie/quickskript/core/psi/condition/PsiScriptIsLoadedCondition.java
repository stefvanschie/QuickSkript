package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if a script is loaded.
 *
 * @since 0.1.0
 */
public class PsiScriptIsLoadedCondition extends PsiElement<Boolean> {

    /**
     * The scripts to check if they are loaded.
     */
    @NotNull
    protected final PsiElement<?> scripts;

    /**
     * If false, the result is negated.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param scripts the scripts to check if they are loaded
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiScriptIsLoadedCondition(@NotNull PsiElement<?> scripts, boolean positive, int lineNumber) {
        super(lineNumber);

        this.scripts = scripts;
        this.positive = positive;
    }

    /**
     * A factory for creating instances of {@link PsiScriptIsLoadedCondition}.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param scripts the scripts to check if they are loaded
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("[script[s]] %scripts% (is|are) loaded")
        public PsiScriptIsLoadedCondition parsePositive(@NotNull PsiElement<?> scripts, int lineNumber) {
            return create(scripts, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param scripts the scripts to check if they are loaded
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("[script[s]] %scripts% (is|are)(n't| not) loaded")
        public PsiScriptIsLoadedCondition parseNegative(@NotNull PsiElement<?> scripts, int lineNumber) {
            return create(scripts, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param scripts the scripts to check if they are loaded
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the can see condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        public PsiScriptIsLoadedCondition create(@NotNull PsiElement<?> scripts, boolean positive, int lineNumber) {
            return new PsiScriptIsLoadedCondition(scripts, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}

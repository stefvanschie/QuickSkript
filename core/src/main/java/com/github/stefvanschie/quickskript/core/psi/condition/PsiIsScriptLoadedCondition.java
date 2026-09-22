package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the provided scripts are loaded.
 *
 * @since 0.1.0
 */
public class PsiIsScriptLoadedCondition extends PsiElement<Boolean> {

    /**
     * The names of the scripts to check if they are loaded.
     */
    @Nullable
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
    protected PsiIsScriptLoadedCondition(@Nullable PsiElement<?> scripts, boolean positive, int lineNumber) {
        super(lineNumber);

        this.scripts = scripts;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiIsScriptLoadedCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param scripts the scripts to check if they are loaded
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("script[s] [%strings%] (is|are) loaded")
        public PsiIsScriptLoadedCondition parsePositive(@Nullable PsiElement<?> scripts, int lineNumber) {
            return create(scripts, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param scripts the scripts to check if they are loaded
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("script[s] [%strings%] (isn't|is not|aren't|are not) loaded")
        public PsiIsScriptLoadedCondition parseNegative(@Nullable PsiElement<?> scripts, int lineNumber) {
            return create(scripts, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param scripts the scripts to check if they are loaded
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        public PsiIsScriptLoadedCondition create(@Nullable PsiElement<?> scripts, boolean positive, int lineNumber) {
            return new PsiIsScriptLoadedCondition(scripts, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public String getType() {
            return "boolean";
        }
    }
}
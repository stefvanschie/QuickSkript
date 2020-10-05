package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

/**
 * Gets the current time and date
 *
 * @since 0.1.0
 */
public class PsiNowExpression extends PsiElement<LocalDateTime> {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiNowExpression(int lineNumber) {
        super(lineNumber);
    }

    @Nullable
    @Override
    protected LocalDateTime executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return LocalDateTime.now();
    }

    /**
     * A factory for creating {@link PsiNowExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiNowExpression}s
         */
        @NotNull
        private SkriptPattern pattern = SkriptPattern.parse("now");

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiNowExpression parse(int lineNumber) {
            return create(lineNumber);
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
        public PsiNowExpression create(int lineNumber) {
            return new PsiNowExpression(lineNumber);
        }
    }
}

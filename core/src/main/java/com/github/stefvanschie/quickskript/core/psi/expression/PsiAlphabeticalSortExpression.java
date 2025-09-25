package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Sorts the given texts in alphabetical order.
 *
 * @since 0.1.0
 */
public class PsiAlphabeticalSortExpression extends PsiElement<String[]> {

    /**
     * A single text element or a collection of text elements
     */
    private PsiElement<?> texts;

    /**
     * Creates a new element with the given line number
     *
     * @param texts the texts to sort
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiAlphabeticalSortExpression(@NotNull PsiElement<?> texts, int lineNumber) {
        super(lineNumber);

        this.texts = texts;

        if (texts.isPreComputed()) {
            preComputed = executeImpl(null, null);

            this.texts = null;
        }
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected String[] executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return this.texts.executeMulti(environment, context).stream()
                .map(Object::toString)
                .sorted()
                .toArray(String[]::new);
    }

    /**
     * A factory for creating {@link PsiAlphabeticalSortExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * This gets called upon parsing
         *
         * @param texts the texts to sort
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("alphabetically sorted %texts%")
        public PsiAlphabeticalSortExpression parse(@NotNull PsiElement<?> texts, int lineNumber) {
            return new PsiAlphabeticalSortExpression(texts, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.TEXTS;
        }
    }
}

package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Joins texts together with the given delimiter
 *
 * @since 0.1.0
 */
public class PsiJoinExpression extends PsiElement<String> {

    /**
     * The texts to join together
     */
    private PsiElement<?> texts;

    /**
     * The delimiter
     */
    private PsiElement<?> delimiter;

    /**
     * Creates a new element with the given line number
     *
     * @param texts the texts to join together
     * @param delimiter the delimiter
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiJoinExpression(@NotNull PsiElement<?> texts, @Nullable PsiElement<?> delimiter, int lineNumber) {
        super(lineNumber);

        this.texts = texts;
        this.delimiter = delimiter;

        if (texts.isPreComputed() && (delimiter == null || delimiter.isPreComputed())) {
            preComputed = executeImpl(null, null);

            this.texts = null;
            this.delimiter = null;
        }
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected String executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        List<String> texts = new ArrayList<>();

        this.texts.executeMulti(environment, context).forEach(e -> texts.add(e.toString()));

        if (texts.isEmpty()) {
            return "";
        }

        String delimiter = this.delimiter == null ? ", " : this.delimiter.execute(environment, context, String.class);
        StringBuilder parts = new StringBuilder(texts.get(0));

        for (int index = 1; index < texts.size(); index++) {
            parts.append(delimiter);
            parts.append(texts.get(index));
        }

        return parts.toString();
    }

    /**
     * A factory for creating {@link PsiJoinExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiJoinExpression}s
         */
        @NotNull
        private final SkriptPattern pattern =
            SkriptPattern.parse("(concat[enate]|join) %texts% [(with|using|by) [[the] delimiter] %text%]");

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param texts the texts to join together
         * @param delimiter the delimiter
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiJoinExpression parse(@NotNull PsiElement<?> texts, @Nullable PsiElement<?> delimiter,
            int lineNumber) {
            return create(texts, delimiter, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param texts the texts to join together
         * @param delimiter the delimiter
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiJoinExpression create(@NotNull PsiElement<?> texts, @Nullable PsiElement<?> delimiter,
            int lineNumber) {
            return new PsiJoinExpression(texts, delimiter, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.TEXT;
        }
    }
}

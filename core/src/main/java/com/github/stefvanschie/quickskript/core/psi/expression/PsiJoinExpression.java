package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import com.github.stefvanschie.quickskript.core.util.text.TextPart;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Joins texts together with the given delimiter
 *
 * @since 0.1.0
 */
public class PsiJoinExpression extends PsiElement<Text> {

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
            preComputed = executeImpl(null);

            this.texts = null;
            this.delimiter = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Contract(pure = true)
    @Override
    protected Text executeImpl(@Nullable Context context) {
        Object object = texts.execute(context);

        List<Text> texts;

        if (object instanceof Iterable) {
            texts = new ArrayList<>();
            for (Text text : (Iterable<Text>) object) {
                texts.add(text);
            }
        } else if (object instanceof Text[]) {
            texts = Arrays.asList((Text[]) object);
        } else if (object != null && object.getClass().isArray()) {
            texts = new ArrayList<>(Array.getLength(object));
            for (int index = 0; index < Array.getLength(object); index++) {
                texts.add((Text) Array.get(object, index));
            }
        } else if (object instanceof Text) {
            texts = Collections.singletonList((Text) object);
        } else {
            throw new ExecutionException("Can only join text(s)", lineNumber);
        }

        if (texts.isEmpty()) {
            return Text.empty();
        }

        Text delimiter = this.delimiter == null ? Text.parseLiteral(", ") : this.delimiter.execute(context, Text.class);
        List<TextPart> parts = new ArrayList<>(texts.get(0).getParts());

        for (int index = 1; index < texts.size(); index++) {
            parts.addAll(delimiter.getParts());
            parts.addAll(texts.get(index).getParts());
        }

        return new Text(parts);
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
    }
}

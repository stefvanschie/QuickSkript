package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Sorts the given texts in alphabetical order.
 *
 * @since 0.1.0
 */
public class PsiAlphabeticalSortExpression extends PsiElement<Text[]> {

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
            preComputed = executeImpl(null);

            this.texts = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Contract(pure = true)
    @Override
    protected Text[] executeImpl(@Nullable Context context) {
        Text[] texts;
        Object result = this.texts.execute(context);

        if (result instanceof Iterable) {
            List<Text> textsList = new ArrayList<>();

            for (Object obj : ((Iterable<?>) result)) {
                textsList.add((Text) obj);
            }

            texts = textsList.toArray(Text[]::new);
        } else if (result instanceof Object[]) {
            Object[] objects = (Object[]) result;
            texts = new Text[objects.length];

            for (int i = 0; i < objects.length; i++) {
                texts[i] = (Text) objects[i];
            }
        } else if (result.getClass().isArray()) {
            int length = Array.getLength(result);
            texts = new Text[length];

            for (int i = 0; i < length; ++i) {
                texts[i] = (Text) Array.get(result, i);
            }
        } else {
            texts = new Text[] {(Text) result};
        }

        Arrays.sort(texts);

        return texts;
    }

    /**
     * A factory for creating {@link PsiAlphabeticalSortExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiAlphabeticalSortExpression}s
         */
        @NotNull
        private final SkriptPattern pattern = SkriptPattern.parse("alphabetically sorted %texts%");

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
        @Pattern("pattern")
        public PsiAlphabeticalSortExpression parse(@NotNull PsiElement<?> texts, int lineNumber) {
            return new PsiAlphabeticalSortExpression(texts, lineNumber);
        }
    }
}

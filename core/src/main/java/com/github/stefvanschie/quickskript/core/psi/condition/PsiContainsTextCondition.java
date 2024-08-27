package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if a given text contains another text.
 *
 * @since 0.1.0
 */
public class PsiContainsTextCondition extends PsiElement<Boolean> {

    /**
     * The text to check.
     */
    private PsiElement<?> text;

    /**
     * the part to check if contained by text.
     */
    private PsiElement<?> part;

    /**
     * If false, the result will be inverted.
     */
    private final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param text the text to check
     * @param part the part to check if contained by text
     * @param positive if false, the result will be inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiContainsTextCondition(
        @NotNull PsiElement<?> text,
        @NotNull PsiElement<?> part,
        boolean positive,
        int lineNumber
    ) {
        super(lineNumber);

        this.text = text;
        this.part = part;
        this.positive = positive;

        if (text.isPreComputed() && part.isPreComputed()) {
            super.preComputed = executeImpl(null, null);

            this.text = null;
            this.part = null;
        }
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        MultiResult<? extends String> texts = this.text.executeMulti(environment, context, String.class);
        MultiResult<? extends String> parts = this.part.executeMulti(environment, context, String.class);

        return this.positive == texts.test(text -> parts.test(text::contains));
    }

    /**
     * A factory for creating {@link PsiContainsTextCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching positive {@link PsiContainsTextCondition}s
         */
        @NotNull
        private final SkriptPattern positivePattern = SkriptPattern.parse("%texts% contain[s] %texts%");

        /**
         * The pattern for matching negative {@link PsiContainsTextCondition}s
         */
        @NotNull
        private final SkriptPattern negativePattern = SkriptPattern.parse(
            "%texts% (doesn't|does not|do not|don't) contain %texts%"
        );

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param text the text to check
         * @param part the part to check if contained by text
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiContainsTextCondition parsePositive(
            @NotNull PsiElement<?> text,
            @NotNull PsiElement<?> part,
            int lineNumber
        ) {
            return create(text, part, true, lineNumber);
        }

        /**
         * Parses the {@link #negativePattern} and invokes this method with its types if the match succeeds
         *
         * @param text the text to check
         * @param part the part to check if contained by text
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiContainsTextCondition parseNegative(
            @NotNull PsiElement<?> text,
            @NotNull PsiElement<?> part,
            int lineNumber
        ) {
            return create(text, part, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param text the text to check
         * @param part the part to check if contained by text
         * @param positive if false, the result is inverted
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiContainsTextCondition create(
            @NotNull PsiElement<?> text,
            @NotNull PsiElement<?> part,
            boolean positive,
            int lineNumber
        ) {
            return new PsiContainsTextCondition(text, part, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}

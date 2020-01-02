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

import java.util.Locale;

/**
 * Changes the capitalization of text to all uppercase or all lowercase.
 *
 * @since 0.1.0
 */
public class PsiCapitalizationTextExpression extends PsiElement<Text> {

    /**
     * The capitalization to change the text to
     */
    private Capitalization capitalization;

    /**
     * The text to change the capitalization of
     */
    private PsiElement<?> text;

    /**
     * Creates a new element with the given line number
     *
     * @param capitalization the capitalization to change the text to
     * @param text the text to change the capitalization of
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiCapitalizationTextExpression(@NotNull Capitalization capitalization, @NotNull PsiElement<?> text,
        int lineNumber) {
        super(lineNumber);

        this.capitalization = capitalization;
        this.text = text;

        if (text.isPreComputed()) {
            preComputed = executeImpl(null);

            this.capitalization = null;
            this.text = null;
        }
    }

    @Nullable
    @Override
    protected Text executeImpl(@Nullable Context context) {
        String text = this.text.execute(context, Text.class).toString();
        Locale locale = Locale.getDefault();
        StringBuilder newText = new StringBuilder(text.length());

        if (capitalization == Capitalization.LOWER) {
            newText.append(text.toLowerCase(locale));
        } else if (capitalization == Capitalization.UPPER) {
            newText.append(text.toUpperCase(locale));
        } else if (capitalization == Capitalization.TITLE) {
            String[] parts = text.split(" ");

            for (String part : parts) {
                newText
                    .append(part.substring(0, 1).toUpperCase(locale))
                    .append(part.substring(1).toLowerCase(locale))
                    .append(' ');
            }

            newText.setLength(newText.length() - 1);
        }

        return Text.parse(newText.toString());
    }

    /**
     * A factory for creating {@link PsiCapitalizationTextExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching lowercase {@link PsiCapitalizationTextExpression}s
         */
        @NotNull
        private SkriptPattern lowerPattern = SkriptPattern.parse("%text% in lower case");

        /**
         * The pattern for matching uppercase {@link PsiCapitalizationTextExpression}s
         */
        @NotNull
        private SkriptPattern upperPattern = SkriptPattern.parse("%text% in upper case");

        /**
         * The pattern for matching titlecase {@link PsiCapitalizationTextExpression}s
         */
        @NotNull
        private SkriptPattern titlePattern = SkriptPattern.parse("capitalized %text%");

        /**
         * Parses the {@link #lowerPattern} and invokes this method with its types if the match succeeds
         *
         * @param text the text to change the capitalization of
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("lowerPattern")
        public PsiCapitalizationTextExpression parseLower(@NotNull PsiElement<?> text, int lineNumber) {
            return create(Capitalization.LOWER, text, lineNumber);
        }

        /**
         * Parses the {@link #upperPattern} and invokes this method with its types if the match succeeds
         *
         * @param text the text to change the capitalization of
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("upperPattern")
        public PsiCapitalizationTextExpression parseUpper(@NotNull PsiElement<?> text, int lineNumber) {
            return create(Capitalization.UPPER, text, lineNumber);
        }

        /**
         * Parses the {@link #titlePattern} and invokes this method with its types if the match succeeds
         *
         * @param text the text to change the capitalization of
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("titlePattern")
        public PsiCapitalizationTextExpression parseTitle(@NotNull PsiElement<?> text, int lineNumber) {
            return create(Capitalization.TITLE, text, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param capitalization the capitalization to change the text to
         * @param text the text to change the capitalization of
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiCapitalizationTextExpression create(@NotNull Capitalization capitalization,
            @NotNull PsiElement<?> text, int lineNumber) {
            return new PsiCapitalizationTextExpression(capitalization, text, lineNumber);
        }
    }

    /**
     * The capitalization to change the text to
     *
     * @since 0.1.0
     */
    public enum Capitalization {

        /**
         * Indicates that the text should be changed to lowercase
         *
         * @since 0.1.0
         */
        LOWER,

        /**
         * Indicates that the text should be changed to uppercase
         *
         * @since 0.1.0
         */
        UPPER,

        /**
         * Indicates that the text should be changed to title case
         *
         * @since 0.1.0
         */
        TITLE
    }
}

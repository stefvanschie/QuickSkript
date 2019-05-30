package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Checks whether a given text starts with another given text. This may be pre computed, if the texts are also pre
 * computed.
 *
 * @since 0.1.0
 */
public class PsiStartsWithCondition extends PsiElement<Boolean> {

    /**
     * The text that will be checked
     */
    private PsiElement<?> text;

    /**
     * The prefix to check for
     */
    private PsiElement<?> prefix;

    /**
     * False if the result of execution needs to be inverted
     */
    private final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param text the text that will be checked
     * @param prefix the prefix to check for
     * @param positive false if the result of execution needs to be inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiStartsWithCondition(@NotNull PsiElement<?> text, @NotNull PsiElement<?> prefix, boolean positive,
                                     int lineNumber) {
        super(lineNumber);

        this.text = text;
        this.prefix = prefix;
        this.positive = positive;

        if (text.isPreComputed() && prefix.isPreComputed()) {
            preComputed = executeImpl(null);

            this.text = null;
            this.prefix = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        Text text = this.text.execute(context, Text.class);

        return positive == text.toString().startsWith(prefix.execute(context, Text.class).toString());
    }

    /**
     * A factory for creating {@link PsiStartsWithCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiStartsWithCondition> {

        /**
         * A pattern for matching positive {@link PsiStartsWithCondition}s
         */
        @NotNull
        private final Pattern positivePattern = Pattern.compile("(?<text>[\\s\\S]+) starts? with (?<prefix>[\\s\\S]+)");

        /**
         * A pattern for matching negative {@link PsiStartsWithCondition}s
         */
        @NotNull
        private final Pattern negativePattern =
            Pattern.compile("(?<text>[\\s\\S]+) (?:doesn't|does not|do not|don't) start with (?<prefix>[\\s\\S]+)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiStartsWithCondition tryParse(@NotNull String text, int lineNumber) {
            var skriptLoader = SkriptLoader.get();

            Matcher positiveMatcher = positivePattern.matcher(text);

            if (positiveMatcher.matches()) {
                String textGroup = positiveMatcher.group("text");
                String prefixGroup = positiveMatcher.group("prefix");

                PsiElement<?> textElement = skriptLoader.forceParseElement(textGroup, lineNumber);
                PsiElement<?> prefix = skriptLoader.forceParseElement(prefixGroup, lineNumber);

                return create(textElement, prefix, true, lineNumber);
            }

            Matcher negativeMatcher = negativePattern.matcher(text);

            if (negativeMatcher.matches()) {
                String textGroup = negativeMatcher.group("text");
                String prefixGroup = negativeMatcher.group("prefix");

                PsiElement<?> textElement = skriptLoader.forceParseElement(textGroup, lineNumber);
                PsiElement<?> prefix = skriptLoader.forceParseElement(prefixGroup, lineNumber);

                return create(textElement, prefix, false, lineNumber);
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param text the text to check
         * @param prefix the prefix to find in the text
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        private PsiStartsWithCondition create(@NotNull PsiElement<?> text, @NotNull PsiElement<?> prefix,
                                              boolean positive, int lineNumber) {
            return new PsiStartsWithCondition(text, prefix, positive, lineNumber);
        }
    }
}

package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiConverter;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A psi element which holds numbers. This is always pre computed.
 *
 * @since 0.1.0
 */
public class PsiNumberLiteral extends PsiPrecomputedHolder<Double> {

    /**
     * Creates a new psi number
     *
     * @param number the number this psi is wrapping
     * @param lineNumber the line number
     * @since 0.1.0
     */
    private PsiNumberLiteral(double number, int lineNumber) {
        super(number, lineNumber);
    }

    /**
     * The factory for creating {@link PsiNumberLiteral}s.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching number literals
         */
        @NotNull
        private final Pattern pattern = Pattern.compile("-?\\d+(?:.\\d+)?");

        /**
         * This gets called upon parsing
         *
         * @param text the text to parse
         * @param lineNumber the line number
         * @return the function, or null to indicate failure
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiNumberLiteral tryParse(@NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                return null;
            }

            return create(Double.parseDouble(text), lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param value the value of the literal
         * @param lineNumber the line number
         * @return the literal
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        protected PsiNumberLiteral create(double value, int lineNumber) {
            return new PsiNumberLiteral(value, lineNumber);
        }
    }

    /**
     * A converter to convert other types to this one
     *
     * @since 0.1.0
     */
    public static class Converter implements PsiConverter<PsiNumberLiteral> {

        @Nullable
        @Contract(pure = true)
        @Override
        public PsiNumberLiteral convert(@NotNull Object object, int lineNumber) {
            return new PsiNumberLiteral(Double.parseDouble(object.toString()), lineNumber);
        }
    }
}

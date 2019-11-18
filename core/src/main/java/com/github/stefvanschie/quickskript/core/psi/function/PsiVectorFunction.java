package com.github.stefvanschie.quickskript.core.psi.function;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Creates a vector
 *
 * @since 0.1.0
 */
public class PsiVectorFunction extends PsiElement<Object> {

    /**
     * Three coordinates for the vector
     */
    protected PsiElement<?> x, y, z;

    /**
     * Creates a new vector function
     *
     * @param x the x value
     * @param y the y value
     * @param z the z value
     * @param lineNumber the line number
     * @since 0.1.0
     */
    protected PsiVectorFunction(@NotNull PsiElement<?> x, @NotNull PsiElement<?> y, @NotNull PsiElement<?> z,
                                int lineNumber) {
        super(lineNumber);

        this.x = x;
        this.y = y;
        this.z = z;

        if (this.x.isPreComputed() && this.y.isPreComputed() && this.z.isPreComputed()) {
            preComputed = executeImpl(null);
            this.x = this.y = this.z = null;
        }
    }

    /**
     * A factory for creating vector functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching vector expressions
         */
        @NotNull
        private final Pattern pattern = Pattern.compile("vector\\((?<parameters>[\\s\\S]+)\\)");

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
        public PsiVectorFunction tryParse(@NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                return null;
            }

            String[] values = matcher.group("parameters").replace(" ", "").split(",");

            if (values.length != 3)
                return null;

            var skriptLoader = SkriptLoader.get();

            PsiElement<?> x = skriptLoader.forceParseElement(values[0], lineNumber);
            PsiElement<?> y = skriptLoader.forceParseElement(values[1], lineNumber);
            PsiElement<?> z = skriptLoader.forceParseElement(values[2], lineNumber);

            return create(x, y, z, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param x the x component of the vector
         * @param y the y component of the vector
         * @param z the z component of the vector
         * @param lineNumber the line number
         * @return the function
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        protected PsiVectorFunction create(@NotNull PsiElement<?> x, @NotNull PsiElement<?> y, @NotNull PsiElement<?> z,
                                           int lineNumber) {
            return new PsiVectorFunction(x, y, z, lineNumber);
        }
    }
}

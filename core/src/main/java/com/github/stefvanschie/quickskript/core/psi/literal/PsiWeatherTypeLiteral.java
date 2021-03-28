package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.util.Type;
import com.github.stefvanschie.quickskript.core.util.literal.WeatherType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a weather type
 *
 * @since 0.1.0
 */
public class PsiWeatherTypeLiteral extends PsiPrecomputedHolder<WeatherType> {

    /**
     * Creates a new element with the given line number
     *
     * @param weatherType the weather type
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiWeatherTypeLiteral(@NotNull WeatherType weatherType, int lineNumber) {
        super(weatherType, lineNumber);
    }

    /**
     * A factory for creating {@link PsiWeatherTypeLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * This gets called whenever an attempt at parsing a tree type is made
         *
         * @param text the text to be parsed
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiWeatherTypeLiteral parse(@NotNull String text, int lineNumber) {
            WeatherType weatherType = WeatherType.byName(text.toLowerCase());

            if (weatherType == null) {
                return null;
            }

            return create(weatherType, lineNumber);
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
        public PsiWeatherTypeLiteral create(@NotNull WeatherType weatherType, int lineNumber) {
            return new PsiWeatherTypeLiteral(weatherType, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.OBJECT;
        }
    }
}

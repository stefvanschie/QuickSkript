package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.util.literal.Time;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a tiem in a Minecraft world
 *
 * @since 0.1.0
 */
public class PsiTimeLiteral extends PsiPrecomputedHolder<Time> {

    /**
     * Creates a new element with the given line number
     *
     * @param time the time this represents
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiTimeLiteral(@NotNull Time time, int lineNumber) {
        super(time, lineNumber);
    }

    /**
     * A factory for creating {@link PsiTimeLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * This gets called whenever an attempt at parsing a time is made
         *
         * @param text the text to match
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiTimeLiteral parse(@NotNull String text, int lineNumber) {
            Time time = Time.parse(text, lineNumber);

            if (time == null) {
                return null;
            }

            return create(time, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param time the time
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiTimeLiteral create(@NotNull Time time, int lineNumber) {
            return new PsiTimeLiteral(time, lineNumber);
        }
    }
}

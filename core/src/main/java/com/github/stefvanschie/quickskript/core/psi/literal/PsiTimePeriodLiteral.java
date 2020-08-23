package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.util.literal.Time;
import com.github.stefvanschie.quickskript.core.util.literal.TimePeriod;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a time period
 *
 * @since 0.1.0
 */
public class PsiTimePeriodLiteral extends PsiPrecomputedHolder<TimePeriod> {

    /**
     * Creates a new element with the given line number
     *
     * @param timePeriod the time period
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiTimePeriodLiteral(@NotNull TimePeriod timePeriod, int lineNumber) {
        super(timePeriod, lineNumber);
    }

    /**
     * A factory for creating {@link PsiTimePeriodLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * This gets called whenever an attempt at parsing a time period is made
         *
         * @param text the text to parse
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiTimePeriodLiteral parse(@NotNull String text, int lineNumber) {
            if (text.equalsIgnoreCase("dawn")) {
                return create(TimePeriod.DAWN, lineNumber);
            }

            if (text.equalsIgnoreCase("day")) {
                return create(TimePeriod.DAY, lineNumber);
            }

            if (text.equalsIgnoreCase("dusk")) {
                return create(TimePeriod.DUSK, lineNumber);
            }

            if (text.equalsIgnoreCase("night")) {
                return create(TimePeriod.NIGHT, lineNumber);
            }

            int index = text.indexOf('-');

            if (index == -1) {
                Time time = Time.parse(text, lineNumber);

                if (time == null) {
                    return null;
                }

                return create(new TimePeriod(time, time), lineNumber);
            }

            Time start = Time.parse(text.substring(0, index).trim(), lineNumber);

            if (start == null) {
                return null;
            }

            Time end = Time.parse(text.substring(index + 1).trim(), lineNumber);

            if (end == null) {
                return null;
            }

            return create(new TimePeriod(start, end), lineNumber);
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
        public PsiTimePeriodLiteral create(@NotNull TimePeriod timePeriod, int lineNumber) {
            return new PsiTimePeriodLiteral(timePeriod, lineNumber);
        }
    }
}

package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.pattern.group.RegexGroup;
import com.github.stefvanschie.quickskript.core.pattern.group.SkriptPatternGroup;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.exception.ParseException;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Pair;
import com.github.stefvanschie.quickskript.core.util.literal.Time;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
         * The pattern for matching 24 hour {@link PsiTimeLiteral}s
         */
        @NotNull
        private final SkriptPattern pattern24Hour = SkriptPattern.parse("<\\d{1,2}>:<\\d{1,2}>");

        /**
         * The pattern for matching 12 hour {@link PsiTimeLiteral}s
         */
        @NotNull
        private final SkriptPattern pattern12Hour = SkriptPattern.parse("<\\d{1,2}>[:<\\d{1,2}>][ ](0\u00A6am|1\u00A6pm)");

        /**
         * Parses the {@link #pattern24Hour} and invokes this method with its types if the match succeeds
         *
         * @param result the skript match result
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern24Hour")
        public PsiTimeLiteral parse24Hour(@NotNull SkriptMatchResult result, int lineNumber) {
            int hours = -1;
            int minutes = -1;

            for (Pair<SkriptPatternGroup, String> pair : result.getMatchedGroups()) {
                if (!(pair.getX() instanceof RegexGroup)) {
                    continue;
                }

                if (hours == -1) {
                    hours = Integer.parseInt(pair.getY());
                } else {
                    minutes = Integer.parseInt(pair.getY());
                    break;
                }
            }

            if (hours > 24) {
                throw new ParseException("Hours in time cannot be greater than 24 hours", lineNumber);
            }

            if (minutes >= 60) {
                throw new ParseException("Minutes in time cannot be greater or equal to 60 minutes", lineNumber);
            }

            int ticks = (int) Math.round((hours * 1000) + (minutes * (1000.0 / 60.0)) - 6000);

            if (ticks < 0) {
                ticks += 24000;
            }

            return create(new Time(ticks), lineNumber);
        }

        /**
         * Parses the {@link #pattern24Hour} and invokes this method with its types if the match succeeds
         *
         * @param result the skript match result
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern12Hour")
        public PsiTimeLiteral parse12Hour(@NotNull SkriptMatchResult result, int lineNumber) {
            int hours = -1;
            int minutes = -1;

            for (Pair<SkriptPatternGroup, String> pair : result.getMatchedGroups()) {
                if (!(pair.getX() instanceof RegexGroup)) {
                    continue;
                }

                String regex = pair.getY();

                if (hours == -1) {
                    hours = Integer.parseInt(regex);
                } else {
                    if (regex.isBlank()) {
                        minutes = 0;
                    } else {
                        minutes = Integer.parseInt(regex);
                    }

                    break;
                }
            }

            if (hours == 12) {
                hours = 0;
            } else if (hours > 12) {
                throw new ParseException("Hours in time cannot be greater than 12 hours", lineNumber);
            }

            if (minutes >= 60) {
                throw new ParseException("Minutes in time cannot be greater or equal to 60 minutes", lineNumber);
            }

            if (result.getParseMark() == 1) {
                hours += 12;
            }

            int ticks = (int) Math.round((hours * 1000) + (minutes * (1000.0 / 60.0)) - 6000);

            if (ticks < 0) {
                ticks += 24000;
            }

            return create(new Time(ticks), lineNumber);
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

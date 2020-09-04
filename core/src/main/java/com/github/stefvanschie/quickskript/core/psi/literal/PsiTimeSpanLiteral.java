package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.pattern.group.RegexGroup;
import com.github.stefvanschie.quickskript.core.pattern.group.SkriptPatternGroup;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.exception.ParseException;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Pair;
import com.github.stefvanschie.quickskript.core.util.literal.TimeSpan;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a time span
 *
 * @since 0.1.0
 */
public class PsiTimeSpanLiteral extends PsiPrecomputedHolder<TimeSpan> {

    /**
     * Creates a new element with the given line number
     *
     * @param timeSpan the time span
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiTimeSpanLiteral(@NotNull TimeSpan timeSpan, int lineNumber) {
        super(timeSpan, lineNumber);
    }

    /**
     * A factory for creating {@link PsiTimeSpanLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiTimeSpanLiteral}s
         */
        @NotNull
        private SkriptPattern pattern = SkriptPattern.parse("[1\u00A6<\\d{1,3}>:]<\\d{1,2}>:<\\d{1,2}>[2\u00A6<\\.\\d{1,3}>]");

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param result the match result
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiTimeSpanLiteral parse(@NotNull SkriptMatchResult result, int lineNumber) {
            long hours = -1;
            long minutes = -1;
            long seconds = -1;
            long millis = -1;

            int parseMark = result.getParseMark();

            for (Pair<SkriptPatternGroup, String> pair : result.getMatchedGroups()) {
                if (!(pair.getX() instanceof RegexGroup)) {
                    continue;
                }

                String regex = pair.getY();

                if (hours == -1 && (parseMark == 1 || parseMark == (1 ^ 2))) {
                    if (regex.isBlank()) {
                        hours = 0;
                    } else {
                        hours = Integer.parseInt(regex);
                    }
                } else if (minutes == -1) {
                    minutes = Integer.parseInt(regex);
                } else if (seconds == -1) {
                    seconds = Integer.parseInt(regex);
                } else if (parseMark == 2 || parseMark == (1 ^ 2)) {
                    if (regex.isBlank()) {
                        millis = 0;
                    } else {
                        millis = Integer.parseInt(regex.substring(1));
                    }

                    break;
                }
            }

            return create(new TimeSpan(hours * 3600000 + minutes * 60000 + seconds * 1000 + millis), lineNumber);
        }

        /**
         * This gets called when an attempt at parsing a time span is made
         *
         * @param text the text to parse
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiTimeSpanLiteral parse(@NotNull String text, int lineNumber) {
            String[] parts = text.split(" *and *| *, *");

            long millis = 0;
            TimeType lastTimeType = TimeType.REAL;

            for (String part : parts) {
                String[] subParts = part.split(" ");
                int subPartsIndex = 0;

                double amount = 1;

                String subPart = subParts[subPartsIndex];
                if (subPart.equalsIgnoreCase("a") || subPart.equalsIgnoreCase("an")) {
                    subPartsIndex++;
                } else if (subPart.matches("\\d+(?:\\.\\d+)?")) {
                    amount = Double.parseDouble(subPart);
                    subPartsIndex++;
                }

                subPart = subParts[subPartsIndex];
                if (subPart.equalsIgnoreCase("real") || subPart.equalsIgnoreCase("irl") ||
                    subPart.equalsIgnoreCase("rl")) {
                    lastTimeType = TimeType.REAL;
                    subPartsIndex++;
                } else if (subPart.equalsIgnoreCase("minecraft") ||
                    subPart.equalsIgnoreCase("mc")) {
                    lastTimeType = TimeType.MINECRAFT;
                    subPartsIndex++;
                }

                double duration;
                boolean tick = false;

                subPart = subParts[subPartsIndex];
                if (subPart.equalsIgnoreCase("tick") || subPart.equalsIgnoreCase("ticks")) {
                    duration = amount * 20;
                    tick = true;
                } else if (subPart.equalsIgnoreCase("second") ||
                    subPart.equalsIgnoreCase("seconds")) {
                    duration = amount * 1000;
                } else if (subPart.equalsIgnoreCase("minute") ||
                    subPart.equalsIgnoreCase("minutes")) {
                    duration = amount * 60000;
                } else if (subPart.equalsIgnoreCase("hour") ||
                    subPart.equalsIgnoreCase("hours")) {
                    duration = amount * 3600000;
                } else if (subPart.equalsIgnoreCase("day") ||
                    subPart.equalsIgnoreCase("days")) {
                    duration = amount * 86400000;
                } else {
                    return null;
                }

                if (lastTimeType == TimeType.MINECRAFT && !tick) {
                    duration /= 72;
                }

                millis += Math.round(duration);
            }

            return create(new TimeSpan(millis), lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param timeSpan the time span
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiTimeSpanLiteral create(@NotNull TimeSpan timeSpan, int lineNumber) {
            return new PsiTimeSpanLiteral(timeSpan, lineNumber);
        }

        /**
         * The types of time
         *
         * @since 0.1.0
         */
        private enum TimeType {

            /**
             * Minecraft time
             *
             * @since 0.1.0
             */
            MINECRAFT,

            /**
             * Real time
             *
             * @since 0.1.0
             */
            REAL
        }
    }
}

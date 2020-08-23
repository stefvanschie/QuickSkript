package com.github.stefvanschie.quickskript.core.util.literal;

import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.pattern.group.RegexGroup;
import com.github.stefvanschie.quickskript.core.pattern.group.SkriptPatternGroup;
import com.github.stefvanschie.quickskript.core.psi.exception.ParseException;
import com.github.stefvanschie.quickskript.core.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Represents time in a Minecraft world
 *
 * @since 0.1.0
 */
public class Time {

    /**
     * The amount of ticks in this Minecraft world. This is always between [0,24000). 0 ticks represents 06:00 and not
     * midnight, like other time systems.
     */
    private final int ticks;

    private static final SkriptPattern PATTERN_24_HOUR = SkriptPattern.parse("<\\d{1,2}>:<\\d{1,2}>");

    private static final SkriptPattern PATTERN_12_HOUR = SkriptPattern.parse("<\\d{1,2}>[:<\\d{1,2}>][ ](0\u00A6am|1\u00A6pm)");

    /**
     * Creates a new time with the given amount of ticks. If this is greater or equal to 24000, this will be changed to
     * fall within the range [0, 24000). If this is below zero, an {@link IllegalArgumentException} will be thrown.
     *
     * @param ticks the amount of ticks
     * @since 0.1.0
     * @throws IllegalArgumentException when the amount of ticks is below zero
     */
    public Time(int ticks) {
        if (ticks < 0) {
            throw new IllegalArgumentException("Amount of ticks below zero");
        }

        this.ticks = ticks % 24000;
    }

    /**
     * Parses the time from the given text. Returns null when this piece of text isn't a time. Throws a
     * {@link ParseException} when the amount of hours or minutes exceeds the maximum amount.
     *
     * @param text the text to parse
     * @return the time
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public static Time parse(@NotNull String text, int lineNumber) {
        List<SkriptMatchResult> matches = PATTERN_24_HOUR.match(text);
        matches.removeIf(SkriptMatchResult::hasUnmatchedParts);

        if (!matches.isEmpty()) {
            SkriptMatchResult result = matches.get(0);

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

            return new Time(ticks);
        }

        matches = PATTERN_12_HOUR.match(text);
        matches.removeIf(SkriptMatchResult::hasUnmatchedParts);

        if (matches.isEmpty()) {
            return null;
        }

        SkriptMatchResult result = matches.get(0);

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

        return new Time(ticks);
    }
}

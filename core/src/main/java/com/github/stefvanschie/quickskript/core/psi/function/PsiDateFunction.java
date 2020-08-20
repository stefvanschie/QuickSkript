package com.github.stefvanschie.quickskript.core.psi.function;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A function for creating dates from a given year, month and day and optionally also hour, minute, second, millisecond.
 * Note that while functions containing a zone and dst offset are still accepted, these fields have no effect.
 *
 * @since 0.1.0
 */
public class PsiDateFunction extends PsiElement<LocalDateTime> {

    /**
     * The year, month and day parameters
     */
    private PsiElement<?> year, month, day;

    /**
     * The hour, minute, second, millisecond parameters
     */
    @Nullable
    private PsiElement<?> hour, minute, second, millisecond;

    /**
     * Creates a new date function
     *
     * @param year the year
     * @param month the month
     * @param day the day
     * @param hour the hour
     * @param minute the minute
     * @param second the second
     * @param millisecond the millisecond
     * @param lineNumber the line number
     * @since 0.1.0
     */
    private PsiDateFunction(@NotNull PsiElement<?> year, @NotNull PsiElement<?> month, @NotNull PsiElement<?> day,
                            @Nullable PsiElement<?> hour, @Nullable PsiElement<?> minute,
                            @Nullable PsiElement<?> second, @Nullable PsiElement<?> millisecond, int lineNumber) {
        super(lineNumber);

        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.millisecond = millisecond;

        if (this.year.isPreComputed() && this.month.isPreComputed() && this.day.isPreComputed() &&
            (this.hour == null || this.hour.isPreComputed()) && (this.minute == null || this.minute.isPreComputed()) &&
            (this.second == null || this.second.isPreComputed()) &&
            (this.millisecond == null || this.millisecond.isPreComputed())) {
            preComputed = executeImpl(null);
            this.year = this.month = this.day = this.hour = this.minute = this.second = this.millisecond = null;
        }
    }

    @NotNull
    @Override
    protected LocalDateTime executeImpl(@Nullable Context context) {
        return LocalDateTime.of(
                year.execute(context, Number.class).intValue(),
                month.execute(context, Number.class).intValue(),
                day.execute(context, Number.class).intValue(),
                hour == null ? 0 : hour.execute(context, Number.class).intValue(),
                minute == null ? 0 : minute.execute(context, Number.class).intValue(),
                second == null ? 0 : second.execute(context, Number.class).intValue(),
                millisecond == null ? 0 : millisecond.execute(context, Number.class).intValue() * 1000000
        );
    }

    /**
     * A factory for creating date functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching date expressions
         */
        @NotNull
        private final Pattern pattern = Pattern.compile("date\\((?<parameters>(?:[\\s\\S]+, *)+[\\s\\S]+)\\)");

        /**
         * This gets called upon parsing
         *
         * @param skriptLoader the skript loader
         * @param text the text to parse
         * @param lineNumber the line number
         * @return the function, or null to indicate failure
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiDateFunction tryParse(@NotNull SkriptLoader skriptLoader, @NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                return null;
            }

            String[] values = matcher.group("parameters").replace(" ", "").split(",");

            if (values.length < 3 || values.length > 9) {
                return null;
            }

            List<PsiElement<?>> elements = new ArrayList<>(Math.min(values.length, 7));

            for (int i = 0; i < values.length; i++) {
                elements.add(i, skriptLoader.forceParseElement(values[i], lineNumber));
            }

            return create(
                elements.get(0),
                elements.get(1),
                elements.get(2),
                elements.size() > 3 ? elements.get(3) : null,
                elements.size() > 4 ? elements.get(4) : null,
                elements.size() > 5 ? elements.get(5) : null,
                elements.size() > 6 ? elements.get(6) : null,
                lineNumber
            );
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the
         * {@link #tryParse(SkriptLoader, String, int)} method.
         *
         * @param year the year of the date
         * @param month the month of the date
         * @param day the day of the date
         * @param hour the hour of the date
         * @param minute the minute of the date
         * @param second the second of the date
         * @param millisecond the millisecond of the date
         * @param lineNumber the line number
         * @return the function
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        protected PsiDateFunction create(@NotNull PsiElement<?> year, @NotNull PsiElement<?> month,
                                         @NotNull PsiElement<?> day, @Nullable PsiElement<?> hour,
                                         @Nullable PsiElement<?> minute, @Nullable PsiElement<?> second,
                                         @Nullable PsiElement<?> millisecond, int lineNumber) {
            return new PsiDateFunction(year, month, day, hour, minute, second, millisecond, lineNumber);
        }
    }
}

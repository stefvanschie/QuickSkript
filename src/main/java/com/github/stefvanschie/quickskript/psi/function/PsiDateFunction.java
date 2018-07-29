package com.github.stefvanschie.quickskript.psi.function;

import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.psi.PsiFactory;
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
public class PsiDateFunction implements PsiElement<LocalDateTime> {

    /**
     * The year, month and day parameters
     */
    @NotNull
    private PsiElement<Number> year, month, day;

    /**
     * The hour, minute, second, millisecond parameters
     */
    @Nullable
    private PsiElement<Number> hour, minute, second, millisecond;

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
     */
    private PsiDateFunction(@NotNull PsiElement<Number> year, @NotNull PsiElement<Number> month,
                            @NotNull PsiElement<Number> day, @Nullable PsiElement<Number> hour,
                            @Nullable PsiElement<Number> minute, @Nullable PsiElement<Number> second,
                            @Nullable PsiElement<Number> millisecond) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.millisecond = millisecond;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime execute() {
        return LocalDateTime.of(
            year.execute().intValue(),
            month.execute().intValue(),
            day.execute().intValue(),
            hour == null ? 0 : hour.execute().intValue(),
            minute == null ? 0 : minute.execute().intValue(),
            second == null ? 0 : second.execute().intValue(),
            millisecond == null ? 0 : millisecond.execute().intValue() * 1000000
        );
    }

    /**
     * A factory for creating date functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiFactory<PsiDateFunction> {

        /**
         * The pattern for matching date expressions
         */
        private static final Pattern PATTERN = Pattern.compile("date\\(((?:[\\s\\S]+,[ ]*)+[\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiDateFunction parse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String[] values = matcher.group(1).replace(" ", "").split(",");

            if (values.length < 3 || values.length > 9)
                return null;

            List<PsiElement<Number>> elements = new ArrayList<>(Math.min(values.length, 7));

            for (int i = 0; i < values.length; i++)
                elements.add(i, (PsiElement<Number>) PsiElementFactory.parseText(values[i], Number.class));

            return new PsiDateFunction(
                elements.get(0),
                elements.get(1),
                elements.get(2),
                elements.size() > 3 ? elements.get(3) : null,
                elements.size() > 4 ? elements.get(4) : null,
                elements.size() > 5 ? elements.get(5) : null,
                elements.size() > 6 ? elements.get(6) : null
            );
        }
    }

    static {
        PsiElementFactory.getClassTypes().put(PsiDateFunction.class, LocalDateTime.class);
    }
}

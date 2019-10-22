package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Formats {@link LocalDateTime} according to a specified pattern
 *
 * @since 0.1.0
 */
public class PsiFormatDateTimeExpression extends PsiElement<String> {

    /**
     * The {@link LocalDateTime} to format
     */
    private PsiElement<?> dateTime;

    /**
     * The format to format the {@link #dateTime} with, or null if the default should be used
     */
    private PsiElement<?> format;

    /**
     * Creates a new element with the given line number
     *
     * @param format the format to format the {@link #dateTime} with
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiFormatDateTimeExpression(@NotNull PsiElement<?> dateTime, @Nullable PsiElement<?> format, int lineNumber) {
        super(lineNumber);

        this.dateTime = dateTime;
        this.format = format;

        if (this.dateTime.isPreComputed() && (this.format == null || this.format.isPreComputed())) {
            preComputed = executeImpl(null);

            this.dateTime = null;
            this.format = null;
        }
    }

    @Nullable
    @Override
    protected String executeImpl(@Nullable Context context) {
        String pattern = this.format == null ? "yyyy-MM-dd HH:mm:ss z" : format.execute(context, String.class);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());

        return dateTime.execute(context, LocalDateTime.class).format(dateTimeFormatter);
    }

    /**
     * A factory for creating {@link PsiFormatDateTimeExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiFormatDateTimeExpression}s
         */
        @NotNull
        private final SkriptPattern pattern =
            SkriptPattern.parse("%dates% formatted [human-readable] [(with|as) %text%]");

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param dateTime the date time to format
         * @param format the format to format with
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiFormatDateTimeExpression parse(@NotNull PsiElement<?> dateTime, @Nullable PsiElement<?> format,
            int lineNumber) {
            return create(dateTime, format, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param dateTime the date time to format
         * @param format the format to format with
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiFormatDateTimeExpression create(@NotNull PsiElement<?> dateTime, @Nullable PsiElement<?> format,
            int lineNumber) {
            return new PsiFormatDateTimeExpression(dateTime, format, lineNumber);
        }
    }
}

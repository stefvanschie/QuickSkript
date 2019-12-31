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

/**
 * Gets the unix timestamp in seconds of a given date.
 *
 * @since 0.1.0
 */
public class PsiUnixTimestampExpression extends PsiElement<Long> {

    /**
     * The date to get the timestamp of
     */
    private PsiElement<?> dateTime;

    /**
     * Creates a new element with the given line number
     *
     * @param dateTime the date to get the timestamp of
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiUnixTimestampExpression(@NotNull PsiElement<?> dateTime, int lineNumber) {
        super(lineNumber);

        this.dateTime = dateTime;

        if (dateTime.isPreComputed()) {
            preComputed = executeImpl(null);

            this.dateTime = null;
        }
    }

    @Nullable
    @Override
    protected Long executeImpl(@Nullable Context context) {
        LocalDateTime dateTime = this.dateTime.execute(context, LocalDateTime.class);

        return dateTime.toEpochSecond(ZoneId.systemDefault().getRules().getOffset(dateTime));
    }

    /**
     * A factory for creating {@link PsiUnixTimestampExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiUnixTimestampExpression}s
         */
        @NotNull
        private SkriptPattern[] patterns = SkriptPattern.parse(
            "[the] unix timestamp of %dates%",
            "%dates%'[s] unix timestamp"
        );

        /**
         * Parses the {@link #patterns} and invokes this method with its types if the match succeeds
         *
         * @param dateTime the date to get the timestamp of
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patterns")
        public PsiUnixTimestampExpression parse(@NotNull PsiElement<?> dateTime, int lineNumber) {
            return create(dateTime, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param dateTime the date to get the timestamp of
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiUnixTimestampExpression create(@NotNull PsiElement<?> dateTime, int lineNumber) {
            return new PsiUnixTimestampExpression(dateTime, lineNumber);
        }
    }
}

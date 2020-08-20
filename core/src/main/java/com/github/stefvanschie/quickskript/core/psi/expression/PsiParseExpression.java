package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.pattern.group.RegexGroup;
import com.github.stefvanschie.quickskript.core.psi.PsiConverter;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Parses a given value to another type or skript pattern.
 *
 * //TODO: add support for skript patterns
 *
 * @since 0.1.0
 */
public class PsiParseExpression extends PsiElement<Object> {

    /**
     * The value we want to change the type of
     */
    @NotNull
    private final PsiElement<?> value;

    /**
     * The factory that will parse our value
     */
    @NotNull
    private final PsiConverter<?> converter;

    /**
     * Constructs a new parse expression
     *
     * @param value the value to parse
     * @param converter the converter which will convert the value
     * @param lineNumber the line number
     * @since 0.1.0
     */
    private PsiParseExpression(@NotNull PsiElement<?> value, @NotNull PsiConverter<?> converter, int lineNumber) {
        super(lineNumber);

        this.value = value;
        this.converter = converter;

        if (this.value.isPreComputed()) {
            preComputed = executeImpl(null);
        }
    }

    @Nullable
    @Override
    protected Object executeImpl(@Nullable Context context) {
        Object toParse = value.execute(context);

        if (toParse == null) {
            return null; //TODO didn't think this through, no idea what should happen, please fix
        }

        PsiElement<?> parsed = converter.convert(toParse.toString(), lineNumber);

        //TODO: if this stuff can't be parsed, the parse error needs to be set
        if (parsed == null) {
            return null;
        }

        return parsed.execute(context);
    }

    /**
     * A factory for creating parse expressions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern to match parse expressions with
         */
        @NotNull
        private final SkriptPattern pattern = SkriptPattern.parse("%text% parsed as <.+>");

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param skriptLoader the skript loader
         * @param result the match result
         * @param value the value to parse
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiParseExpression parse(@NotNull SkriptLoader skriptLoader, @NotNull SkriptMatchResult result,
            @NotNull PsiElement<?> value, int lineNumber) {
            String converterText = result.getMatchedGroups().stream()
                .filter(entry -> entry.getX() instanceof RegexGroup)
                .map(Pair::getY)
                .findAny()
                .orElseThrow();
            PsiConverter<?> converter = skriptLoader.forceGetConverter(converterText, lineNumber);

            return create(value, converter, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param value the value to be converted
         * @param converter the converter to convert the value
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        protected PsiParseExpression create(@NotNull PsiElement<?> value, @NotNull PsiConverter<?> converter,
                                            int lineNumber) {
            return new PsiParseExpression(value, converter, lineNumber);
        }
    }
}

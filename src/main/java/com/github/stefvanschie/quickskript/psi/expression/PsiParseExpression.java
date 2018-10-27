package com.github.stefvanschie.quickskript.psi.expression;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiConverter;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.skript.SkriptLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses a given value to another type or skript pattern. This element is never pre computed.
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
     * @since 0.1.0
     */
    private PsiParseExpression(@NotNull PsiElement<?> value, @NotNull PsiConverter<?> converter, int lineNumber) {
        super(lineNumber);

        this.value = value;
        this.converter = converter;

        if (this.value.isPreComputed())
            preComputed = executeImpl(null);
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    protected Object executeImpl(@Nullable Context context) {
        Object toParse = value.execute(context);
        if (toParse == null)
            return null; //TODO didn't think this through, no idea what should happen, please fix

        PsiElement<?> parsed = converter.convert(toParse.toString(), lineNumber);

        //TODO: if this stuff can't be parsed, the parse error needs to be set
        if (parsed == null)
            return null;

        return parsed.execute(context);
    }

    /**
     * A factory for creating parse expressions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiParseExpression> {

        /**
         * The pattern to match parse expressions with
         */
        private final Pattern pattern = Pattern.compile("([\\s\\S]+) parsed as ([\\s\\S]+)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiParseExpression tryParse(@NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches())
                return null;

            String valueString = matcher.group(1);

            PsiElement<?> value = SkriptLoader.get().forceParseElement(valueString, lineNumber);

            String factoryString = matcher.group(2);

            PsiConverter<?> converter = SkriptLoader.get().forceGetConverter(factoryString, lineNumber);

            return new PsiParseExpression(value, converter, lineNumber);
        }
    }
}

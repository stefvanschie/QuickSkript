package com.github.stefvanschie.quickskript.psi.function;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.psi.exception.ParseException;
import com.github.stefvanschie.quickskript.skript.SkriptLoader;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Creates a vector
 *
 * @since 0.1.0
 */
public class PsiVectorFunction extends PsiElement<Vector> {

    /**
     * Three coordinates for the vector
     */
    private PsiElement<?> x, y, z;

    /**
     * Creates a new vector function
     *
     * @param x the x value
     * @param y the y value
     * @param z the z value
     */
    private PsiVectorFunction(PsiElement<?> x, PsiElement<?> y, PsiElement<?> z) {
        this.x = x;
        this.y = y;
        this.z = z;

        if (this.x.isPreComputed() && this.y.isPreComputed() && this.z.isPreComputed()) {
            preComputed = executeImpl(null);
            this.x = this.y = this.z = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected Vector executeImpl(@Nullable Context context) {
        Object xResult = x.execute(context);

        if (!(xResult instanceof Number))
            throw new ExecutionException("Result of expression should be a number, but it wasn't");

        Object yResult = y.execute(context);

        if (!(yResult instanceof Number))
            throw new ExecutionException("Result of expression should be a number, but it wasn't");

        Object zResult = z.execute(context);

        if (!(zResult instanceof Number))
            throw new ExecutionException("Result of expression should be a number, but it wasn't");

        return new Vector(((Number) xResult).doubleValue(), ((Number) yResult).doubleValue(),
            ((Number) zResult).doubleValue());
    }

    /**
     * A factory for creating vector functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiVectorFunction> {

        /**
         * The pattern for matching vector expressions
         */
        private final Pattern PATTERN = Pattern.compile("vector\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiVectorFunction tryParse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String[] values = matcher.group(1).replace(" ", "").split(",");

            if (values.length != 3)
                return null;

            PsiElement<?> x = SkriptLoader.get().tryParseElement(values[0]);

            if (x == null)
                throw new ParseException("Function was unable to find an expression named " + values[0]);

            PsiElement<?> y = SkriptLoader.get().tryParseElement(values[1]);

            if (y == null)
                throw new ParseException("Function was unable to find an expression named " + values[1]);

            PsiElement<?> z = SkriptLoader.get().tryParseElement(values[2]);

            if (z == null)
                throw new ParseException("Function was unable to find an expression named " + values[2]);

            return new PsiVectorFunction(x, y, z);
        }
    }
}

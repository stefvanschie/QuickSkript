package com.github.stefvanschie.quickskript.psi.function;

import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.psi.PsiFactory;
import com.github.stefvanschie.quickskript.psi.exception.ParseException;
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
public class PsiVectorFunction implements PsiElement<Vector> {

    /**
     * Three coordinates for the vector
     */
    private PsiElement<Number> x, y, z;

    /**
     * Creates a new vector function
     *
     * @param x the x value
     * @param y the y value
     * @param z the z value
     */
    private PsiVectorFunction(PsiElement<Number> x, PsiElement<Number> y, PsiElement<Number> z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector execute() {
        return new Vector(x.execute().doubleValue(), y.execute().doubleValue(), z.execute().doubleValue());
    }

    /**
     * A factory for creating vector functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiFactory<PsiVectorFunction> {

        /**
         * The pattern for matching vector expressions
         */
        private static final Pattern PATTERN = Pattern.compile("vector\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiVectorFunction parse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String[] values = matcher.group(1).replace(" ", "").split(",");

            if (values.length != 3)
                return null;

            PsiElement<Number> x = (PsiElement<Number>) PsiElementFactory.parseText(values[0], Number.class);

            if (x == null)
                throw new ParseException("Function was unable to find an expression named " + values[0]);

            PsiElement<Number> y = (PsiElement<Number>) PsiElementFactory.parseText(values[1], Number.class);

            if (y == null)
                throw new ParseException("Function was unable to find an expression named " + values[1]);

            PsiElement<Number> z = (PsiElement<Number>) PsiElementFactory.parseText(values[2], Number.class);

            if (z == null)
                throw new ParseException("Function was unable to find an expression named " + values[2]);

            return new PsiVectorFunction(x, y, z);
        }
    }
}

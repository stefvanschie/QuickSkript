package com.github.stefvanschie.quickskript.psi.function;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
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
        Number xResult = x.execute(context, Number.class);
        Number yResult = y.execute(context, Number.class);
        Number zResult = z.execute(context, Number.class);

        return new Vector(xResult.doubleValue(), yResult.doubleValue(), zResult.doubleValue());
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
        private final Pattern pattern = Pattern.compile("vector\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiVectorFunction tryParse(@NotNull String text) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches())
                return null;

            String[] values = matcher.group(1).replace(" ", "").split(",");

            if (values.length != 3)
                return null;

            PsiElement<?> x = SkriptLoader.get().forceParseElement(values[0]);
            PsiElement<?> y = SkriptLoader.get().forceParseElement(values[1]);
            PsiElement<?> z = SkriptLoader.get().forceParseElement(values[2]);

            return new PsiVectorFunction(x, y, z);
        }
    }
}

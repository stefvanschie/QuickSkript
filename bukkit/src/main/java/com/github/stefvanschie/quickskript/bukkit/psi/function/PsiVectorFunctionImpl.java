package com.github.stefvanschie.quickskript.bukkit.psi.function;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.function.PsiVectorFunction;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Creates a vector
 *
 * @since 0.1.0
 */
public class PsiVectorFunctionImpl extends PsiVectorFunction {

    /**
     * Creates a new vector function
     *
     * @param x the x value
     * @param y the y value
     * @param z the z value
     */
    private PsiVectorFunctionImpl(@NotNull PsiElement<?> x, @NotNull PsiElement<?> y, @NotNull PsiElement<?> z,
                                  int lineNumber) {
        super(x, y, z, lineNumber);
    }

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
    public static class Factory extends PsiVectorFunction.Factory {

        @NotNull
        @Override
        public PsiVectorFunctionImpl create(@NotNull PsiElement<?> x, @NotNull PsiElement<?> y,
                                            @NotNull PsiElement<?> z, int lineNumber) {
            return new PsiVectorFunctionImpl(x, y, z, lineNumber);
        }
    }
}

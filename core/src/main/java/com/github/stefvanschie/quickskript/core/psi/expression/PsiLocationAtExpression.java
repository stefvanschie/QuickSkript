package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.Type;
import com.github.stefvanschie.quickskript.core.util.literal.Location;
import com.github.stefvanschie.quickskript.core.util.literal.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Creates a location based on the given x, y, z, and world.
 *
 * @since 0.1.0
 */
public class PsiLocationAtExpression extends PsiElement<Location> {

    /**
     * The x, y, and z components of the location.
     */
    @Nullable
    private PsiElement<?> x, y, z;

    /**
     * The world of the location.
     */
    @Nullable
    private PsiElement<?> world;

    /**
     * Creates a new {@link PsiLocationAtExpression} with the given line number.
     *
     * @param x the x component
     * @param y the y component
     * @param z the z component
     * @param world the world of the location
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiLocationAtExpression(
        @NotNull PsiElement<?> x,
        @NotNull PsiElement<?> y,
        @NotNull PsiElement<?> z,
        @NotNull PsiElement<?> world,
        int lineNumber
    ) {
        super(lineNumber);

        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;

        if (x.isPreComputed() && y.isPreComputed() && z.isPreComputed() && world.isPreComputed()) {
            super.preComputed = executeImpl(null, null);

            this.x = null;
            this.y = null;
            this.z = null;
            this.world = null;
        }
    }

    @NotNull
    @Override
    protected Location executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        if (this.x == null || this.y == null || this.z == null || this.world == null) {
            throw new NullPointerException("Unable to execute expression, missing data");
        }

        double x = this.x.execute(environment, context, Double.class);
        double y = this.y.execute(environment, context, Double.class);
        double z = this.z.execute(environment, context, Double.class);
        World world = this.world.execute(environment, context, World.class);

        return new Location(world, x, y, z);
    }

    /**
     * A factory for creating {@link PsiLocationAtExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiLocationAtExpression}s
         */
        @SuppressWarnings("HardcodedFileSeparator")
        @NotNull
        private final SkriptPattern pattern = SkriptPattern.parse(
            "[the] (location|position) [at] [\\(][x[ ][=[ ]]]%number%, [y[ ][=[ ]]]%number%, [and] [z[ ][=[ ]]]%number%[\\)] [[(in|of) [[the] world]] %world%]"
        );

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param x the x component
         * @param y the y component
         * @param z the z component
         * @param world the world of the location
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiLocationAtExpression parse(
            @NotNull PsiElement<?> x,
            @NotNull PsiElement<?> y,
            @NotNull PsiElement<?> z,
            @Nullable PsiElement<?> world,
            int lineNumber
        ) {
            if (world == null) {
                return null;
            }

            return create(x, y, z, world, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         * @param x the x component
         * @param y the y component
         * @param z the z component
         * @param world the world of the location
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiLocationAtExpression create(
            @NotNull PsiElement<?> x,
            @NotNull PsiElement<?> y,
            @NotNull PsiElement<?> z,
            @NotNull PsiElement<?> world,
            int lineNumber
        ) {
            return new PsiLocationAtExpression(x, y, z, world, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.LOCATION;
        }
    }
}

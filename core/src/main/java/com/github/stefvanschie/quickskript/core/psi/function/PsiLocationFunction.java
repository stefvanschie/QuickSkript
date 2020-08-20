package com.github.stefvanschie.quickskript.core.psi.function;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * A function for creating locations
 *
 * @since 0.1.0
 */
public class PsiLocationFunction extends PsiElement<Object> {

    /**
     * The world for this location
     */
    @NotNull
    protected final PsiElement<?> world;

    /**
     * The x, y and z for this location
     */
    @NotNull
    protected final PsiElement<?> x, y, z;

    /**
     * Optional yaw an pitch for this location
     */
    @Nullable
    protected final PsiElement<?> yaw, pitch;

    /**
     * Creates a new location function
     *
     * @param world the world
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     * @param yaw the yaw
     * @param pitch the pitch
     * @param lineNumber the line number
     * @since 0.1.0
     */
    protected PsiLocationFunction(@NotNull PsiElement<?> world, @NotNull PsiElement<?> x, @NotNull PsiElement<?> y,
                                @NotNull PsiElement<?> z, @Nullable PsiElement<?> yaw, @Nullable PsiElement<?> pitch,
                                int lineNumber) {
        super(lineNumber);

        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    /**
     * A factory for creating location functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * This gets called upon parsing
         *
         * @param skriptLoader the skript loader to parse with
         * @param text the text to parse
         * @param lineNumber the line number
         * @return the function, or null to indicate failure
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiLocationFunction tryParse(@NotNull SkriptLoader skriptLoader, @NotNull String text, int lineNumber) {
            if (!text.startsWith("location(") || text.charAt(text.length() - 1) != ')') {
                return null;
            }

            String[] values = text.substring(9, text.length() - 1).replace(" ", "").split(",");

            if (values.length < 4 || values.length > 6) {
                return null;
            }

            PsiElement<?> world = skriptLoader.forceParseElement(values[0], lineNumber);

            List<PsiElement<?>> elements = new ArrayList<>(Math.min(values.length, 5));

            for (int i = 1; i < values.length; i++) {
                elements.add(i - 1, skriptLoader.forceParseElement(values[i], lineNumber));
            }

            return create(
                world,
                elements.get(0),
                elements.get(1),
                elements.get(2),
                elements.size() > 3 ? elements.get(3) : null,
                elements.size() > 4 ? elements.get(4) : null,
                lineNumber
            );
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the
         * {@link #tryParse(SkriptLoader, String, int)} method.
         *
         * @param world the world of the location
         * @param x the x coordinate of the location
         * @param y the y coordinate of the location
         * @param z the z coordinate of the location
         * @param yaw the yaw of the location
         * @param pitch the pitch of the location
         * @param lineNumber the line number
         * @return the function
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        protected PsiLocationFunction create(@NotNull PsiElement<?> world, @NotNull PsiElement<?> x,
                                             @NotNull PsiElement<?> y, @NotNull PsiElement<?> z,
                                             @Nullable PsiElement<?> yaw, @Nullable PsiElement<?> pitch,
                                             int lineNumber) {
            return new PsiLocationFunction(world, x, y, z, yaw, pitch, lineNumber);
        }
    }
}

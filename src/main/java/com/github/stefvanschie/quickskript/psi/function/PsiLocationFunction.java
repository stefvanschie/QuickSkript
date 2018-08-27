package com.github.stefvanschie.quickskript.psi.function;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.psi.PsiFactory;
import com.github.stefvanschie.quickskript.psi.exception.ParseException;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A function for creating locations
 *
 * @since 0.1.0
 */
public class PsiLocationFunction extends PsiElement<Location> {

    /**
     * The world for this location
     */
    @NotNull
    private final PsiElement<World> world;

    /**
     * The x, y and z for this location
     */
    @NotNull
    private final PsiElement<Number> x;
    @NotNull
    private final PsiElement<Number> y;
    @NotNull
    private final PsiElement<Number> z;

    /**
     * Optional yaw an pitch for this location
     */
    @Nullable
    private final PsiElement<Number> yaw;
    @Nullable
    private final PsiElement<Number> pitch;

    /**
     * Creates a new location function
     *
     * @param world the world
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     * @param yaw the yaw
     * @param pitch the pitch
     * @since 0.1.0
     */
    private PsiLocationFunction(@NotNull PsiElement<World> world, @NotNull PsiElement<Number> x,
                                @NotNull PsiElement<Number> y, @NotNull PsiElement<Number> z,
                                @Nullable PsiElement<Number> yaw, @Nullable PsiElement<Number> pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public Location executeImpl(@Nullable Context context) {
        return new Location(
            world.execute(context),
            x.execute(context).doubleValue(),
            y.execute(context).doubleValue(),
            z.execute(context).doubleValue(),
            yaw == null ? 0 : yaw.execute(context).floatValue(),
            pitch == null ? 0 : pitch.execute(context).floatValue()
        );
    }

    /**
     * A factory for creating location functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiFactory<PsiLocationFunction> {

        /**
         * The pattern for matching location expressions
         */
        private static final Pattern PATTERN = Pattern.compile("location\\(((?:[\\s\\S]+,[ ]*)+[\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiLocationFunction parse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String[] values = matcher.group(1).replace(" ", "").split(",");

            if (values.length < 4 || values.length > 6)
                return null;

            PsiElement<World> world = (PsiElement<World>) PsiElementFactory.parseText(values[0], World.class);

            if (world == null)
                throw new ParseException("Function was unable to find an expression named " + values[0]);

            List<PsiElement<Number>> elements = new ArrayList<>(Math.min(values.length, 5));

            for (int i = 1; i < values.length; i++)
                elements.add(i - 1, (PsiElement<Number>) PsiElementFactory.parseText(values[i], Number.class));

            return new PsiLocationFunction(
                world,
                elements.get(0),
                elements.get(1),
                elements.get(2),
                elements.size() > 3 ? elements.get(3) : null,
                elements.size() > 4 ? elements.get(4) : null
            );
        }
    }
}

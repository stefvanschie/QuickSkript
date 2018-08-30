package com.github.stefvanschie.quickskript.psi.function;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.psi.PsiElementUtil;
import com.github.stefvanschie.quickskript.psi.exception.ExecutionException;
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
    private final PsiElement<?> world;

    /**
     * The x, y and z for this location
     */
    @NotNull
    private final PsiElement<?> x, y, z;

    /**
     * Optional yaw an pitch for this location
     */
    @Nullable
    private final PsiElement<?> yaw, pitch;

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
    private PsiLocationFunction(@NotNull PsiElement<?> world, @NotNull PsiElement<?> x, @NotNull PsiElement<?> y,
                                @NotNull PsiElement<?> z, @Nullable PsiElement<?> yaw, @Nullable PsiElement<?> pitch) {
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
        Object worldResult = world.execute(context);

        if (!(worldResult instanceof World))
            throw new ExecutionException("Result of expression should be a world, but it wasn't");

        Object xResult = x.execute(context);

        if (!(xResult instanceof Number))
            throw new ExecutionException("Result of expression should be a number, but it wasn't");

        Object yResult = y.execute(context);

        if (!(yResult instanceof Number))
            throw new ExecutionException("Result of expression should be a number, but it wasn't");

        Object zResult = z.execute(context);

        if (!(zResult instanceof Number))
            throw new ExecutionException("Result of expression should be a number, but it wasn't");

        Object yawResult = yaw == null ? null : yaw.execute(context);

        if (yawResult != null && !(yawResult instanceof Number))
            throw new ExecutionException("Result of expression should be a number, but it wasn't");

        Object pitchResult = pitch == null ? null : pitch.execute(context);

        if (pitchResult != null && !(pitchResult instanceof Number))
            throw new ExecutionException("Result of expression should be a number, but it wasn't");

        return new Location(
            (World) worldResult,
            ((Number) xResult).doubleValue(),
            ((Number) yResult).doubleValue(),
            ((Number) zResult).doubleValue(),
            yawResult == null ? 0 : ((Number) yawResult).floatValue(),
            pitchResult == null ? 0 : ((Number) pitchResult).floatValue()
        );
    }

    /**
     * A factory for creating location functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiLocationFunction> {

        /**
         * The pattern for matching location expressions
         */
        private static final Pattern PATTERN = Pattern.compile("location\\(((?:[\\s\\S]+,[ ]*)+[\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiLocationFunction tryParse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String[] values = matcher.group(1).replace(" ", "").split(",");

            if (values.length < 4 || values.length > 6)
                return null;

            PsiElement<?> world = PsiElementUtil.tryParseText(values[0]);

            if (world == null)
                throw new ParseException("Function was unable to find an expression named " + values[0]);

            List<PsiElement<?>> elements = new ArrayList<>(Math.min(values.length, 5));

            for (int i = 1; i < values.length; i++)
                elements.add(i - 1, PsiElementUtil.tryParseText(values[i]));

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

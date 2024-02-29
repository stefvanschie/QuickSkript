package com.github.stefvanschie.quickskript.core.util.literal.direction;

import com.github.stefvanschie.quickskript.core.util.literal.Location;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An absolute direction. An absolute direction signifies a movement over Minecraft's coordinate grid. Instances of this
 * class are immutable.
 *
 * @since 0.1.0
 */
public class AbsoluteDirection implements Direction {

    /**
     * The movement over the x, y, and z axes.
     */
    private final double x, y, z;

    /**
     * Creates a new absolute direction with the specified components.
     *
     * @param x the x component
     * @param y the y component
     * @param z the z component
     * @since 0.1.0
     */
    public AbsoluteDirection(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public Location getRelative(@NotNull Location location) {
        double newX = location.getX() + this.x;
        double newY = location.getY() + this.y;
        double newZ = location.getZ() + this.z;

        return new Location(location.getWorld(), newX, newY, newZ, location.getYaw(), location.getPitch());
    }

    /**
     * Adds two absolute directions together. The resulting direction is the sum of each component of both directions.
     *
     * @param direction the direction to add to this direction.
     * @return a new direction, representing the sum of the added directions
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public AbsoluteDirection add(@NotNull AbsoluteDirection direction) {
        return new AbsoluteDirection(this.x + direction.x, this.y + direction.y, this.z + direction.z);
    }

    @Override
    @Contract(pure = true)
    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        AbsoluteDirection that = (AbsoluteDirection) object;

        if (Double.compare(that.x, x) != 0) {
            return false;
        }

        if (Double.compare(that.y, y) != 0) {
            return false;
        }

        if (Double.compare(that.z, z) != 0) {
            return false;
        }

        return true;
    }

    @Override
    @Contract(pure = true)
    public int hashCode() {
        int result;
        long temp = Double.doubleToLongBits(x);

        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);

        return 31 * result + (int) (temp ^ (temp >>> 32));
    }
}

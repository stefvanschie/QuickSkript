package com.github.stefvanschie.quickskript.core.util.literal.direction;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

/**
 * A relative direction similar to {@link RelativeDirection}, but this one will always be horizontal i.e., the pitch is
 * always zero. This is not the same as a {@link RelativeDirection} with the pitch set to zero, since there its relation
 * might cause the pitch not to be zero. Here, the direction with respect to another object will always have a pitch of
 * zero, regardless of the pitch of the related object. This follows Minecraft's coordinate system.
 *
 * @since 0.1.0
 */
public class HorizontalRelativeDirection implements Direction {

    /**
     * The yaw of this direction. The yaw is a unit in between -180 and 180.
     */
    private final double yaw;

    /**
     * The length of this direction.
     */
    private final double length;

    /**
     * Creates a new relative direction with the specified components.
     *
     * @param yaw the yaw
     * @param length the length
     * @since 0.1.0
     */
    public HorizontalRelativeDirection(double yaw, double length) {
        this.yaw = yaw;
        this.length = length;
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

        HorizontalRelativeDirection that = (HorizontalRelativeDirection) object;

        if (Double.compare(that.yaw, yaw) != 0) {
            return false;
        }

        if (Double.compare(that.length, length) != 0) {
            return false;
        }

        return true;
    }

    @Override
    @Contract(pure = true)
    public int hashCode() {
        int result;
        long temp = Double.doubleToLongBits(yaw);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(length);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}

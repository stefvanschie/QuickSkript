package com.github.stefvanschie.quickskript.core.util.literal.direction;

import com.github.stefvanschie.quickskript.core.util.literal.Location;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a relative direction. For example, a yaw of 90 would represent a ninety degree, clockwise rotation with
 * respect to the direction of some other object. This follows Minecraft's coordinate system.
 *
 * @since 0.1.0
 */
public class RelativeDirection implements Direction {

    /**
     * The yaw and pitch. The yaw is a unit in between -180 and 180 and the pitch is a unit in between -90 and 90.
     */
    private final double yaw, pitch;

    /**
     * The length of this direction.
     */
    private final double length;

    /**
     * Creates a new relative direction with the specified components.
     *
     * @param yaw the yaw
     * @param pitch the pitch
     * @param length the length
     * @since 0.1.0
     */
    public RelativeDirection(double yaw, double pitch, double length) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.length = length;
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public Location getRelative(@NotNull Location location) {
        double radianYaw = Math.toRadians(location.getYaw()) + Math.PI / 2;
        double radianPitch = -Math.toRadians(location.getPitch());

        double p = Math.cos(radianPitch + this.pitch) * this.length;

        double newX = Math.cos(radianYaw + this.yaw) * p + location.getX();
        double newY = Math.sin(radianPitch + this.pitch) * Math.cos(this.yaw) * length + location.getY();
        double newZ = Math.sin(radianYaw + this.yaw) * p + location.getZ();

        return new Location(location.getWorld(), newX, newY, newZ, location.getYaw(), location.getPitch());
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

        RelativeDirection that = (RelativeDirection) object;

        if (Double.compare(that.yaw, yaw) != 0) {
            return false;
        }

        if (Double.compare(that.pitch, pitch) != 0) {
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
        temp = Double.doubleToLongBits(pitch);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(length);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}

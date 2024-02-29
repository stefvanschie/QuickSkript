package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a location in a world. The location consists of an x, y, and z component, a yaw and a pitch, and a world.
 *
 * @since 0.1.0
 */
public class Location {

    /**
     * The world this location is in.
     */
    @NotNull
    private final World world;

    /**
     * The x, y, and z positions of this location.
     */
    private final double x, y, z;

    /**
     * The rotation of this location.
     */
    private final float yaw, pitch;

    /**
     * Creates a new location with the provided x, y, and z component, a yaw and a pitch, and a world.
     *
     * @param world the world of this location
     * @param x the x component
     * @param y the y component
     * @param z the z component
     * @since 0.1.0
     */
    public Location(@NotNull World world, double x, double y, double z, float yaw, float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    /**
     * Creates a new location with the provided x, y, and z component, and a world. The yaw and pitch of the location
     * will both be zero.
     *
     * @param world the world of this location
     * @param x the x component
     * @param y the y component
     * @param z the z component
     * @since 0.1.0
     */
    public Location(@NotNull World world, double x, double y, double z) {
        this(world, x, y, z, 0, 0);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Location location = (Location) object;

        if (Double.compare(location.x, x) != 0) {
            return false;
        }

        if (Double.compare(location.y, y) != 0) {
            return false;
        }

        if (Double.compare(location.z, z) != 0) {
            return false;
        }

        if (Float.compare(location.yaw, yaw) != 0) {
            return false;
        }

        if (Float.compare(location.pitch, pitch) != 0) {
            return false;
        }

        if (!world.equals(location.world)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long xBits = Double.doubleToLongBits(x);
        result = (int) (xBits ^ (xBits >>> 32));

        long yBits = Double.doubleToLongBits(y);
        result = 31 * result + (int) (yBits ^ (yBits >>> 32));

        long zBits = Double.doubleToLongBits(z);
        result = 31 * result + (int) (zBits ^ (zBits >>> 32));

        result = 31 * result + (yaw != 0.0f ? Float.floatToIntBits(yaw) : 0);
        result = 31 * result + (pitch != 0.0f ? Float.floatToIntBits(pitch) : 0);
        return 31 * result + world.hashCode();
    }

    /**
     * Gets the world of this location.
     *
     * @return the world
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public World getWorld() {
        return this.world;
    }

    /**
     * Gets the x coordinate of this location.
     *
     * @return the x coordinate
     * @since 0.1.0
     */
    @Contract(pure = true)
    public double getX() {
        return this.x;
    }

    /**
     * Gets the y coordinate of this location.
     *
     * @return the y coordinate
     * @since 0.1.0
     */
    @Contract(pure = true)
    public double getY() {
        return this.y;
    }

    /**
     * Gets the z coordinate of this location.
     *
     * @return the z coordinate
     * @since 0.1.0
     */
    @Contract(pure = true)
    public double getZ() {
        return this.z;
    }

    /**
     * Gets the yaw of this location.
     *
     * @return the yaw
     * @since 0.1.0
     */
    @Contract(pure = true)
    public float getYaw() {
        return this.yaw;
    }

    /**
     * Gets the pitch of this location.
     *
     * @return the pitch
     * @since 0.1.0
     */
    @Contract(pure = true)
    public float getPitch() {
        return this.pitch;
    }
}

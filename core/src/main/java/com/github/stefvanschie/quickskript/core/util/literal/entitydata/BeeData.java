package com.github.stefvanschie.quickskript.core.util.literal.entitydata;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * The data for bees.
 *
 * @since 0.1.0
 */
public abstract class BeeData {

    /**
     * Whether the bee has nectar on it. If this is null, this is undefined.
     */
    protected final Boolean hasNectar;

    /**
     * Whether the bee is angry; if false, the bee is happy. If this is null, this is undefined.
     */
    protected final Boolean isAngry;

    /**
     * Creates a new instance of this class with the provided data.
     *
     * @param hasNectar whether the bee has nectar
     * @param isAngry whether the bee is angry
     * @since 0.1.0
     */
    public BeeData(Boolean hasNectar, Boolean isAngry) {
        this.hasNectar = hasNectar;
        this.isAngry = isAngry;
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        BeeData beeData = (BeeData) object;

        return Objects.equals(this.hasNectar, beeData.hasNectar) && Objects.equals(this.isAngry, beeData.isAngry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.hasNectar, this.isAngry);
    }
}

package com.github.stefvanschie.quickskript.spigot.util.entitydata;

import com.github.stefvanschie.quickskript.core.util.literal.entitydata.BeeData;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The data for bees on Paper.
 *
 * @since 0.1.0
 */
public class BeeDataImpl extends BeeData implements EntityDataImpl {

    /**
     * Creates a new instance of this class with the provided data.
     *
     * @param hasNectar whether the bee has nectar
     * @param isAngry whether the bee is angry
     * @since 0.1.0
     */
    public BeeDataImpl(@Nullable Boolean hasNectar, @Nullable Boolean isAngry) {
        super(hasNectar, isAngry);
    }

    @Override
    public boolean matches(@NotNull Entity entity) {
        if (!(entity instanceof Bee bee)) {
            return false;
        }

        boolean nectarCorrect = super.hasNectar == null || super.hasNectar == bee.hasNectar();
        boolean angryCorrect = super.isAngry == null || super.isAngry == bee.getAnger() > 0;

        return nectarCorrect && angryCorrect;
    }
}

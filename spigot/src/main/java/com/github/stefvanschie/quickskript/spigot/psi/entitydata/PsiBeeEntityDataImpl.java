package com.github.stefvanschie.quickskript.spigot.psi.entitydata;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.entitydata.PsiBeeEntityData;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.literal.entitydata.BeeData;
import com.github.stefvanschie.quickskript.spigot.util.entitydata.BeeDataImpl;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Creates bee data.
 *
 * @since 0.1.0
 */
public class PsiBeeEntityDataImpl extends PsiBeeEntityData {

    /**
     * Creates a new psi element which holds a precomputed beeData
     *
     * @param hasNectar whether the bee data has nectar
     * @param isAngry whether the bee data is angry
     * @param lineNumber the line number of this element
     * @since 0.1.0
     */
    private PsiBeeEntityDataImpl(@Nullable Boolean hasNectar, @Nullable Boolean isAngry, int lineNumber) {
        super(hasNectar, isAngry, lineNumber);
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    @Override
    protected BeeData executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return new BeeDataImpl(super.hasNectar, super.isAngry);
    }

    /**
     * A factory for creating instances of {@link PsiBeeEntityDataImpl}.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiBeeEntityData.Factory {

        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        @Override
        protected PsiBeeEntityData create(@Nullable Boolean hasNectar, @Nullable Boolean isAngry, int lineNumber) {
            return new PsiBeeEntityDataImpl(hasNectar, isAngry, lineNumber);
        }
    }
}

package com.github.stefvanschie.quickskript.spigot.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiDoRespawnAnchorsWorkCondition;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.literal.World;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether respawn anchors work in the given worlds.
 *
 * @since 0.1.0
 */
public class PsiDoRespawnAnchorsWorkConditionImpl extends PsiDoRespawnAnchorsWorkCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param worlds the worlds to check for
     * @param positive if false, the result is inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiDoRespawnAnchorsWorkConditionImpl(
        @NotNull PsiElement<? extends World> worlds,
        boolean positive,
        int lineNumber
    ) {
        super(worlds, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return super.positive == super.worlds.executeMulti(environment, context).test(world -> {
            String name = world.getName();
            org.bukkit.World bukkitWorld = Bukkit.getWorld(name);

            if (bukkitWorld == null) {
                throw new ExecutionException(
                    "World with name '" + name + "' does not exist or is not loaded",
                    super.lineNumber
                );
            }

            return bukkitWorld.isRespawnAnchorWorks();
        });
    }

    /**
     * A factory for creating {@link PsiDoRespawnAnchorsWorkConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiDoRespawnAnchorsWorkCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiDoRespawnAnchorsWorkCondition create(
            @NotNull PsiElement<? extends World> worlds,
            boolean positive,
            int lineNumber
        ) {
            return new PsiDoRespawnAnchorsWorkConditionImpl(worlds, positive, lineNumber);
        }
    }
}

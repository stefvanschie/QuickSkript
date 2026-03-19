package com.github.stefvanschie.quickskript.spigot.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiWorldIsLoadedCondition;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.literal.World;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if worlds are loaded.
 *
 * @since 0.1.0
 */
public class PsiWorldIsLoadedConditionImpl extends PsiWorldIsLoadedCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param worlds the worlds to check if they are loaded
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiWorldIsLoadedConditionImpl(@NotNull PsiElement<?> worlds, boolean positive, int lineNumber) {
        super(worlds, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return super.positive == super.worlds.executeMulti(environment, context, World.class)
            .test(world -> Bukkit.getWorld(world.getName()) != null);
    }

    /**
     * A factory for creating instances of {@link PsiWorldIsLoadedConditionImpl}.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiWorldIsLoadedCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        @Override
        protected PsiWorldIsLoadedConditionImpl create(@NotNull PsiElement<?> worlds, boolean positive,
                                                       int lineNumber) {
            return new PsiWorldIsLoadedConditionImpl(worlds, positive, lineNumber);
        }
    }
}

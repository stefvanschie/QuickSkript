package com.github.stefvanschie.quickskript.spigot.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsPluginEnabledCondition;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the given plugins are enabled.
 *
 * @since 0.1.0
 */
public class PsiIsPluginEnabledConditionImpl extends PsiIsPluginEnabledCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param plugins the plugins to check if they are enabled
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsPluginEnabledConditionImpl(@NotNull PsiElement<?> plugins, boolean positive, int lineNumber) {
        super(plugins, positive, lineNumber);
    }

    @Override
    protected @Nullable Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        PluginManager pluginManager = Bukkit.getPluginManager();

        return super.positive == super.plugins.executeMulti(environment, context, String.class)
            .test(pluginManager::isPluginEnabled);
    }

    /**
     * A factory for creating instances of {@link PsiIsPluginEnabledConditionImpl}.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsPluginEnabledCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        @Override
        public PsiIsPluginEnabledCondition create(@NotNull PsiElement<?> plugins, boolean positive, int lineNumber) {
            return new PsiIsPluginEnabledConditionImpl(plugins, positive, lineNumber);
        }
    }
}

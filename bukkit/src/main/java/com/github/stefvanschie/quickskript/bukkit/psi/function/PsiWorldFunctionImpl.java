package com.github.stefvanschie.quickskript.bukkit.psi.function;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.function.PsiWorldFunction;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets a world from a given string. This will never be pre computed, since there is no guarantee that any world
 * management plugin is loaded when this plugin is enabled.
 *
 * @since 0.1.0
 */
public class PsiWorldFunctionImpl extends PsiWorldFunction {

    /**
     * Creates a new world function
     *
     * @param parameter the parameter
     * @since 0.1.0
     */
    private PsiWorldFunctionImpl(@NotNull PsiElement<?> parameter, int lineNumber) {
        super(parameter, lineNumber);
    }

    @Nullable
    @Override
    public World executeImpl(@Nullable Context context) {
        return Bukkit.getWorld(parameter.execute(context, Text.class).toString());
    }

    /**
     * A factory for creating world functions
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiWorldFunction.Factory {

        @NotNull
        @Override
        public PsiWorldFunctionImpl create(@NotNull PsiElement<?> parameter, int lineNumber) {
            return new PsiWorldFunctionImpl(parameter, lineNumber);
        }
    }
}

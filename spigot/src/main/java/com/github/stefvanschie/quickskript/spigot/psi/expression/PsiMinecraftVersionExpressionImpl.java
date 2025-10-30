package com.github.stefvanschie.quickskript.spigot.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiMinecraftVersionExpression;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the Minecraft version. This is always pre-computed.
 *
 * @since 0.1.0
 */
public class PsiMinecraftVersionExpressionImpl extends PsiMinecraftVersionExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiMinecraftVersionExpressionImpl(int lineNumber) {
        super(lineNumber);

        preComputed = executeImpl(null, null);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected String executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        String bukkitVersion = Bukkit.getBukkitVersion();

        return bukkitVersion.substring(0, bukkitVersion.indexOf('-'));
    }

    /**
     * A factory for creating {@link PsiMinecraftVersionExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiMinecraftVersionExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiMinecraftVersionExpression create(int lineNumber) {
            return new PsiMinecraftVersionExpressionImpl(lineNumber);
        }
    }
}

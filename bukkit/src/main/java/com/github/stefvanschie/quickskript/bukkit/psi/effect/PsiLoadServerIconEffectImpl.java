package com.github.stefvanschie.quickskript.bukkit.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiLoadServerIconEffect;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.util.TemporaryCache;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * Loads a server icon for later usage
 *
 * @since 0.1.0
 */
public class PsiLoadServerIconEffectImpl extends PsiLoadServerIconEffect {

    /**
     * Creates a new element with the given line number
     *
     * @param path       the path to the server icon, see {@link #path}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiLoadServerIconEffectImpl(@NotNull PsiElement<?> path, int lineNumber) {
        super(path, lineNumber);
    }

    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        String path = this.path.execute(context, Text.class).toString();

        try {
            TemporaryCache.set("last-server-icon", Bukkit.loadServerIcon(new File(path)));
        } catch (Exception e) {
            throw new ExecutionException(e, lineNumber);
        }

        return null;
    }

    /**
     * A factory for creating {@link PsiLoadServerIconEffectImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiLoadServerIconEffect.Factory {

        @NotNull
        @Override
        public PsiLoadServerIconEffect create(@NotNull PsiElement<?> path, int lineNumber) {
            return new PsiLoadServerIconEffectImpl(path, lineNumber);
        }
    }
}

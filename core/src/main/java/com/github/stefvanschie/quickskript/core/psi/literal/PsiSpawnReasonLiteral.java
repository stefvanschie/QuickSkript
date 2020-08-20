package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.literal.SpawnReason;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a spawn reason
 *
 * @since 0.1.0
 */
public class PsiSpawnReasonLiteral extends PsiPrecomputedHolder<SpawnReason> {

    /**
     * Creates a new element with the given line number
     *
     * @param spawnReason the spawn reason
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiSpawnReasonLiteral(@NotNull SpawnReason spawnReason, int lineNumber) {
        super(spawnReason, lineNumber);
    }

    /**
     * A factory for creating {@link PsiSpawnReasonLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * This is called whenever a spawn reason is attempted to be parsed
         *
         * @param text the text to be parsed
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiSpawnReasonLiteral parse(@NotNull String text, int lineNumber) {
            for (SpawnReason spawnReason : SpawnReason.values()) {
                for (String alias : spawnReason.getAliases()) {
                    if (!alias.equalsIgnoreCase(text)) {
                        continue;
                    }

                    return create(spawnReason, lineNumber);
                }
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiSpawnReasonLiteral create(@NotNull SpawnReason spawnReason, int lineNumber) {
            return new PsiSpawnReasonLiteral(spawnReason, lineNumber);
        }
    }
}

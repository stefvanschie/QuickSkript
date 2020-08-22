package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.util.literal.TeleportCause;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a teleport cause
 *
 * @since 0.1.0
 */
public class PsiTeleportCauseLiteral extends PsiPrecomputedHolder<TeleportCause> {

    /**
     * Creates a new element with the given line number
     *
     * @param teleportCause the teleport cause
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiTeleportCauseLiteral(@NotNull TeleportCause teleportCause, int lineNumber) {
        super(teleportCause, lineNumber);
    }

    /**
     * A factory for creating {@link PsiTeleportCauseLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * This gets called whenever an attempt at parsing a teleport cause is made
         *
         * @param text the text to be parsed
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiTeleportCauseLiteral parse(@NotNull String text, int lineNumber) {
            for (TeleportCause teleportCause : TeleportCause.values()) {
                for (String alias : teleportCause.getAliases()) {
                    if (!alias.equalsIgnoreCase(text)) {
                        continue;
                    }

                    return create(teleportCause, lineNumber);
                }
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param teleportCause the teleport cause
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiTeleportCauseLiteral create(@NotNull TeleportCause teleportCause, int lineNumber) {
            return new PsiTeleportCauseLiteral(teleportCause, lineNumber);
        }
    }
}

package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.util.literal.ResourcePackStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a resource pack status
 *
 * @since 0.1.0
 */
public class PsiResourcePackStatus extends PsiPrecomputedHolder<ResourcePackStatus> {

    /**
     * Creates a new element with the given line number
     *
     * @param status the status this element represents
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiResourcePackStatus(@NotNull ResourcePackStatus status, int lineNumber) {
        super(status, lineNumber);
    }

    /**
     * A factory for creating {@link PsiResourcePackStatus}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * This is called whenever an attempt at parsing a resource pack status is made
         *
         * @param text the text to parse
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiResourcePackStatus parse(@NotNull String text, int lineNumber) {
            for (ResourcePackStatus status : ResourcePackStatus.values()) {
                if (!status.name().replace('_', ' ').equalsIgnoreCase(text)) {
                    continue;
                }

                return create(status, lineNumber);
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
        public PsiResourcePackStatus create(@NotNull ResourcePackStatus status, int lineNumber) {
            return new PsiResourcePackStatus(status, lineNumber);
        }
    }
}

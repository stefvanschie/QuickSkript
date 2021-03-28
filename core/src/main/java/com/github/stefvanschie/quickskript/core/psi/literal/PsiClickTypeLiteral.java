package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.util.Type;
import com.github.stefvanschie.quickskript.core.util.literal.ClickType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

/**
 * Gets a click type
 *
 * @since 0.1.0
 */
public class PsiClickTypeLiteral extends PsiPrecomputedHolder<ClickType> {

    /**
     * Creates a new element with the given line number
     *
     * @param clickType the click type
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiClickTypeLiteral(@NotNull ClickType clickType, int lineNumber) {
        super(clickType, lineNumber);
    }

    /**
     * A factory for creating {@link PsiClickTypeLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * This gets called upon parsing
         *
         * @param text the text to parse
         * @param lineNumber the line number
         * @return the literal, or null to indicate failure
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiClickTypeLiteral parse(@NotNull String text, int lineNumber) {
            ClickType clickType;

            try {
                clickType = ClickType.valueOf(text.replace(' ', '_').toUpperCase(Locale.getDefault()));
            } catch (IllegalArgumentException exception) {
                return null;
            }

            return create(clickType, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param clickType the click type
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiClickTypeLiteral create(@NotNull ClickType clickType, int lineNumber) {
            return new PsiClickTypeLiteral(clickType, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.CLICK_TYPE;
        }
    }
}

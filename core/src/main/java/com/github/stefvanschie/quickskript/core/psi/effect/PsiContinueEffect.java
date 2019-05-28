package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.SimpleInstructionPointerMovement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An effect which continue the latest loop
 *
 * @since 0.1.0
 */
public class PsiContinueEffect extends PsiPrecomputedHolder<SimpleInstructionPointerMovement.Loop> {

    /**
     * Creates a new psi element which holds a precomputed value
     *
     * @param lineNumber the line number of this element
     * @since 0.1.0
     */
    public PsiContinueEffect(int lineNumber) {
        super(SimpleInstructionPointerMovement.Loop.CONTINUE, lineNumber);
    }

    /**
     * A factory for creating {@link PsiContinueEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiContinueEffect> {

        /**
         * The pattern for matching {@link PsiContinueEffect}s
         */
        @NotNull
        private final Pattern pattern = Pattern.compile("continue(?: loop)?");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiContinueEffect tryParse(@NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                return null;
            }

            return new PsiContinueEffect(lineNumber);
        }
    }
}

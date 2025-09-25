package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.psi.util.pointermovement.SimpleInstructionPointerMovement;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    private PsiContinueEffect(int lineNumber) {
        super(SimpleInstructionPointerMovement.Loop.CONTINUE, lineNumber);
    }

    /**
     * A factory for creating {@link PsiContinueEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("continue [loop]")
        public PsiContinueEffect parse(int lineNumber) {
            return new PsiContinueEffect(lineNumber);
        }

        @Nullable
        @Contract(pure = true)
        @Override
        public Type getType() {
            return null;
        }
    }
}

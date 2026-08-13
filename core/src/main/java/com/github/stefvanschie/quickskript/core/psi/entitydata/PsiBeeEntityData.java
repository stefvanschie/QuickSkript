package com.github.stefvanschie.quickskript.core.psi.entitydata;

import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.literal.entitydata.BeeData;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Creates bee data.
 *
 * @since 0.1.0
 */
public class PsiBeeEntityData extends PsiPrecomputedHolder<BeeData> {

    /**
     * Creates a new psi element which holds a precomputed beeData
     *
     * @param beeData the beeData this psi is wrapping
     * @param lineNumber the line number of this element
     * @since 0.1.0
     */
    private PsiBeeEntityData(@NotNull BeeData beeData, int lineNumber) {
        super(beeData, lineNumber);
    }

    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param result the skript match result
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("[1¦happy|2¦angry] [4¦[8¦no ]nectar] bee")
        public PsiBeeEntityData parse(@NotNull SkriptMatchResult result, int lineNumber) {
            int parseMark = result.getParseMark();

            Boolean isAngry = null, hasNectar = null;

            if ((parseMark & 1) != 0) {
                isAngry = false;
            } else if ((parseMark & 2) != 0) {
                isAngry = true;
            }

            if ((parseMark & 8) != 0) {
                hasNectar = false;
            } else if ((parseMark & 4) != 0) {
                hasNectar = true;
            }

            return create(new BeeData(isAngry, hasNectar), lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param beeData the worlds to check if they are loaded
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(value = "_, _ -> new", pure = true)
        protected PsiBeeEntityData create(@NotNull BeeData beeData, int lineNumber) {
            return new PsiBeeEntityData(beeData, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public String getType() {
            return "entity data";
        }
    }
}

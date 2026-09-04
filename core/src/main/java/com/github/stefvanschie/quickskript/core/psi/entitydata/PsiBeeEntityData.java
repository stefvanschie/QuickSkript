package com.github.stefvanschie.quickskript.core.psi.entitydata;

import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.literal.entitydata.BeeData;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Creates bee data.
 *
 * @since 0.1.0
 */
public class PsiBeeEntityData extends PsiElement<BeeData> {

    /**
     * Whether the bee data has nectar.
     */
    @Nullable
    protected final Boolean hasNectar;

    /**
     * Whether the bee data is angry.
     */
    @Nullable
    protected final Boolean isAngry;

    /**
     * Creates a new psi element which holds a precomputed beeData
     *
     * @param hasNectar whether the bee data has nectar
     * @param isAngry whether the bee data is angry
     * @param lineNumber the line number of this element
     * @since 0.1.0
     */
    protected PsiBeeEntityData(@Nullable Boolean hasNectar, @Nullable Boolean isAngry, int lineNumber) {
        super(lineNumber);

        this.hasNectar = hasNectar;
        this.isAngry = isAngry;
    }

    /**
     * A factory for creating instances of {@link PsiBeeEntityData}.
     *
     * @since 0.1.0
     */
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

            return create(hasNectar, isAngry, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param hasNectar whether the bee data has nectar
         * @param isAngry whether the bee data is angry
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        protected PsiBeeEntityData create(@Nullable Boolean hasNectar, @Nullable Boolean isAngry, int lineNumber) {
            return new PsiBeeEntityData(hasNectar, isAngry, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public String getType() {
            return "entity data";
        }
    }
}

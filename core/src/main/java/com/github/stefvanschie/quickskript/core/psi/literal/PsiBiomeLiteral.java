package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.util.registry.BiomeRegistry;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets a biome by a specific name. This is always pre-computed.
 *
 * @since 0.1.0
 */
public class PsiBiomeLiteral extends PsiPrecomputedHolder<BiomeRegistry.Entry> {

    /**
     * The name of the biome to get the biome from
     */
    protected BiomeRegistry.Entry biome;

    /**
     * Creates a new element with the given line number
     *
     * @param biome the biome
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiBiomeLiteral(@NotNull BiomeRegistry.Entry biome, int lineNumber) {
        super(biome, lineNumber);
    }

    /**
     * A factory for creating {@link PsiBiomeLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * This gets called upon parsing
         *
         * @param skriptLoader the skript loader to parse with
         * @param text the text to parse
         * @param lineNumber the line number
         * @return the literal, or null to indicate failure
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiBiomeLiteral tryParse(@NotNull SkriptLoader skriptLoader, @NotNull String text, int lineNumber) {
            BiomeRegistry.Entry biome = skriptLoader.getBiomeRegistry().getEntries().stream()
                .filter(entry -> entry.getName().equalsIgnoreCase(text))
                .findAny()
                .orElse(null);

            if (biome == null) {
                return null;
            }

            return create(biome, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the
         * {@link #tryParse(SkriptLoader, String, int)} method.
         *
         * @param biome the value of the literal
         * @param lineNumber the line number
         * @return the literal
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        protected PsiBiomeLiteral create(BiomeRegistry.Entry biome, int lineNumber) {
            return new PsiBiomeLiteral(biome, lineNumber);
        }
    }
}

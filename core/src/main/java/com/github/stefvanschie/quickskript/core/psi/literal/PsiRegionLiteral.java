package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.pattern.group.RegexGroup;
import com.github.stefvanschie.quickskript.core.pattern.group.SkriptPatternGroup;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.exception.ParseException;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.util.Pair;
import com.github.stefvanschie.quickskript.core.util.literal.Region;
import com.github.stefvanschie.quickskript.core.util.registry.RegionRegistry;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Gets a region by the given name
 *
 * @since 0.1.0
 */
public class PsiRegionLiteral extends PsiPrecomputedHolder<Region> {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiRegionLiteral(@NotNull Region region, int lineNumber) {
        super(region, lineNumber);
    }

    /**
     * A factory for creating {@link PsiRegionLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiRegionLiteral}s
         */
        @NotNull
        private SkriptPattern pattern = SkriptPattern.parse("\"<.+>\"");

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param skriptLoader the skript loader to parse with
         * @param result the pattern match result
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiRegionLiteral parse(@NotNull SkriptLoader skriptLoader, @NotNull SkriptMatchResult result,
            int lineNumber) {
            for (Pair<SkriptPatternGroup, String> matchedGroup : result.getMatchedGroups()) {
                if (!(matchedGroup.getX() instanceof RegexGroup)) {
                    continue;
                }

                RegionRegistry regionRegistry = skriptLoader.getRegionRegistry();
                Collection<? extends Region> regions = regionRegistry.getRegions(matchedGroup.getY());

                if (regions.size() == 0) {
                    return null;
                }

                if (regions.size() > 1) {
                    throw new ParseException("Multiple regions can correspond to given region name", lineNumber);
                }

                return create(regions.toArray(Region[]::new)[0], lineNumber);
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
        public PsiRegionLiteral create(@NotNull Region region, int lineNumber) {
            return new PsiRegionLiteral(region, lineNumber);
        }
    }
}

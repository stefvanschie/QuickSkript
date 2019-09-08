package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.pattern.group.RegexGroup;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.psi.util.pointermovement.ExitSectionsPointerMovement;
import com.github.stefvanschie.quickskript.core.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Exits from a certain amount and type of section
 *
 * @since 0.1.0
 */
public class PsiExitEffect extends PsiPrecomputedHolder<ExitSectionsPointerMovement> {

    /**
     * Creates a new element with the given line number
     *
     * @param exitSectionsPointerMovement the instruction to exit from the section(s)
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiExitEffect(@NotNull ExitSectionsPointerMovement exitSectionsPointerMovement, int lineNumber) {
        super(exitSectionsPointerMovement, lineNumber);
    }

    /**
     * A factory for creating {@link PsiExitEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * A map mapping the parse marks as used in {@link #patternsInfinite} and {@link #patternsFinite} to the correct
         * {@link ExitSectionsPointerMovement.Type}
         */
        @NotNull
        private final Map<Integer, ExitSectionsPointerMovement.Type> exitTypesByParseMark = Map.of(
            0, ExitSectionsPointerMovement.Type.EVERYTHING,
            1, ExitSectionsPointerMovement.Type.LOOPS,
            2, ExitSectionsPointerMovement.Type.CONDITIONALS
        );

        /**
         * A set of patterns where the amount of sections is infinite
         */
        @NotNull
        private final SkriptPattern[] patternsInfinite = SkriptPattern.parse(
            "(exit|stop) [trigger]",
            "(exit|stop) all (0\u00A6section|1\u00A6loop|2\u00A6conditional)s"
        );

        /**
         * A set of patterns where the amount of sections is finite
         */
        @NotNull
        @SuppressWarnings("HardcodedFileSeparator")
        private final SkriptPattern[] patternsFinite = SkriptPattern.parse(
            "(exit|stop) [(1|a|the|this)] (0\u00A6section|1\u00A6loop|2\u00A6conditional)",
            "(exit|stop) <\\d+> (0\u00A6section|1\u00A6loop|2\u00A6conditional)s"
        );

        /**
         * Parses the {@link #patternsInfinite} and invokes this method with its types if the match succeeds
         *
         * @param result the match result
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patternsInfinite")
        public PsiExitEffect parseInfinite(@NotNull SkriptMatchResult result, int lineNumber) {
            ExitSectionsPointerMovement.Type type = exitTypesByParseMark.get(result.getParseMark());

            return new PsiExitEffect(new ExitSectionsPointerMovement(type), lineNumber);
        }

        /**
         * Parses the {@link #patternsFinite} and invokes this method with its types if the match succeeds
         *
         * @param result the match result
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patternsFinite")
        public PsiExitEffect parseFinite(@NotNull SkriptMatchResult result, int lineNumber) {
            String regexMatch = result.getMatchedGroups().stream()
                .filter(entry -> entry.getX() instanceof RegexGroup)
                .map(Pair::getY)
                .findAny()
                .orElse(null);

            ExitSectionsPointerMovement.Type type = exitTypesByParseMark.get(result.getParseMark());

            int amount = regexMatch == null ? 1 : Integer.parseInt(regexMatch);
            return new PsiExitEffect(new ExitSectionsPointerMovement(type, amount), lineNumber);
        }
    }
}

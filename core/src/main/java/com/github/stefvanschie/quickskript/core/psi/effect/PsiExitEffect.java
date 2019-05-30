package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.exception.ParseException;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.pointermovement.ExitSectionsPointerMovement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    public static class Factory implements PsiElementFactory<PsiExitEffect> {

        /**
         * A set of patterns where the amount of sections is infinite
         */
        @NotNull
        private final Set<Pattern> patternsInfinite = Set.of(
            "(?:exit|stop)(?: trigger)?",
            "(?:exit|stop) all (?<type>section|loop|conditional)s"
        ).stream().map(Pattern::compile).collect(Collectors.toUnmodifiableSet());

        /**
         * A set of patterns where the amount of sections is finite
         */
        @NotNull
        @SuppressWarnings("HardcodedFileSeparator")
        private final Set<Pattern> patternsFinite = Set.of(
            "(?:exit|stop)(?: (?:1|a|the|this))? (?<type>section|loop|conditional)",
            "(?:exit|stop) (?<amount>\\d+) (?<type>section|loop|conditional)s"
        ).stream().map(Pattern::compile).collect(Collectors.toUnmodifiableSet());

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiExitEffect tryParse(@NotNull String text, int lineNumber) {
            var matcherInfinite = patternsInfinite.stream()
                .map(pattern -> pattern.matcher(text))
                .filter(Matcher::matches)
                .findAny()
                .orElse(null);

            if (matcherInfinite != null) {
                ExitSectionsPointerMovement.Type type = ExitSectionsPointerMovement.Type.EVERYTHING;

                if (matcherInfinite.groupCount() != 0) {
                    String typeGroup = matcherInfinite.group("type");
                    type = ExitSectionsPointerMovement.Type.byName(typeGroup);

                    if (type == null) {
                        throw new ParseException("Illegal section type", lineNumber);
                    }
                }

                return new PsiExitEffect(new ExitSectionsPointerMovement(type), lineNumber);
            }

            var matcherFinite = patternsFinite.stream()
                .map(pattern -> pattern.matcher(text))
                .filter(Matcher::matches)
                .findAny()
                .orElse(null);

            if (matcherFinite != null) {
                String typeGroup = matcherFinite.group("type");
                ExitSectionsPointerMovement.Type type = ExitSectionsPointerMovement.Type.byName(typeGroup);

                if (type == null) {
                    throw new ParseException("Illegal section type", lineNumber);
                }

                int amount = matcherFinite.groupCount() < 2 ? 1 : Integer.parseInt(matcherFinite.group("amount"));

                return new PsiExitEffect(new ExitSectionsPointerMovement(type, amount), lineNumber);
            }

            return null;
        }
    }
}

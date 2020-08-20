package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.pattern.group.RegexGroup;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Executes the specified code if the specified condition is true.
 *
 * @since 0.1.0
 */
public class PsiDoIfEffect extends PsiElement<Void> {

    /**
     * The expression to execute
     */
    @NotNull
    private final PsiElement<?> expression;

    /**
     * The condition to check
     */
    @NotNull
    private final PsiElement<?> condition;

    /**
     * Creates a new element with the given line number
     *
     * @param expression the expression to execute, see {@link #expression}
     * @param condition the condition to check, see {@link #condition}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiDoIfEffect(@NotNull PsiElement<?> expression, @NotNull PsiElement<?> condition, int lineNumber) {
        super(lineNumber);

        this.expression = expression;
        this.condition = condition;

        if (condition.isPreComputed()) {
            //TODO: show warning
        }
    }

    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        if (condition.execute(context, Boolean.class)) {
            expression.execute(context);
        }

        return null;
    }

    /**
     * A factory for creating {@link PsiDoIfEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern to match {@link PsiDoIfEffect}s
         */
        @NotNull
        private final SkriptPattern pattern = SkriptPattern.parse("<.+> if <.+>");

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param skriptLoader the skript loader
         * @param input the input to match against
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiDoIfEffect parse(@NotNull SkriptLoader skriptLoader, @NotNull String input, int lineNumber) {
            for (SkriptMatchResult result : pattern.match(input)) {
                if (result.hasUnmatchedParts()) {
                    continue;
                }

                String[] regexGroups = result.getMatchedGroups().stream()
                    .filter(entry -> entry.getX() instanceof RegexGroup)
                    .map(Pair::getY)
                    .toArray(String[]::new);

                PsiElement<?> expression = skriptLoader.tryParseElement(regexGroups[0], lineNumber);
                PsiElement<?> condition = skriptLoader.tryParseElement(regexGroups[1], lineNumber);

                if (expression == null || condition == null) {
                    continue;
                }

                return new PsiDoIfEffect(expression, condition, lineNumber);
            }

            return null;
        }
    }
}

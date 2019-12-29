package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.pattern.group.RegexGroup;
import com.github.stefvanschie.quickskript.core.pattern.group.TypeGroup;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.exception.ParseException;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Executes one of two elements depending on whether the condition is true or false. If the condition evaluates to true,
 * the if code is ran, otherwise the else code is being ran.
 *
 * @since 0.1.0
 */
public class PsiTernaryExpression extends PsiElement<Object> {

    /**
     * The condition to determine which code should be executed
     */
    private PsiElement<?> condition;

    /**
     * The code to be ran when the {@link #condition} evaluates to true
     */
    private PsiElement<?> ifCode;

    /**
     * The code to be ran when the {@link #condition} evaluates to false
     */
    private PsiElement<?> elseCode;

    /**
     * Creates a new element with the given line number
     *
     * @param condition the condition for this ternary operator
     * @param ifCode the code to be executed when the condition evaluates to true
     * @param elseCode the code to be executed when the condition evaluates to false
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiTernaryExpression(@NotNull PsiElement<?> condition, @NotNull PsiElement<?> ifCode,
        @NotNull PsiElement<?> elseCode, int lineNumber) {
        super(lineNumber);

        this.condition = condition;
        this.ifCode = ifCode;
        this.elseCode = elseCode;

        if (condition.isPreComputed()) {
            boolean conditionResult = condition.execute(null, Boolean.class);

            if (conditionResult && ifCode.isPreComputed()) {
                preComputed = ifCode.execute(null);
            } else if (!conditionResult && elseCode.isPreComputed()) {
                preComputed = elseCode.execute(null);
            } else {
                return;
            }

            this.condition = null;
            this.ifCode = null;
            this.elseCode = null;
        }
    }

    @Nullable
    @Override
    protected Object executeImpl(@Nullable Context context) {
        if (condition.execute(context, Boolean.class)) {
            return ifCode.execute(context);
        }

        return elseCode.execute(context);
    }

    /**
     * A factory for creating {@link PsiTernaryExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiTernaryExpression}s
         */
        @NotNull
        private SkriptPattern pattern = SkriptPattern.parse("%objects% if <.+>[,] (otherwise|else) %objects%");

        /**
         * Tries to parse a ternary expression.
         *
         * @param input the input to match against
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiTernaryExpression parse(@NotNull String input, int lineNumber) {
            for (SkriptMatchResult match : pattern.match(input)) {
                if (match.hasUnmatchedParts()) {
                    continue;
                }

                String conditionString = match.getMatchedGroups().stream()
                    .filter(entry -> entry.getX() instanceof RegexGroup)
                    .map(Pair::getY)
                    .findAny()
                    .orElseThrow(() -> new ParseException("Ternary expression parsed incorrectly", lineNumber));
                String[] typeGroups = match.getMatchedGroups().stream()
                    .filter(entry -> entry.getX() instanceof TypeGroup)
                    .map(Pair::getY)
                    .toArray(String[]::new);

                PsiElement<?> condition = SkriptLoader.get().tryParseElement(conditionString, lineNumber);
                PsiElement<?> ifCode = SkriptLoader.get().tryParseElement(typeGroups[0], lineNumber);
                PsiElement<?> elseCode = SkriptLoader.get().tryParseElement(typeGroups[1], lineNumber);

                if (condition == null || ifCode == null || elseCode == null) {
                    continue;
                }

                return create(condition, ifCode, elseCode, lineNumber);
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param condition the condition for this ternary operator
         * @param ifCode the code to be executed when the condition evaluates to true
         * @param elseCode the code to be executed when the condition evaluates to false
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiTernaryExpression create(@NotNull PsiElement<?> condition, @NotNull PsiElement<?> ifCode,
            @NotNull PsiElement<?> elseCode, int lineNumber) {
            return new PsiTernaryExpression(condition, ifCode, elseCode, lineNumber);
        }
    }
}

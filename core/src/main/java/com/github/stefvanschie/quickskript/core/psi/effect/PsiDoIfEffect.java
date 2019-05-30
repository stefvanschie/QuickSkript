package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

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
    private PsiElement<?> expression;

    /**
     * The condition to check
     */
    @NotNull
    private PsiElement<?> condition;

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

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        if (condition.execute(context, Boolean.class)) {
            this.expression.execute(context);
        }

        return null;
    }

    /**
     * A factory for creating {@link PsiDoIfEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiDoIfEffect> {

        /**
         * The pattern to match {@link PsiDoIfEffect}s
         */
        @NotNull
        private final Pattern pattern = Pattern.compile("(?<expression>.+) if (?<condition>.+)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiDoIfEffect tryParse(@NotNull String text, int lineNumber) {
            var matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                return null;
            }

            var skriptLoader = SkriptLoader.get();

            PsiElement<?> expression = skriptLoader.forceParseElement(matcher.group("expression"), lineNumber);
            PsiElement<?> condition = skriptLoader.forceParseElement(matcher.group("condition"), lineNumber);

            return new PsiDoIfEffect(expression, condition, lineNumber);
        }
    }
}

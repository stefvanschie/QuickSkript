package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if the given plugins are enabled.
 *
 * @since 0.1.0
 */
public class PsiIsPluginEnabledCondition extends PsiElement<Boolean> {

    /**
     * The plugins to check if they are enabled.
     */
    @NotNull
    protected final PsiElement<?> plugins;

    /**
     * If false, the result is negated.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param plugins the plugins to check if they are enabled
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsPluginEnabledCondition(@NotNull PsiElement<?> plugins, boolean positive, int lineNumber) {
        super(lineNumber);

        this.plugins = plugins;
        this.positive = positive;
    }

    /**
     * A factory for creating instances of {@link PsiIsPluginEnabledCondition}.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param plugins the plugins to check if they are enabled
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("plugin[s] %strings% (is|are) enabled")
        public PsiIsPluginEnabledCondition parsePositive(@NotNull PsiElement<?> plugins, int lineNumber) {
            return create(plugins, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param plugins the plugins to check if they are enabled
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("plugin[s] %strings% (is|are)(n't| not) enabled")
        @Pattern("plugin[s] %strings% (is|are) disabled")
        public PsiIsPluginEnabledCondition parseNegative(@NotNull PsiElement<?> plugins, int lineNumber) {
            return create(plugins, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param plugins the plugins to check if they are enabled
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        public PsiIsPluginEnabledCondition create(@NotNull PsiElement<?> plugins, boolean positive, int lineNumber) {
            return new PsiIsPluginEnabledCondition(plugins, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public String getType() {
            return "boolean";
        }
    }
}

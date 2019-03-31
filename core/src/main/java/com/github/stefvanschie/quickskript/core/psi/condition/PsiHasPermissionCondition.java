package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Checks for a specific condition and returns its result. This cannot be pre computed, since there is no guarantee that
 * permissions won't be changed in an earlier piece of code.
 *
 * @since 0.1.0
 */
public class PsiHasPermissionCondition extends PsiElement<Boolean> {

    /**
     * The player/console to check the {@link #permission} for
     */
    protected final PsiElement<?> permissible;

    /**
     * The permission to check for
     */
    protected final PsiElement<?> permission;

    /**
     * True if the result stays the same, false if it needs to be inverted. This is determined by whether there is a
     * "doesn't/don't/does not/do not" in the condition.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given lien number
     *
     * @param permissible the player/console to check for the {@link #permission}, see {@link #permissible}
     * @param permission the permission to check for, see {@link #permission}
     * @param positive true if the result stays the same, false if the result needs to be inverted, see
     *                 {@link #positive}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiHasPermissionCondition(PsiElement<?> permissible, PsiElement<?> permission, boolean positive,
                                      int lineNumber) {
        super(lineNumber);

        this.permissible = permissible;
        this.permission = permission;
        this.positive = positive;
    }

    /**
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Nullable
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        throw new UnsupportedOperationException("Cannot execute expression without implementation.");
    }

    /**
     * A factory for creating psi has permission conditions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiHasPermissionCondition> {

        /**
         * The pattern to match has permission conditions with
         */
        @NotNull
        private final Pattern pattern = Pattern
            .compile("([\\s\\S]+?) ((?:do(?:es)?n't|do(?:es)? not) )?ha(?:ve|s) (?:the )?permissions? ([\\s\\S]+)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiHasPermissionCondition tryParse(@NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                return null;
            }

            PsiElement<?> permissible = SkriptLoader.get().forceParseElement(matcher.group(1), lineNumber);
            PsiElement<?> permission = SkriptLoader.get().forceParseElement(matcher.group(3), lineNumber);

            return create(permissible, permission, matcher.group(2) == null, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param permissible the object to check the permission for
         * @param permission the permission to check
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        protected PsiHasPermissionCondition create(PsiElement<?> permissible, PsiElement<?> permission, boolean positive,
                                                   int lineNumber) {
            return new PsiHasPermissionCondition(permissible, permission, positive, lineNumber);
        }
    }
}

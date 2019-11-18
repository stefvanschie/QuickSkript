package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
    @NotNull
    protected final PsiElement<?> permissible;

    /**
     * The permission to check for
     */
    @NotNull
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
    protected PsiHasPermissionCondition(@NotNull PsiElement<?> permissible, @NotNull PsiElement<?> permission,
                                        boolean positive, int lineNumber) {
        super(lineNumber);

        this.permissible = permissible;
        this.permission = permission;
        this.positive = positive;
    }

    /**
     * A factory for creating psi has permission conditions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The positive pattern to match has permission conditions with
         */
        @SuppressWarnings("HardcodedFileSeparator")
        @NotNull
        private final SkriptPattern positivePattern =
            SkriptPattern.parse("%players/console% (has|have) [the] permission[s] %texts%");

        /**
         * The negative pattern to match has permission conditions with
         */
        @SuppressWarnings("HardcodedFileSeparator")
        @NotNull
        private final SkriptPattern negativePattern =
            SkriptPattern.parse("%players/console% (doesn't|does not|do not|don't) have [the] permission[s] %texts%");

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param permissible the object to check the permission for
         * @param permission the permission to check
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiHasPermissionCondition parsePositive(@NotNull PsiElement<?> permissible,
                                                       @NotNull PsiElement<?> permission, int lineNumber) {
            return create(permissible, permission, true, lineNumber);
        }

        /**
         * Parses the {@link #negativePattern} and invokes this method with its types if the match succeeds
         *
         * @param permissible the object to check the permission for
         * @param permission the permission to check
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiHasPermissionCondition parseNegative(@NotNull PsiElement<?> permissible,
                                                       @NotNull PsiElement<?> permission, int lineNumber) {
            return create(permissible, permission, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param permissible the object to check the permission for
         * @param permission the permission to check
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        protected PsiHasPermissionCondition create(@NotNull PsiElement<?> permissible,
                                                   @NotNull PsiElement<?> permission, boolean positive,
                                                   int lineNumber) {
            return new PsiHasPermissionCondition(permissible, permission, positive, lineNumber);
        }
    }
}

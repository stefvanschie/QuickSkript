package com.github.stefvanschie.quickskript.psi.condition;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.util.Text;
import org.bukkit.permissions.Permissible;
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
    private final PsiElement<?> permissible;

    /**
     * The permission to check for
     */
    private final PsiElement<?> permission;

    /**
     * True if the result stays the same, false if it needs to be inverted. This is determined by whether there is a
     * "doesn't/don't/does not/do not" in the condition.
     */
    private final boolean positive;

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
    private PsiHasPermissionCondition(PsiElement<?> permissible, PsiElement<?> permission, boolean positive,
                                     int lineNumber) {
        super(lineNumber);

        this.permissible = permissible;
        this.permission = permission;
        this.positive = positive;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        return positive == permissible
            .execute(context, Permissible.class).hasPermission(permission.execute(context, Text.class).construct());
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
        private final Pattern pattern = Pattern
            .compile("([\\s\\S]+?) ((?:do(?:es)?n't|do(?:es)? not) )?ha(?:ve|s) (?:the )?permissions? ([\\s\\S]+)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiHasPermissionCondition tryParse(@NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches())
                return null;

            PsiElement<?> permissible = SkriptLoader.get().forceParseElement(matcher.group(1), lineNumber);
            PsiElement<?> permission = SkriptLoader.get().forceParseElement(matcher.group(3), lineNumber);

            return new PsiHasPermissionCondition(permissible, permission, matcher.group(2) == null, lineNumber);
        }
    }
}

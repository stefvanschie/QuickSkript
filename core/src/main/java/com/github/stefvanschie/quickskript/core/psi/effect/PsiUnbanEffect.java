package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * An effect for undoing (IP) bans.
 *
 * @since 0.1.0
 */
public class PsiUnbanEffect extends PsiElement<Void> {

    /**
     * The object to undo the (IP) ban for
     */
    @NotNull
    protected final PsiElement<?> object;

    /**
     * True if this ban is an IP ban, false otherwise
     */
    protected final boolean ipBan;

    /**
     * Creates a new element with the given line number
     *
     * @param object the object to undo the (IP) ban for
     * @param ipBan true if this ban is an IP ban, false otherwise
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiUnbanEffect(@NotNull PsiElement<?> object, boolean ipBan, int lineNumber) {
        super(lineNumber);

        this.object = object;
        this.ipBan = ipBan;
    }

    /**
     * A factory for creating {@link PsiUnbanEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for non-IP-ban {@link PsiUnbanEffect}s
         */
        @SuppressWarnings("HardcodedFileSeparator")
        @NotNull
        private final SkriptPattern nonIPBanPattern = SkriptPattern.parse("unban %texts/offline players%");

        /**
         * The pattern for IP-ban {@link PsiUnbanEffect}s
         */
        @NotNull
        private final SkriptPattern[] ipBanPatterns = SkriptPattern.parse(
            "unban %players% by IP",
            "(IP(-| )unban|un[-]IP[-]ban) %players%"
        );

        /**
         * Parses the {@link #nonIPBanPattern} and invokes this method with its types if the match succeeds
         *
         * @param object the object to unban
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("nonIPBanPattern")
        public PsiUnbanEffect parseNonIPBan(@NotNull PsiElement<?> object, int lineNumber) {
            return create(object, false, lineNumber);
        }

        /**
         * Parses the {@link #ipBanPatterns} and invokes this method with its types if the match succeeds
         *
         * @param object the object to unban
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("ipBanPatterns")
        public PsiUnbanEffect parseIPBan(@NotNull PsiElement<?> object, int lineNumber) {
            return create(object, true, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param object the object to be banned
         * @param ipBan true if this ban is an IP ban, false otherwise
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiUnbanEffect create(@NotNull PsiElement<?> object, boolean ipBan, int lineNumber) {
            return new PsiUnbanEffect(object, ipBan, lineNumber);
        }
    }
}

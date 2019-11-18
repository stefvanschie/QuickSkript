package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An effect for issuing (IP) bans.
 *
 * @since 0.1.0
 */
public class PsiBanEffect extends PsiElement<Void> {

    /**
     * The object to issue the (IP) ban for
     */
    @NotNull
    protected final PsiElement<?> object;

    /**
     * The reason for the ban
     */
    @Nullable
    protected final PsiElement<?> reason;

    /**
     * True if this ban is an IP ban, false otherwise
     */
    protected final boolean ipBan;

    /**
     * Creates a new element with the given line number
     *
     * @param object the object to issue the (IP) ban for
     * @param reason the reason for the ban
     * @param ipBan true if this ban is an IP ban, false otherwise
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiBanEffect(@NotNull PsiElement<?> object, @Nullable PsiElement<?> reason, boolean ipBan,
                           int lineNumber) {
        super(lineNumber);

        this.object = object;
        this.reason = reason;
        this.ipBan = ipBan;
    }

    /**
     * A factory for creating {@link PsiBanEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for non-IP-ban {@link PsiBanEffect}s
         */
        @SuppressWarnings("HardcodedFileSeparator")
        @NotNull
        private final SkriptPattern nonIPBanPattern = SkriptPattern
            .parse("ban %texts/offline players% [(by reason of|because [of]|on account of|due to) %text%]");

        /**
         * The pattern for IP-ban {@link PsiBanEffect}s
         */
        @NotNull
        private final SkriptPattern[] ipBanPatterns = SkriptPattern.parse(
            "ban %players% by IP [(by reason of|because [of]|on account of|due to) %text%]",
            "IP(-| )ban %players% [(by reason of|because [of]|on account of|due to) %text%]"
        );

        /**
         * Parses the {@link #nonIPBanPattern} and invokes this method with its types if the match succeeds
         *
         * @param object the object to be banned
         * @param reason the reason for the bane
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("nonIPBanPattern")
        public PsiBanEffect parseNonIPBan(@NotNull PsiElement<?> object, @Nullable PsiElement<?> reason,
                                          int lineNumber) {
            return create(object, reason, false, lineNumber);
        }

        /**
         * Parses the {@link #ipBanPatterns} and invokes this method with its types if the match succeeds
         *
         * @param object the object to be banned
         * @param reason the reason for the bane
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("ipBanPatterns")
        public PsiBanEffect parseIPBan(@NotNull PsiElement<?> object, @Nullable PsiElement<?> reason,
                                          int lineNumber) {
            return create(object, reason, true, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param object the object to be banned
         * @param reason the reason for the ban
         * @param ipBan true if this ban is an IP ban, false otherwise
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiBanEffect create(@NotNull PsiElement<?> object, @Nullable PsiElement<?> reason, boolean ipBan,
                                   int lineNumber) {
            return new PsiBanEffect(object, reason, ipBan, lineNumber);
        }
    }
}

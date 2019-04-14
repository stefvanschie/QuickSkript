package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    protected PsiElement<?> object;

    /**
     * The reason for the ban
     */
    @Nullable
    protected PsiElement<?> reason;

    /**
     * True if this ban is an IP ban, false otherwise
     */
    protected boolean ipBan;

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
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        throw new UnsupportedOperationException("Cannot execute expression without implementation.");
    }

    /**
     * A factory for creating {@link PsiBanEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiBanEffect> {

        /**
         * The pattern for non-IP-ban {@link PsiBanEffect}s
         */
        @NotNull
        private final Pattern pattern =
            Pattern.compile("ban ([\\s\\S]+?)(?: (?:by reason of|because(?: of)?|on account of|due to) ([\\s\\S]+))?$");

        /**
         * The pattern for IP-ban {@link PsiBanEffect}s
         */
        @NotNull
        private final Pattern ipBanPattern = Pattern.compile(
            "(IP[- ])?ban ([\\s\\S]+?)( by IP)?(?: (?:by reason of|because(?: of)?|on account of|due to) ([\\s\\S]+))?$"
        );

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiBanEffect tryParse(@NotNull String text, int lineNumber) {
            SkriptLoader skriptLoader = SkriptLoader.get();

            Matcher nonIpBanMatcher = pattern.matcher(text);

            if (nonIpBanMatcher.matches()) {
                PsiElement<?> object = skriptLoader.forceParseElement(nonIpBanMatcher.group(1), lineNumber);
                PsiElement<?> reason = null;

                if (nonIpBanMatcher.groupCount() >= 2) {
                    reason = skriptLoader.forceParseElement(nonIpBanMatcher.group(2), lineNumber);
                }

                return create(object, reason, false, lineNumber);
            }

            Matcher ipBanMatcher = ipBanPattern.matcher(text);

            if (ipBanMatcher.matches()) {
                PsiElement<?> object = skriptLoader.forceParseElement(ipBanMatcher.group(1), lineNumber);
                PsiElement<?> reason = null;

                if (ipBanMatcher.groupCount() >= 2) {
                    reason = skriptLoader.forceParseElement(ipBanMatcher.group(2), lineNumber);
                }

                return create(object, reason, true, lineNumber);
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
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

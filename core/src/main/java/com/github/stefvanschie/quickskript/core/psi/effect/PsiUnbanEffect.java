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
 * An effect for undoing (IP) bans.
 *
 * @since 0.1.0
 */
public class PsiUnbanEffect extends PsiElement<Void> {

    /**
     * The object to undo the (IP) ban for
     */
    @NotNull
    protected PsiElement<?> object;

    /**
     * True if this ban is an IP ban, false otherwise
     */
    protected boolean ipBan;

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
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        throw new UnsupportedOperationException("Cannot execute expression without implementation.");
    }

    /**
     * A factory for creating {@link PsiUnbanEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiUnbanEffect> {

        /**
         * The pattern for non-IP-ban {@link PsiUnbanEffect}s
         */
        @NotNull
        private final Pattern pattern =
            Pattern.compile("unban ([\\s\\S]+)");

        /**
         * The pattern for IP-ban {@link PsiUnbanEffect}s
         */
        @NotNull
        private final Pattern ipBanPattern = Pattern.compile(
            "(IP[- ])?ban ([\\s\\S]+?)( by IP)?$"
        );

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiUnbanEffect tryParse(@NotNull String text, int lineNumber) {
            SkriptLoader skriptLoader = SkriptLoader.get();

            Matcher nonIpBanMatcher = pattern.matcher(text);

            if (nonIpBanMatcher.matches()) {
                PsiElement<?> object = skriptLoader.forceParseElement(nonIpBanMatcher.group(1), lineNumber);

                return create(object, false, lineNumber);
            }

            Matcher ipBanMatcher = ipBanPattern.matcher(text);

            if (ipBanMatcher.matches()) {
                PsiElement<?> object = skriptLoader.forceParseElement(ipBanMatcher.group(1), lineNumber);

                return create(object, true, lineNumber);
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
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

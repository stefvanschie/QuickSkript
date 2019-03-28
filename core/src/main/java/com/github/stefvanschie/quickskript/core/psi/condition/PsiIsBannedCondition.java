package com.github.stefvanschie.quickskript.core.psi.condition;

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
 * Checks whether an offline player or ip is (ip) banned. This cannot be pre computed, since a ban may be set/revoked
 * during game play.
 *
 * @since 0.1.0
 */
public class PsiIsBannedCondition extends PsiElement<Boolean> {

    /**
     * The object to check the ban for
     */
    @NotNull
    protected PsiElement<?> object;

    /**
     * False if the execution result needs to be inverted
     */
    protected boolean positive;

    /**
     * True if we're checking ip bans, false if not
     */
    protected boolean ipBan;

    /**
     * Creates a new element with the given line number
     *
     * @param object the object to check the ban for
     * @param positive false if the execution result needs to be inverted
     * @param ipBan true if we're checking ip bans, false otherwise
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsBannedCondition(@NotNull PsiElement<?> object, boolean positive, boolean ipBan, int lineNumber) {
        super(lineNumber);

        this.object = object;
        this.positive = positive;
        this.ipBan = ipBan;
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
     * A factory for creating {@link PsiIsBannedCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiIsBannedCondition> {

        /**
         * The pattern for matching positive is banned conditions
         */
        @NotNull
        private final Pattern positivePattern = Pattern.compile("([\\s\\S]+) (?:is|are) (IP[ -])?banned");

        /**
         * The pattern for matching negative is banned conditions
         */
        @NotNull
        private final Pattern negativePattern =
            Pattern.compile("([\\s\\S]+) (?:isn't|is not|aren't|are not) (IP[ -])?banned");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiIsBannedCondition tryParse(@NotNull String text, int lineNumber) {
            Matcher positiveMatcher = positivePattern.matcher(text);

            if (positiveMatcher.matches()) {
                PsiElement<?> object = SkriptLoader.get().forceParseElement(positiveMatcher.group(1), lineNumber);

                return create(object, true, positiveMatcher.groupCount() >= 2, lineNumber);
            }

            Matcher negativeMatcher = negativePattern.matcher(text);

            if (negativeMatcher.matches()) {
                PsiElement<?> object = SkriptLoader.get().forceParseElement(negativeMatcher.group(1), lineNumber);

                return create(object, false, negativeMatcher.groupCount() >= 2, lineNumber);
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param object the object to check the ban for
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param ipBan true if we're checking ip bans, false otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiIsBannedCondition create(@NotNull PsiElement<?> object, boolean positive, boolean ipBan,
                                           int lineNumber) {
            return new PsiIsBannedCondition(object, positive, ipBan, lineNumber);
        }
    }
}

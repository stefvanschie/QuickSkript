package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
    protected final PsiElement<?> object;

    /**
     * False if the execution result needs to be inverted
     */
    protected final boolean positive;

    /**
     * True if we're checking ip bans, false if not
     */
    protected final boolean ipBan;

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
     * A factory for creating {@link PsiIsBannedCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching positive non-ip ban is banned conditions
         */
        @SuppressWarnings("HardcodedFileSeparator")
        @NotNull
        private final SkriptPattern positiveNonIPBanPattern =
            SkriptPattern.parse("%offline players/texts% (is|are) banned");

        /**
         * The pattern for matching positive ip ban is banned conditions
         */
        @SuppressWarnings("HardcodedFileSeparator")
        @NotNull
        private final SkriptPattern positiveIPBanPattern =
            SkriptPattern.parse("%players/texts% [(is|are) IP(-| )]banned");

        /**
         * The pattern for matching positive non-ip ban is banned conditions
         */
        @SuppressWarnings("HardcodedFileSeparator")
        @NotNull
        private final SkriptPattern negativeNonIPBanPattern =
            SkriptPattern.parse("%offline players/texts% (isn't|is not|aren't|are not) banned");

        /**
         * The pattern for matching positive ip ban is banned conditions
         */
        @SuppressWarnings("HardcodedFileSeparator")
        @NotNull
        private final SkriptPattern negativeIPBanPattern =
            SkriptPattern.parse("%players/texts% [(isn't|is not|aren't|are not) IP(-| )]banned");

        /**
         * Parses the {@link #positiveNonIPBanPattern} and invokes this method with its types if the match succeeds
         *
         * @param object the object to check the ban for
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positiveNonIPBanPattern")
        public PsiIsBannedCondition parsePositiveNonIPBan(@NotNull PsiElement<?> object, int lineNumber) {
            return create(object, true, false, lineNumber);
        }

        /**
         * Parses the {@link #positiveIPBanPattern} and invokes this method with its types if the match succeeds
         *
         * @param object the object to check the ban for
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positiveIPBanPattern")
        public PsiIsBannedCondition parsePositiveIPBan(@NotNull PsiElement<?> object, int lineNumber) {
            return create(object, true, true, lineNumber);
        }

        /**
         * Parses the {@link #negativeNonIPBanPattern} and invokes this method with its types if the match succeeds
         *
         * @param object the object to check the ban for
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativeNonIPBanPattern")
        public PsiIsBannedCondition parseNegativeNonIPBan(@NotNull PsiElement<?> object, int lineNumber) {
            return create(object, false, false, lineNumber);
        }

        /**
         * Parses the {@link #negativeIPBanPattern} and invokes this method with its types if the match succeeds
         *
         * @param object the object to check the ban for
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativeIPBanPattern")
        public PsiIsBannedCondition parseNegativeIPBan(@NotNull PsiElement<?> object, int lineNumber) {
            return create(object, false, true, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
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

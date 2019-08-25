package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.expression.util.*;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the amount of exhaustion the player has
 *
 * @since 0.1.0
 */
public class PsiExhaustionExpression extends PsiElement<Float> implements Addable, Deletable, Removable, RemoveAllable,
    Resettable, Settable {

    /**
     * The player to get the exhaustion from
     */
    @NotNull
    protected final PsiElement<?> player;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to get the exhaustion from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiExhaustionExpression(@NotNull PsiElement<?> player, int lineNumber) {
        super(lineNumber);

        this.player = player;
    }

    /**
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Nullable
    @Override
    protected Float executeImpl(@Nullable Context context) {
        throw new UnsupportedOperationException("Cannot execute expression without implementation.");
    }

    /**
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Override
    public void add(@Nullable Context context, @NotNull PsiElement<?> object) {
        throw new UnsupportedOperationException("Cannot change expression without implementation.");
    }

    /**
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Override
    public void delete(@Nullable Context context) {
        throw new UnsupportedOperationException("Cannot change expression without implementation.");
    }

    /**
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Override
    public void remove(@Nullable Context context, @NotNull PsiElement<?> object) {
        throw new UnsupportedOperationException("Cannot change expression without implementation.");
    }

    /**
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Override
    public void removeAll(@Nullable Context context, @NotNull PsiElement<?> object) {
        throw new UnsupportedOperationException("Cannot change expression without implementation.");
    }

    /**
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Override
    public void reset(@Nullable Context context) {
        throw new UnsupportedOperationException("Cannot change expression without implementation.");
    }

    /**
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Override
    public void set(@Nullable Context context, @NotNull PsiElement<?> object) {
        throw new UnsupportedOperationException("Cannot change expression without implementation.");
    }

    /**
     * A factory for creating {@link PsiExhaustionExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The patterns for matching {@link PsiExhaustionExpression}s
         */
        @NotNull
        private final SkriptPattern[] patterns = SkriptPattern.parse(
            "[the] exhaustion of %players%",
            "%players%'[s] exhaustion"
        );

        /**
         * Parses the {@link #patterns} and invokes this method with its types if the match succeeds
         *
         * @param player the player to get the exhaustion from
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patterns")
        public PsiExhaustionExpression parse(@NotNull PsiElement<?> player, int lineNumber) {
            return create(player, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param player the player to get the exhaustion from
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiExhaustionExpression create(@NotNull PsiElement<?> player, int lineNumber) {
            return new PsiExhaustionExpression(player, lineNumber);
        }
    }
}
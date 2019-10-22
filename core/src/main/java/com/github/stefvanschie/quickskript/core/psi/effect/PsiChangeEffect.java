package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.util.*;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.PatternTypeOrder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Changes an expression
 *
 * @since 0.1.0
 */
public class PsiChangeEffect extends PsiElement<Void> {

    /**
     * The objects to be added, removed or set on the {@link #changee}. Null if the {@link #changeMode} is either
     * delete or reset.
     */
    @Nullable
    private final PsiElement<?> objects;

    /**
     * The expression to change
     */
    @NotNull
    private final PsiElement<?> changee;

    /**
     * The way in which the {@link #changee} should be changed
     */
    @NotNull
    private final ChangeMode changeMode;

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiChangeEffect(@Nullable PsiElement<?> objects, @NotNull PsiElement<?> changee,
        @NotNull ChangeMode changeMode, int lineNumber) {
        super(lineNumber);

        this.objects = objects;
        this.changee = changee;
        this.changeMode = changeMode;
    }

    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        if (changeMode == ChangeMode.ADD && changee instanceof Addable) {
            ((Addable) changee).add(context, Objects.requireNonNull(objects));
        } else if (changeMode == ChangeMode.SET && changee instanceof Settable) {
            ((Settable) changee).set(context, Objects.requireNonNull(objects));
        } else if (changeMode == ChangeMode.REMOVE_ALL && changee instanceof RemoveAllable) {
            ((RemoveAllable) changee).removeAll(context, Objects.requireNonNull(objects));
        } else if (changeMode == ChangeMode.REMOVE && changee instanceof Removable) {
            ((Removable) changee).remove(context, Objects.requireNonNull(objects));
        } else if (changeMode == ChangeMode.DELETE && changee instanceof Deletable) {
            ((Deletable) changee).delete(context);
        } else if (changeMode == ChangeMode.RESET && changee instanceof Resettable) {
            ((Resettable) changee).reset(context);
        } else {
            throw new ExecutionException("Specified change cannot be applied on the given expression", lineNumber);
        }

        return null;
    }

    /**
     * A factory for creating {@link PsiChangeEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Patterns for adding something
         */
        @NotNull
        private final SkriptPattern[] addPatterns = SkriptPattern.parse(
            "(add|give) %objects% to %~objects%",
            "increase %~objects% by %objects%",
            "give %~objects% %objects%");

        /**
         * The pattern for setting something
         */
        @NotNull
        private final SkriptPattern setPattern = SkriptPattern.parse("set %~objects% to %objects%");

        /**
         * The pattern for removing everything of an expression
         */
        @NotNull
        private final SkriptPattern removeAllPattern =
            SkriptPattern.parse("remove (all|every) %objects% from %~objects%");

        /**
         * Patterns for removing something of an expression
         */
        @NotNull
        private final SkriptPattern[] removePatterns = SkriptPattern.parse(
            "(remove|subtract) %objects% from %~objects%",
            "reduce %~objects% by %objects%");

        /**
         * The pattern for deleting an expression
         */
        @NotNull
        private final SkriptPattern deletePattern = SkriptPattern.parse("(delete|clear) %~objects%");

        /**
         * the pattern for resetting an expression
         */
        @NotNull
        private final SkriptPattern resetPattern = SkriptPattern.parse("reset %~objects%");

        /**
         * Parses the {@link #addPatterns} and invokes this method with its types if the match succeeds
         *
         * @param objects the objects to add
         * @param changee the expression to change
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("addPatterns")
        @PatternTypeOrder(patterns = {1, 2}, typeOrder = {1, 0})
        public PsiChangeEffect parseAdd(@NotNull PsiElement<?> objects, @NotNull PsiElement<?> changee,
            int lineNumber) {
            return new PsiChangeEffect(objects, changee, ChangeMode.ADD, lineNumber);
        }

        /**
         * Parses the {@link #setPattern} and invokes this method with its types if the match succeeds
         *
         * @param objects the objects to set
         * @param changee the expression to change
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("setPattern")
        @PatternTypeOrder(patterns = 0, typeOrder = {1, 0})
        public PsiChangeEffect parseSet(@NotNull PsiElement<?> objects, @NotNull PsiElement<?> changee,
            int lineNumber) {
            return new PsiChangeEffect(objects, changee, ChangeMode.SET, lineNumber);
        }

        /**
         * Parses the {@link #removeAllPattern} and invokes this method with its types if the match succeeds
         *
         * @param objects the objects to remove
         * @param changee the expression to change
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("removeAllPattern")
        public PsiChangeEffect parseRemoveAll(@NotNull PsiElement<?> objects, @NotNull PsiElement<?> changee,
            int lineNumber) {
            return new PsiChangeEffect(objects, changee, ChangeMode.REMOVE_ALL, lineNumber);
        }

        /**
         * Parses the {@link #removePatterns} and invokes this method with its types if the match succeeds
         *
         * @param objects the objects to remove
         * @param changee the expression to change
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("removePatterns")
        @PatternTypeOrder(patterns = 1, typeOrder = {1, 0})
        public PsiChangeEffect parseRemove(@NotNull PsiElement<?> objects, @NotNull PsiElement<?> changee,
            int lineNumber) {
            return new PsiChangeEffect(objects, changee, ChangeMode.REMOVE, lineNumber);
        }

        /**
         * Parses the {@link #deletePattern} and invokes this method with its types if the match succeeds
         *
         * @param changee the expression to change
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("deletePattern")
        public PsiChangeEffect parseDelete(@NotNull PsiElement<?> changee, int lineNumber) {
            return new PsiChangeEffect(null, changee, ChangeMode.DELETE, lineNumber);
        }

        /**
         * Parses the {@link #resetPattern} and invokes this method with its types if the match succeeds
         *
         * @param changee the expression to change
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("resetPattern")
        public PsiChangeEffect parseReset(@NotNull PsiElement<?> changee, int lineNumber) {
            return new PsiChangeEffect(null, changee, ChangeMode.RESET, lineNumber);
        }
    }

    /**
     * In what way an expression should be changed
     *
     * @since 0.1.0
     */
    public enum ChangeMode {

        /**
         * Indicates that something should be added to the expression
         *
         * @since 0.1.0
         */
        ADD,

        /**
         * Indicates that something should be set on the expression
         *
         * @since 0.1.0
         */
        SET,

        /**
         * Indicates that something should be removed from the expression
         *
         * @since 0.1.0
         */
        REMOVE_ALL,

        /**
         * Indicates that something should be removed from the expression
         *
         * @since 0.1.0
         */
        REMOVE,

        /**
         * Indicates that the expression should be deleted
         *
         * @since 0.1.0
         */
        DELETE,

        /**
         * Indicates that the expression should be reset
         *
         * @since 0.1.0
         */
        RESET
    }
}

package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.util.*;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
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
    protected Void executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        if (changeMode == ChangeMode.ADD && changee instanceof Addable) {
            ((Addable) changee).add(environment, context, Objects.requireNonNull(objects));
        } else if (changeMode == ChangeMode.SET && changee instanceof Settable) {
            ((Settable) changee).set(environment, context, Objects.requireNonNull(objects));
        } else if (changeMode == ChangeMode.REMOVE_ALL && changee instanceof RemoveAllable) {
            ((RemoveAllable) changee).removeAll(environment, context, Objects.requireNonNull(objects));
        } else if (changeMode == ChangeMode.REMOVE && changee instanceof Removable) {
            ((Removable) changee).remove(environment, context, Objects.requireNonNull(objects));
        } else if (changeMode == ChangeMode.DELETE && changee instanceof Deletable) {
            ((Deletable) changee).delete(environment, context);
        } else if (changeMode == ChangeMode.RESET && changee instanceof Resettable) {
            ((Resettable) changee).reset(environment, context);
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
         * Parses the patterns and invokes this method with its types if the match succeeds
         *
         * @param objects the objects to add
         * @param changee the expression to change
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("(add|give) %objects% to %~objects%")
        @Pattern("increase %~objects% by %objects%")
        @Pattern("give %~objects% %objects%")
        public PsiChangeEffect parseAdd(@NotNull PsiElement<?> objects, @NotNull PsiElement<?> changee,
            int lineNumber) {
            return new PsiChangeEffect(objects, changee, ChangeMode.ADD, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param objects the objects to set
         * @param changee the expression to change
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("set %~objects% to %objects%")
        public PsiChangeEffect parseSet(@NotNull PsiElement<?> objects, @NotNull PsiElement<?> changee,
            int lineNumber) {
            return new PsiChangeEffect(objects, changee, ChangeMode.SET, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param objects the objects to remove
         * @param changee the expression to change
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("remove (all|every) %objects% from %~objects%")
        public PsiChangeEffect parseRemoveAll(@NotNull PsiElement<?> objects, @NotNull PsiElement<?> changee,
            int lineNumber) {
            return new PsiChangeEffect(objects, changee, ChangeMode.REMOVE_ALL, lineNumber);
        }

        /**
         * Parses the patterns and invokes this method with its types if the match succeeds
         *
         * @param objects the objects to remove
         * @param changee the expression to change
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("(remove|subtract) %objects% from %~objects%")
        @Pattern("reduce %~objects% by %objects%")
        public PsiChangeEffect parseRemove(@NotNull PsiElement<?> objects, @NotNull PsiElement<?> changee,
            int lineNumber) {
            return new PsiChangeEffect(objects, changee, ChangeMode.REMOVE, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param changee the expression to change
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("(delete|clear) %~objects%")
        public PsiChangeEffect parseDelete(@NotNull PsiElement<?> changee, int lineNumber) {
            return new PsiChangeEffect(null, changee, ChangeMode.DELETE, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param changee the expression to change
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("reset %~objects%")
        public PsiChangeEffect parseReset(@NotNull PsiElement<?> changee, int lineNumber) {
            return new PsiChangeEffect(null, changee, ChangeMode.RESET, lineNumber);
        }

        @Nullable
        @Contract(pure = true)
        @Override
        public Type getType() {
            return null;
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

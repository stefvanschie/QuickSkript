package com.github.stefvanschie.quickskript.core.psi.section;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiSection;
import com.github.stefvanschie.quickskript.core.psi.PsiSectionFactory;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.util.pointermovement.ExitSectionsPointerMovement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A section which only executes the contained elements if its condition is met.
 * A section which gets executed when the condition is not met can be attached.
 *
 * This cannot be pre computed, since the elements inside of this statement may not be executed on startup.
 *
 * @since 0.1.0
 */
public class PsiIf extends PsiSection {

    /**
     * The condition of the execution.
     */
    private final PsiElement<?> condition;

    /**
     * The section which should get executed if the condition is not met, if any.
     */
    @Nullable
    private PsiSection elseSection;

    /**
     * Creates a new psi if section.
     *
     * @param elements the elements this section should contain
     * @param condition the condition of the execution
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    public PsiIf(@NotNull PsiElement<?>[] elements, @NotNull PsiElement<?> condition, int lineNumber) {
        super(elements, lineNumber);
        this.condition = condition;

        if (condition.isPreComputed()) {
            //TODO warning
        }
    }

    /**
     * Sets the section which should get executed if the condition is not met.
     * Can only be called once on a single instance.
     *
     * @param elseSection the section to execute when the condition is not met
     * @since 0.1.0
     */
    public void setElseSection(@NotNull PsiSection elseSection) {
        if (this.elseSection != null) {
            throw new IllegalStateException("An else section has already been set for this if section.");
        }

        this.elseSection = elseSection;
    }

    @Nullable
    @Override
    protected ExitSectionsPointerMovement executeImpl(@Nullable Context context) {
        PsiElement<?>[] elements;

        if (condition.execute(context, Boolean.class)) {
            elements = this.elements;
        } else if (elseSection != null) {
            elements = elseSection.getElements();
        } else {
            return null;
        }

        for (PsiElement<?> element : elements) {
            Object result = element.execute(context);

            if (result == Boolean.FALSE) {
                break;
            }

            if (result instanceof ExitSectionsPointerMovement) {
                ExitSectionsPointerMovement pointerMovement = (ExitSectionsPointerMovement) result;
                ExitSectionsPointerMovement.Type type = pointerMovement.getType();

                if (type == ExitSectionsPointerMovement.Type.LOOPS) {
                    throw new ExecutionException("Tried to exit loop, but found a conditional", lineNumber);
                }

                Integer amount = pointerMovement.getAmount();

                if (amount == null) {
                    return new ExitSectionsPointerMovement(type);
                }

                if (amount > 1) {
                    return new ExitSectionsPointerMovement(type, amount - 1);
                }

                return null;
            }
        }

        return null;
    }

    /**
     * A factory for creating if sections.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiSectionFactory<PsiIf> {

        /**
         * The pattern to parse if sections with.
         */
        @NotNull
        private final Pattern pattern = Pattern.compile("if (?<statement>[\\s\\S]+)");

        @Nullable
        @Contract(pure = true)
        @Override
        public PsiIf tryParse(@NotNull SkriptLoader skriptLoader, @NotNull String text,
            @NotNull Supplier<PsiElement<?>[]> elementsSupplier, int lineNumber) {
            Matcher matcher = pattern.matcher(text);
            return matcher.matches()
                ? create(elementsSupplier.get(), skriptLoader
                    .forceParseElement(matcher.group("statement"), lineNumber), lineNumber)
                : null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the
         * {@link #tryParse(SkriptLoader, String, Supplier, int)} method.
         *
         * @param elements the elements for the section
         * @param condition the condition
         * @param lineNumber the line number
         * @return the section
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        protected PsiIf create(@NotNull PsiElement<?>[] elements, @NotNull PsiElement<?> condition, int lineNumber) {
            return new PsiIf(elements, condition, lineNumber);
        }
    }
}

package com.github.stefvanschie.quickskript.core.psi.section;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiSection;
import com.github.stefvanschie.quickskript.core.psi.PsiSectionFactory;
import com.github.stefvanschie.quickskript.core.psi.util.SimpleInstructionPointerMovement;
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
 * @since 0.1.0
 */
public class PsiIf extends PsiSection {

    /**
     * The condition of the execution.
     */
    private PsiElement<?> condition;

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
            preComputed = executeImpl(null);
            this.condition = null;
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

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        if (condition.execute(context, Boolean.class)) {
            for (PsiElement<?> element : elements) {
                Object result = element.execute(context);

                if (result == Boolean.FALSE) {
                    break;
                } else if (result == SimpleInstructionPointerMovement.Loop.CONTINUE) {
                    return null; //the loop is apparently outside of this if statement, so completely exit it
                }
            }
        } else if (elseSection != null) {
            elseSection.execute(context);
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
        private final Pattern pattern = Pattern.compile("if ([\\s\\S]+)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiIf tryParse(@NotNull String text, @NotNull Supplier<PsiElement<?>[]> elementsSupplier, int lineNumber) {
            Matcher matcher = pattern.matcher(text);
            return matcher.matches()
                    ? create(elementsSupplier.get(), SkriptLoader.get()
                    .forceParseElement(matcher.group(1), lineNumber), lineNumber)
                    : null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the
         * {@link #tryParse(String, Supplier, int)} method.
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

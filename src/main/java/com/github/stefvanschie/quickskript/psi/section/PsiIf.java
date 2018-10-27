package com.github.stefvanschie.quickskript.psi.section;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiSection;
import com.github.stefvanschie.quickskript.psi.PsiSectionFactory;
import com.github.stefvanschie.quickskript.skript.SkriptLoader;
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
    @NotNull
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
        if (this.elseSection != null)
            throw new IllegalStateException("An else section has already been set for this if section.");

        this.elseSection = elseSection;
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        if (condition.execute(context, Boolean.class)) {
            super.executeImpl(context);
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
        private final Pattern pattern = Pattern.compile("if ([\\s\\S]+)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiIf tryParse(@NotNull String text, @NotNull Supplier<PsiElement<?>[]> elementsSupplier, int lineNumber) {
            Matcher matcher = pattern.matcher(text);
            return matcher.matches()
                    ? new PsiIf(elementsSupplier.get(), SkriptLoader.get()
                    .forceParseElement(matcher.group(1), lineNumber), lineNumber)
                    : null;
        }
    }
}

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
 * A section which continuously executes its contained elements while the condition is met.
 *
 * @since 0.1.0
 */
public class PsiWhile extends PsiSection {

    /**
     * The condition of the continuing execution.
     */
    private PsiElement<?> condition;

    /**
     * Creates a new psi while section.
     *
     * @param elements the elements this section should contain
     * @param condition the condition of the continuing execution
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiWhile(@NotNull PsiElement<?>[] elements, @NotNull PsiElement<?> condition, int lineNumber) {
        super(elements, lineNumber);

        this.condition = condition;

        if (condition.isPreComputed()) {
            //TODO warning
        }
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        outerLoop:
        while (condition.execute(context, Boolean.class)) {
            for (PsiElement<?> element : elements) {
                Object result = element.execute(context);

                if (result == Boolean.FALSE) {
                    break;
                } else if (result == SimpleInstructionPointerMovement.Loop.CONTINUE) {
                    continue outerLoop;
                }
            }
        }

        return null;
    }

    /**
     * A factory for creating while sections.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiSectionFactory<PsiWhile> {

        /**
         * The pattern to parse while sections with.
         */
        @NotNull
        private final Pattern pattern = Pattern.compile("while ([\\s\\S]+)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiWhile tryParse(@NotNull String text, @NotNull Supplier<PsiElement<?>[]> elementsSupplier,
                                 int lineNumber) {
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
         * @param elements the elements for thiks section
         * @param condition the condition
         * @param lineNumber the line number
         * @return the section
         */
        @NotNull
        @Contract(pure = true)
        protected PsiWhile create(@NotNull PsiElement<?>[] elements, @NotNull PsiElement<?> condition, int lineNumber) {
            return new PsiWhile(elements, condition, lineNumber);
        }
    }
}

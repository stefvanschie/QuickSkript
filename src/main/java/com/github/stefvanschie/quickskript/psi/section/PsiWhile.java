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
 * A section which continuously executes its contained elements while the condition is met.
 *
 * @since 0.1.0
 */
public class PsiWhile extends PsiSection {

    /**
     * The condition of the continuing execution.
     */
    @NotNull
    private final PsiElement<?> condition;

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
        while (condition.execute(context, Boolean.class)) {
            super.executeImpl(context);
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
        private final Pattern pattern = Pattern.compile("while ([\\s\\S]+)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiWhile tryParse(@NotNull String text, @NotNull Supplier<PsiElement<?>[]> elementsSupplier, int lineNumber) {
            Matcher matcher = pattern.matcher(text);
            return matcher.matches()
                    ? new PsiWhile(elementsSupplier.get(), SkriptLoader.get()
                    .forceParseElement(matcher.group(1), lineNumber), lineNumber)
                    : null;
        }
    }
}

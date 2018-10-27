package com.github.stefvanschie.quickskript.psi;

import com.github.stefvanschie.quickskript.context.Context;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * An abstract representation of a section in a skript file.
 * Other sections should extend upon the functionality implemented in this class:
 * it executes all contained elements in order until {@link Boolean#FALSE} is returned.
 *
 * @since 0.1.0
 */
public abstract class PsiSection extends PsiElement<Void> {

    /**
     * The elements this section contains.
     */
    @NotNull
    protected final PsiElement<?>[] elements;

    /**
     * Creates a new section with the specified contained elements and line number.
     *
     * @param elements the elements this section should contain
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiSection(@NotNull PsiElement<?>[] elements, int lineNumber) {
        super(lineNumber);
        this.elements = elements;

        if (Arrays.stream(elements).anyMatch(element -> element.isPreComputed()
                && element.execute(null) instanceof Boolean)) {
            //TODO warning
        }
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        for (PsiElement<?> element : elements) {
            if (element.execute(context) == Boolean.FALSE)
                break;
        }
        return null;
    }
}

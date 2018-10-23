package com.github.stefvanschie.quickskript.psi.section;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class PsiSection extends PsiElement<Void> {

    @NotNull
    private final PsiElement[] elements;

    public PsiSection(@NotNull PsiElement[] elements, int lineNumber) {
        super(lineNumber);
        this.elements = elements;

        if (Arrays.stream(elements).anyMatch(element -> element.isPreComputed()
                && element.execute(null) instanceof Boolean)) {
            //TODO warning
        }
    }


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

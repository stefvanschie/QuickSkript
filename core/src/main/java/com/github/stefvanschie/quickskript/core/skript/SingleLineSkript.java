package com.github.stefvanschie.quickskript.core.skript;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SingleLineSkript implements Skript {

    private final PsiElement<?> element;

    public SingleLineSkript(@NotNull String input) {
        element = SkriptLoader.get().tryParseElement(input, 1);
    }

    @NotNull
    @Override
    public String getName() {
        return "SingleLineSkript";
    }

    @Nullable
    public PsiElement<?> getParsedElement() {
        return element;
    }

    @Nullable
    public Object execute(@NotNull Context context) {
        return element == null ? null : element.execute(context);
    }
}

package com.github.stefvanschie.quickskript.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public interface PsiSectionFactory<T extends PsiSection> {
    @Nullable
    T tryParse(@NotNull String text, @NotNull Supplier<PsiElement<?>[]> elementsSupplier, int lineNumber);
}

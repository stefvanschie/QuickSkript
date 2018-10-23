package com.github.stefvanschie.quickskript.psi.section;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PsiWhile extends PsiElement<Void> {

    @NotNull
    private final PsiElement<?> condition;

    @NotNull
    private final PsiSection content;

    public PsiWhile(@NotNull PsiElement<?> condition, @NotNull PsiSection content, int lineNumber) {
        super(lineNumber);
        this.condition = condition;
        this.content = content;

        if (condition.isPreComputed()) {
            //TODO warning
        }
    }

    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        while (condition.execute(context, Boolean.class)) {
            content.execute(context);
        }
        return null;
    }
}

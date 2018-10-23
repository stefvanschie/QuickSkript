package com.github.stefvanschie.quickskript.psi.section;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PsiIf extends PsiElement<Void> {

    @NotNull
    private final PsiElement<?> condition;

    @NotNull
    private final PsiSection content;

    @Nullable
    private final PsiElement<?> elseElement;

    public PsiIf(@NotNull PsiElement<?> condition, @NotNull PsiSection content,
            @NotNull PsiIf elseIf, int lineNumber) {
        this(condition, content, lineNumber, elseIf);
    }

    public PsiIf(@NotNull PsiElement<?> condition, @NotNull PsiSection content,
            @NotNull PsiSection elseSection, int lineNumber) {
        this(condition, content, lineNumber, elseSection);
    }

    public PsiIf(@NotNull PsiElement<?> condition, @NotNull PsiSection content, int lineNumber) {
        this(condition, content, lineNumber, null);
    }

    private PsiIf(@NotNull PsiElement<?> condition, @NotNull PsiSection content,
            int lineNumber, @Nullable PsiElement<?> elseElement) {
        super(lineNumber);
        this.condition = condition;
        this.content = content;
        this.elseElement = elseElement;

        if (condition.isPreComputed()) {
            //TODO warning
        }
    }


    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        if (condition.execute(context, Boolean.class)) {
            content.executeImpl(context);
        } else if (elseElement != null) {
            elseElement.execute(context);
        }
        return null;
    }
}

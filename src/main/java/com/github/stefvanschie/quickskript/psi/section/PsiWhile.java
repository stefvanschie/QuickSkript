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

public class PsiWhile extends PsiSection {

    @NotNull
    private final PsiElement<?> condition;

    private PsiWhile(@NotNull PsiElement<?>[] elements, @NotNull PsiElement<?> condition, int lineNumber) {
        super(elements, lineNumber);
        this.condition = condition;

        if (condition.isPreComputed()) {
            //TODO warning
        }
    }

    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        while (condition.execute(context, Boolean.class)) {
            super.executeImpl(context);
        }
        return null;
    }

    public static class Factory implements PsiSectionFactory<PsiWhile> {

        private final Pattern pattern = Pattern.compile("while ([\\s\\S]+)");

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

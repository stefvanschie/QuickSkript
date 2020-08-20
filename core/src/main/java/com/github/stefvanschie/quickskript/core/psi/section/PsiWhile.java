package com.github.stefvanschie.quickskript.core.psi.section;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiSection;
import com.github.stefvanschie.quickskript.core.psi.PsiSectionFactory;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.util.pointermovement.ExitSectionsPointerMovement;
import com.github.stefvanschie.quickskript.core.psi.util.pointermovement.SimpleInstructionPointerMovement;
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

    @Nullable
    @Override
    protected ExitSectionsPointerMovement executeImpl(@Nullable Context context) {
        outerLoop:
        while (condition.execute(context, Boolean.class)) {
            for (PsiElement<?> element : elements) {
                Object result = element.execute(context);

                if (result == Boolean.FALSE) {
                    break;
                }

                if (result == SimpleInstructionPointerMovement.Loop.CONTINUE) {
                    continue outerLoop;
                }

                if (result instanceof ExitSectionsPointerMovement) {
                    ExitSectionsPointerMovement pointerMovement = (ExitSectionsPointerMovement) result;
                    ExitSectionsPointerMovement.Type type = pointerMovement.getType();

                    if (type == ExitSectionsPointerMovement.Type.CONDITIONALS) {
                        throw new ExecutionException("Tried to exit conditional, but found a loop", lineNumber);
                    }

                    Integer amount = pointerMovement.getAmount();

                    if (amount == null) {
                        return new ExitSectionsPointerMovement(type);
                    }

                    if (amount > 1) {
                        return new ExitSectionsPointerMovement(type, amount - 1);
                    }

                    return null;
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
        private final Pattern pattern = Pattern.compile("while (?<statement>[\\s\\S]+)");

        @Nullable
        @Contract(pure = true)
        @Override
        public PsiWhile tryParse(@NotNull SkriptLoader skriptLoader, @NotNull String text,
            @NotNull Supplier<PsiElement<?>[]> elementsSupplier, int lineNumber) {
            Matcher matcher = pattern.matcher(text);
            return matcher.matches()
                    ? create(elementsSupplier.get(), skriptLoader
                    .forceParseElement(matcher.group("statement"), lineNumber), lineNumber)
                    : null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the
         * {@link #tryParse(SkriptLoader, String, Supplier, int)} method.
         *
         * @param elements the elements for this section
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

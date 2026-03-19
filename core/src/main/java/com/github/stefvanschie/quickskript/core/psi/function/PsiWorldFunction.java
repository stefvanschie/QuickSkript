package com.github.stefvanschie.quickskript.core.psi.function;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import com.github.stefvanschie.quickskript.core.util.literal.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets a world from a given string. This will never be pre computed, since there is no guarantee that any world
 * management plugin is loaded when this plugin is enabled.
 *
 * @since 0.1.0
 */
public class PsiWorldFunction extends PsiElement<World> {

    /**
     * The parameter for getting the world
     */
    protected final PsiElement<?> parameter;

    /**
     * Creates a new world function
     *
     * @param parameter the parameter
     * @param lineNumber the line number
     * @since 0.1.0
     */
    protected PsiWorldFunction(@NotNull PsiElement<?> parameter, int lineNumber) {
        super(lineNumber);

        this.parameter = parameter;
    }

    /**
     * A factory for creating world functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * This gets called upon parsing
         *
         * @param name the name of the world
         * @param lineNumber the line number
         * @return the function, or null to indicate failure
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Pattern("world\\(%text%\\)")
        public PsiWorldFunction tryParse(@NotNull PsiElement<?> name, int lineNumber) {
            return create(name, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param element the element to compute
         * @param lineNumber the line number
         * @return the function
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        protected PsiWorldFunction create(@NotNull PsiElement<?> element, int lineNumber) {
            return new PsiWorldFunction(element, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.WORLD;
        }
    }
}

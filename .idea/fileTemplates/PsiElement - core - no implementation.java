package ${PACKAGE_NAME};

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * TODO documentation here
 * TODO StandaloneSkriptLoader: registerElement(new ${NAME}.Factory());
 *
 * @since 0.1.0
 */
public class ${NAME} extends PsiElement<${TYPE}> {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected ${NAME}(#if($PARAMETERS_DECLARATION != "") ${PARAMETERS_DECLARATION}, #end int lineNumber) {
        super(lineNumber);
    }

    /**
     * A factory for creating {@link ${NAME}}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link ${NAME}}s
         */
        @NotNull
        private SkriptPattern pattern = SkriptPattern.parse("${PATTERN}");

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public ${NAME} parse(#if($PARAMETERS_DECLARATION != "") ${PARAMETERS_DECLARATION}, #end int lineNumber) {
            return create(#if($PARAMETERS_VARS != "") ${PARAMETERS_VARS}, #end lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public ${NAME} create(#if($PARAMETERS_DECLARATION != "") ${PARAMETERS_DECLARATION}, #end int lineNumber) {
            return new ${NAME}(#if($PARAMETERS_VARS != "") ${PARAMETERS_VARS}, #end lineNumber);
        }
    }
}

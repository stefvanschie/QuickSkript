package ${PACKAGE_NAME};

import com.github.stefvanschie.quickskript.core.context.Context;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * TODO documentation here
 * TODO BukkitSkriptLoader: registerElement(new ${NAME}.Factory());
 *
 * @since 0.1.0
 */
public class ${NAME} extends ${BASE_NAME} {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private ${NAME}(#if($PARAMETERS_DECLARATION != "") ${PARAMETERS_DECLARATION}, #end int lineNumber) {
        super(#if($PARAMETERS_VARS != "") ${PARAMETERS_VARS}, #end lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected ${TYPE} executeImpl(@Nullable Context context) {
        //TODO implementation
    }

    /**
     * A factory for creating {@link ${NAME}}s
     *
     * @since 0.1.0
     */
    public static class Factory extends ${BASE_NAME}.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public ${BASE_NAME} create(#if($PARAMETERS_DECLARATION != "") ${PARAMETERS_DECLARATION}, #end int lineNumber) {
            return new ${NAME}(#if($PARAMETERS_VARS != "") ${PARAMETERS_VARS}, #end lineNumber);
        }
    }
}

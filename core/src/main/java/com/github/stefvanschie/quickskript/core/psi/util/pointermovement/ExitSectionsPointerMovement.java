package com.github.stefvanschie.quickskript.core.psi.util.pointermovement;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Specifies that the code should exit a certain amount of conditionals or loops.
 *
 * @since 0.1.0
 */
public class ExitSectionsPointerMovement {

    /**
     * The type of section that should be exited
     */
    @NotNull
    private final Type type;

    /**
     * The amount of sections that should be exited from
     */
    @Nullable
    private Integer amount;

    /**
     * Creates a new instance which will exit everything
     *
     * @since 0.1.0
     */
    public ExitSectionsPointerMovement() {
        this(Type.EVERYTHING);
    }

    /**
     * Creates a new instance which will exit the specified section type(s)
     *
     * @param type the type of section to stop, see {@link #type}
     * @since 0.1.0
     */
    public ExitSectionsPointerMovement(@NotNull Type type) {
        this.type = type;
    }

    /**
     * Creates a new instance which will exit the specified section type(s) for the amount of
     * layers specified
     *
     * @param type the type of section(s) to exit from
     * @param amount the amount of layers to exit from
     * @since 0.1.0
     */
    public ExitSectionsPointerMovement(@NotNull Type type, int amount) {
        this(type);

        this.amount = amount;
    }

    /**
     * The amount of layers which should be exited from. Null if every layer should be exited from.
     *
     * @return the amount of layers or null
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public Integer getAmount() {
        return amount;
    }

    /**
     * Gets the type(s) of sections which should be exited from
     *
     * @return the section type(s)
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Type getType() {
        return type;
    }

    /**
     * The type of sections you can stop from
     *
     * @since 0.1.0
     */
    public enum Type {

        /**
         * Indicates that every section should be exited from
         *
         * @since 0.1.0
         */
        EVERYTHING,

        /**
         * Indicates that only loops should be exited from
         *
         * @since 0.1.0
         */
        LOOPS,

        /**
         * Indicates that only conditionals should be exited from
         *
         * @since 0.1.0
         */
        CONDITIONALS;

        /**
         * Gets the type by name. 'Section' refers to {@link #EVERYTHING}, 'loops' to {@link #LOOPS} and 'conditional'
         * to {@link #CONDITIONALS}.
         *
         * @param name the name to match against
         * @return the type, or null if the type couldn't be found
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        public static Type byName(@NotNull String name) {
            if (name.equalsIgnoreCase("section")) {
                return EVERYTHING;
            }

            if (name.equalsIgnoreCase("loop")) {
                return LOOPS;
            }

            if (name.equalsIgnoreCase("conditional")) {
                return CONDITIONALS;
            }

            return null;
        }
    }
}

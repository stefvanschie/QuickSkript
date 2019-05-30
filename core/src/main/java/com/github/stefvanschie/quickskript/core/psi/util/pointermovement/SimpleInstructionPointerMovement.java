package com.github.stefvanschie.quickskript.core.psi.util.pointermovement;

/**
 * An enum for indicating that the instruction pointer has to stop following its default execution order and move to a
 * different instruction, to then continue execution from there.
 *
 * @since 0.1.0
 */
public class SimpleInstructionPointerMovement {

    /**
     * Instruction pointer movement for loops
     *
     * @since 0.1.0
     */
    public enum Loop {

        /**
         * Continue indicates that the loop has to stop the current execution order and return back to the first
         * execution, while also executing any code the loop would normally do at the start of a new loop (e.g. checking
         * conditions, executing statements, etc.)
         *
         * @since 0.1.0
         */
        CONTINUE
    }
}

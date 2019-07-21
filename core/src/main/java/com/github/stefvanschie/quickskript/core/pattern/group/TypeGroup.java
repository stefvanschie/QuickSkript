package com.github.stefvanschie.quickskript.core.pattern.group;

import com.github.stefvanschie.quickskript.core.pattern.exception.SkriptPatternParseException;
import com.github.stefvanschie.quickskript.core.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Has to match a certain type of elements. Since patterns and the PSI are separated, this will assume that the match is
 * a valid expression for the PSI.
 *
 * @since 0.1.0
 */
public class TypeGroup implements SkriptPatternGroup {

    /**
     * The constraint of this type
     *
     * @since 0.1.0
     */
    @NotNull
    private final Constraint constraint;

    /**
     * The type this group should match
     */
    @NotNull
    private final String type;

    /**
     * Creates a new type group
     *
     * @param type the type to match, see {@link #type}
     * @param constraint the type's constraint,s ee {@link #constraint}
     * @since 0.1.0
     */
    private TypeGroup(@NotNull String type, @NotNull Constraint constraint) {
        this.type = type;
        this.constraint = constraint;
    }

    /**
     * Gets the constraint of this type group
     *
     * @return the constraint
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Constraint getConstraint() {
        return constraint;
    }

    /**
     * Gets the type of this type group
     *
     * @return the type
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public String getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Contract(pure = true)
    @Override
    public List<SkriptPatternGroup> getChildren() {
        return new ArrayList<>(0);
    }

    /**
     * Parses the group at the starting of the match, returning the correct group if it matches correctly. This will
     * only match if the match is at the start and continuous i.e. there are no gaps in the match.
     *
     * @param input the input to match against
     * @return the created group and the input stripped with the match if successful, null if the match was
     *         unsuccessful.
     */
    @Nullable
    public static Pair<TypeGroup, String> parseStarting(@NotNull String input) {
        if (input.charAt(0) != '%') {
            return null;
        }

        input = input.substring(1);

        int endCharacter = input.indexOf('%');

        if (endCharacter == -1) {
            throw new SkriptPatternParseException(
                "Expected an ending type group character '%' at index " + input.length() + " but wasn't found"
            );
        }

        var constraint = Constraint.ALL;

        switch (input.charAt(1)) {
            case '*':
                constraint = Constraint.LITERAL;
                input = input.substring(1);
                break;
            case '~':
                constraint = Constraint.NOT_LITERAL;
                input = input.substring(1);
                break;
            case '^':
                constraint = Constraint.VARIABLE;
                input = input.substring(1);
                break;
            case '-':
                constraint = Constraint.NULL_IF_ABSENT;
                input = input.substring(1);
                break;
        }

        return new Pair<>(new TypeGroup(input.substring(0, endCharacter), constraint), input.substring(endCharacter + 1));
    }

    /**
     * An enum defining different constraints types may have
     *
     * @since 0.1.0
     */
    public enum Constraint {
        ALL,
        LITERAL,
        NOT_LITERAL,
        VARIABLE,
        NULL_IF_ABSENT
    }
}

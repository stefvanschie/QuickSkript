package com.github.stefvanschie.quickskript.core.pattern.group;

import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.exception.SkriptPatternParseException;
import com.github.stefvanschie.quickskript.core.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    @NotNull
    @Contract(pure = true)
    @Override
    public List<SkriptMatchResult> match(@NotNull SkriptPatternGroup[] followingGroups, @NotNull String input) {
        List<SkriptMatchResult> results = new ArrayList<>();
        StringBuilder builder = new StringBuilder(input);

        do {
            String subInput = builder.toString();
            int length = builder.length();

            if (followingGroups.length == 0) {
                SkriptMatchResult result = new SkriptMatchResult();
                result.addMatchedGroup(this, subInput, 0);
                result.setRestingString(input.substring(length));

                results.add(result);
            } else {
                SkriptPatternGroup[] newArray = Arrays.copyOfRange(followingGroups, 1, followingGroups.length);
                List<SkriptMatchResult> calleeResults = followingGroups[0].match(newArray, input.substring(length));

                calleeResults.forEach(result -> result.addMatchedGroup(this, subInput, 0));

                results.addAll(calleeResults);
            }

            //in case we get an empty string passed
            if (builder.length() > 0) {
                builder.deleteCharAt(length - 1);
            }
        } while (builder.length() > 0);

        return results;
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

    @NotNull
    @Contract(pure = true)
    @Override
    public List<SkriptPatternGroup> getChildren() {
        return Collections.emptyList();
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

        Constraint constraint = Constraint.ALL;

        switch (input.charAt(0)) {
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

        int endCharacter = input.indexOf('%');

        if (endCharacter == -1) {
            throw new SkriptPatternParseException(
                "Expected an ending type group character '%' at index " + input.length() + " but wasn't found"
            );
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

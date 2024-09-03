package com.github.stefvanschie.quickskript.core.pattern.group;

import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.exception.SkriptPatternInvalidGroupException;
import com.github.stefvanschie.quickskript.core.pattern.exception.SkriptPatternParseException;
import com.github.stefvanschie.quickskript.core.util.Pair;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

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
     * The types this group should match
     */
    @NotNull
    private final Type[] types;

    /**
     * Creates a new type group
     *
     * @param types the types to match, see {@link #types}
     * @param constraint the type's constraint,s ee {@link #constraint}
     * @since 0.1.0
     */
    private TypeGroup(@NotNull Type[] types, @NotNull Constraint constraint) {
        this.types = types;
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
     * Gets the types of this type group
     *
     * @return the types
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Type[] getTypes() {
        return types;
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public List<SkriptPatternGroup> getChildren() {
        return Collections.emptyList();
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public <T extends SkriptPatternGroup> List<T> getGroups(Class<T> groupClass) {
        if (getClass() == groupClass) {
            return Collections.singletonList((T) this);
        }

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
    public static Pair<TypeGroup, StringBuilder> parseStarting(@NotNull StringBuilder input) {
        if (input.charAt(0) != '%') {
            return null;
        }

        input.deleteCharAt(0);

        Constraint constraint = Constraint.ALL;

        switch (input.charAt(0)) {
            case '*':
                constraint = Constraint.LITERAL;
                input.deleteCharAt(0);
                break;
            case '~':
                constraint = Constraint.NOT_LITERAL;
                input.deleteCharAt(0);
                break;
            case '^':
                constraint = Constraint.VARIABLE;
                input.deleteCharAt(0);
                break;
            case '-':
                constraint = Constraint.NULL_IF_ABSENT;
                input.deleteCharAt(0);
                break;
        }

        int endCharacter = input.indexOf("%");

        if (endCharacter == -1) {
            throw new SkriptPatternParseException(
                "Expected an ending type group character '%' at index " + input.length() + " but wasn't found"
            );
        }

        String[] textTypes = input.substring(0, endCharacter).split("/");
        Type[] types = new Type[textTypes.length];

        for (int index = 0; index < textTypes.length; index++) {
            String textType = textTypes[index];
            Type type = Type.byName(textType);

            if (type == null) {
                throw new SkriptPatternParseException("Type '" + textType + "' is not a valid type");
            }

            types[index] = type;
        }

        return new Pair<>(new TypeGroup(types, constraint), input.delete(0, endCharacter + 1));
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public Collection<String> unrollFully(@NotNull SkriptPatternGroup @NotNull [] groups) {
        throw new SkriptPatternInvalidGroupException("Type group cannot be unrolled");
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        TypeGroup typeGroup = (TypeGroup) object;

        return constraint == typeGroup.constraint && Arrays.equals(types, typeGroup.types);
    }

    @Override
    public int hashCode() {
        return Objects.hash(constraint, types);
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

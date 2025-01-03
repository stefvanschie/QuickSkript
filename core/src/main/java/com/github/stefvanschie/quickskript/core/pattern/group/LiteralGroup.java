package com.github.stefvanschie.quickskript.core.pattern.group;

import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * A literal is a piece of text, without spaces, that should be directly and exactly matched, before matching is
 * successful.
 *
 * @since 0.1.0
 */
public class LiteralGroup implements SkriptPatternGroup {

    /**
     * The text inside this literal
     */
    @NotNull
    private final String text;

    /**
     * Creates a new literal group
     *
     * @since 0.1.0
     */
    private LiteralGroup(@NotNull String text) {
        this.text = text;
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public List<SkriptMatchResult> match(@NotNull SkriptPatternGroup[] followingGroups, @NotNull String input) {
        if (!input.startsWith(text)) {
            return new ArrayList<>(0);
        }

        if (followingGroups.length == 0) {
            SkriptMatchResult result = new SkriptMatchResult();
            result.addMatchedGroup(this, text, 0);
            result.setRestingString(input.substring(text.length()));

            return Collections.singletonList(result);
        }

        SkriptPatternGroup[] newArray = Arrays.copyOfRange(followingGroups, 1, followingGroups.length);
        List<SkriptMatchResult> results = followingGroups[0].match(newArray, input.substring(text.length()));

        results.forEach(result -> result.addMatchedGroup(this, text, 0));

        return results;
    }

    /**
     * Gets the text this literal has to match
     *
     * @return the text
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public String getText() {
        return text;
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
    public static Pair<LiteralGroup, StringBuilder> parseStarting(@NotNull StringBuilder input) {
        int index = 0;
        int escapesHit = 0;
        char previousChar = '\0';
        char matchingChar = input.charAt(0);

        //noinspection HardcodedFileSeparator
        while ((matchingChar != ' ' && matchingChar != '%' && matchingChar != '[' && matchingChar != '('
            && matchingChar != '<') || previousChar == '\\') {
            index++;

            //noinspection HardcodedFileSeparator
            if (previousChar == '\\' && (matchingChar == ' ' || matchingChar == '%' || matchingChar == '[' ||
                matchingChar == '(' || matchingChar == '<' || matchingChar == ']' || matchingChar == ')' ||
                matchingChar == '>')) {
                escapesHit++; //we need to remove the escaped characters later, so keep in mind how many we have matched
            }

            if (index >= input.length()) {
                break;
            }

            previousChar = matchingChar;
            matchingChar = input.charAt(index);
        }

        if (index == 0) {
            return null;
        }

        //remove escape characters
        StringBuilder builder = new StringBuilder(input.substring(0, index));
        int escapeIndex = builder.indexOf("\\");

        while (escapeIndex != -1 && escapeIndex != builder.length() - 1) {
            char escapedChar = builder.charAt(escapeIndex + 1);

            if (escapedChar == '[' || escapedChar == ']' || escapedChar == '<' || escapedChar == '>' || escapedChar == '%' || escapedChar == '(' || escapedChar == ')') {
                builder.deleteCharAt(escapeIndex);
            }

            escapeIndex = builder.indexOf("\\", escapeIndex + 1);
        }

        builder.append(input.substring(index));

        LiteralGroup literalGroup = new LiteralGroup(builder.substring(0, index - escapesHit));

        return new Pair<>(literalGroup, builder.delete(0, index - escapesHit));
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public Collection<String> unrollFully(@NotNull SkriptPatternGroup @NotNull [] groups) {
        if (groups.length == 0) {
            return Collections.singleton(getText());
        }

        int index = 0;

        Collection<String> matches = new HashSet<>();

        matches.add(getText());

        Collection<String> newMatches = new HashSet<>();
        Collection<String> strings;

        if (groups[0] instanceof OptionalGroup) {
            strings = groups[0].unrollFully(new SkriptPatternGroup[0]);

            for (String match : matches) {
                for (String string : strings) {
                    newMatches.add(match + string);
                }
            }

            if (groups.length == 1) {
                return newMatches;
            }

            matches = new HashSet<>(newMatches);
            newMatches.clear();
            index++;
        }

        strings = groups[index].unrollFully(Arrays.copyOfRange(groups, index + 1, groups.length));

        for (String match : matches) {
            for (String string : strings) {
                newMatches.add(match + string);
            }
        }

        return newMatches;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        LiteralGroup literalGroup = (LiteralGroup) object;

        return text.equals(literalGroup.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }
}

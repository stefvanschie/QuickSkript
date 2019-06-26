package com.github.stefvanschie.quickskript.core.pattern.group;

import com.github.stefvanschie.quickskript.core.pattern.exception.SkriptPatternParseException;
import com.github.stefvanschie.quickskript.core.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

/**
 * A group that matches according to a regular expression. This group is greedy: it will match as much as possible as
 * long as the match is still valid.
 *
 * @since 0.1.0
 */
public class RegexGroup extends SkriptPatternGroup {

    /**
     * The pattern of this regex group
     */
    @NotNull
    private Pattern pattern;

    /**
     * Creates a new regex group
     *
     * @param pattern the pattern to match
     * @since 0.1.0
     */
    private RegexGroup(@NotNull String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    /**
     * Gets the pattern this group holds
     *
     * @return the pattern
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Pattern getPattern() {
        return pattern;
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
    public static Pair<RegexGroup, String> parseStarting(@NotNull String input) {
        if (!input.startsWith("<")) {
            return null;
        }

        int endCharacter = input.indexOf('>');

        if (endCharacter == -1) {
            throw new SkriptPatternParseException(
                "Expected an ending regex group character '>' at index " + input.length() + " but wasn't found"
            );
        }

        return new Pair<>(new RegexGroup(input.substring(1, endCharacter)), input.substring(endCharacter + 1));
    }
}

package com.github.stefvanschie.quickskript.core.pattern.group;

import com.github.stefvanschie.quickskript.core.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A literal is a piece of text, without spaces, that should be directly and exactly matched, before matching is
 * successful.
 *
 * @since 0.1.0
 */
public class LiteralGroup extends SkriptPatternGroup {

    /**
     * The text inside this literal
     */
    @NotNull
    private String text;

    /**
     * Creates a new literal group
     *
     * @since 0.1.0
     */
    private LiteralGroup(@NotNull String text) {
        this.text = text;
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

    /**
     * Parses the group at the starting of the match, returning the correct group if it matches correctly. This will
     * only match if the match is at the start and continuous i.e. there are no gaps in the match.
     *
     * @param input the input to match against
     * @return the created group and the input stripped with the match if successful, null if the match was
     *         unsuccessful.
     */
    @Nullable
    public static Pair<LiteralGroup, String> parseStarting(@NotNull String input) {
        int index = 0;
        char matchingChar = input.charAt(0);

        while (matchingChar != ' ' && matchingChar != '%' && matchingChar != '[' && matchingChar != '('
            && matchingChar != '<') {
            index++;

            if (index >= input.length()) {
                break;
            }

            matchingChar = input.charAt(index);
        }

        if (index == 0) {
            return null;
        }

        return new Pair<>(new LiteralGroup(input.substring(0, index)), input.substring(index));
    }
}

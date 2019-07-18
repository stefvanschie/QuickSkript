package com.github.stefvanschie.quickskript.core.pattern.group;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.pattern.exception.SkriptPatternParseException;
import com.github.stefvanschie.quickskript.core.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * A group that marks possible choices.
 *
 * @since 0.1.0
 */
public class ChoiceGroup implements SkriptPatternGroup {

    /**
     * The different choices
     */
    @NotNull
    private SkriptPattern[] patterns;

    /**
     * The parse marks assigned to each individual choice. 0 if no parse mark was explicitly assigned.
     */
    @NotNull
    private int[] parseMarks;

    /**
     * Creates an optional group
     *
     * @param patterns the patterns inside this optional group
     * @param parseMarks the parse marks for each pattern
     * @since 0.1.0
     */
    private ChoiceGroup(@NotNull SkriptPattern[] patterns, @NotNull int[] parseMarks) {
        this.patterns = patterns;
        this.parseMarks = parseMarks;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Contract(pure = true)
    @Override
    public List<SkriptPatternGroup> getChildren() {
        var groups = new ArrayList<SkriptPatternGroup>();

        for (SkriptPattern pattern : getPatterns()) {
            groups.addAll(pattern.getGroups());
        }

        return groups;
    }

    /**
     * Gets the possible choices as skript patterns
     *
     * @return the choices
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public SkriptPattern[] getPatterns() {
        return patterns;
    }

    /**
     * Gets the parse marks for each choice
     *
     * @return the parse marks
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public int[] getParseMarks() {
        return parseMarks;
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
    public static Pair<ChoiceGroup, String> parseStarting(@NotNull String input) {
        if (input.charAt(0) != '(') {
            return null;
        }

        input = input.substring(1);

        int openingOptionals = 0;
        int openingChoices = 1;

        int currentParseMark = 0;
        int groupStartIndex = 0;

        List<SkriptPattern> patterns = new ArrayList<>();
        List<Integer> parseMarks = new ArrayList<>();

        int lastIndex;

        for (int index = 0; ; index++) {
            if (index == input.length()) {
                throw new SkriptPatternParseException(
                    "Expected an ending optional group character ')' at index " + input.length() + " but wasn't found"
                );
            }

            char character = input.charAt(index);

            if (character == ']') {
                openingOptionals--;
            } else if (character == '[') {
                openingOptionals++;
            } else if (character == '(') {
                openingChoices++;
            } else if (character == ')') {
                openingChoices--;

                if (openingChoices == 0) {
                    patterns.add(SkriptPattern.parse(input.substring(groupStartIndex, index)));
                    parseMarks.add(currentParseMark);

                    lastIndex = index;

                    break;
                }
            }

            if (openingOptionals == 0 && openingChoices == 1) {
                if (character == '|') {
                    patterns.add(SkriptPattern.parse(input.substring(groupStartIndex, index)));
                    parseMarks.add(currentParseMark);

                    currentParseMark = 0;
                    groupStartIndex = index + 1;
                } else if (character == '\u00A6') { //broken bar character
                    String parseMarkString = input.substring(groupStartIndex, index);

                    if (!parseMarkString.matches("-?\\d+")) {
                        throw new SkriptPatternParseException(
                            "Parse mark needs to be an integer, but '" + parseMarkString + "' does not adhere to this"
                        );
                    }

                    currentParseMark = Integer.parseInt(parseMarkString);
                    groupStartIndex = index + 1;
                }
            }
        }

        int[] parseMarkArray = parseMarks.stream().mapToInt(x -> x).toArray();
        ChoiceGroup choiceGroup = new ChoiceGroup(patterns.toArray(new SkriptPattern[0]), parseMarkArray);

        return new Pair<>(choiceGroup, input.substring(lastIndex + 1));
    }
}

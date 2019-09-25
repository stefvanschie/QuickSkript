package com.github.stefvanschie.quickskript.core.pattern;

import com.github.stefvanschie.quickskript.core.pattern.group.*;
import com.github.stefvanschie.quickskript.core.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Contains a fully parsed skript pattern
 *
 * @since 0.1.0
 */
public class SkriptPattern {

    /**
     * The groups of this skript pattern
     */
    private final List<SkriptPatternGroup> groups;

    /**
     * True if {@link TypeGroup} and {@link RegexGroup} should match as much as possible, false if they should match as
     * little as possible. In both cases, a valid match has priority over this option: the groups mentioned will not
     * match something if that means the match will become impossible, even though a match is possible if they'd capture
     * more.
     */
    private boolean greedy = true;

    /**
     * A set with functions that can parse groups
     */
    private static final Set<Function<String, Pair<? extends SkriptPatternGroup, String>>> GROUP_PARSERS = Set.of(
        ChoiceGroup::parseStarting,
        LiteralGroup::parseStarting,
        OptionalGroup::parseStarting,
        RegexGroup::parseStarting,
        SpaceGroup::parseStarting,
        TypeGroup::parseStarting
    );

    /**
     * Creates a new skript pattern
     *
     * @param groups the groups
     * @since 0.1.0
     */
    private SkriptPattern(List<SkriptPatternGroup> groups) {
        this.groups = groups;
    }

    /**
     * Parses a skript pattern from the given input
     *
     * @param input the input to transform into a pattern
     * @return the pattern
     * @since 0.1.0
     */
    @NotNull
    public static SkriptPattern parse(@NotNull String input) {
        List<SkriptPatternGroup> groups = new ArrayList<>();

        while (!input.isEmpty()) {
            for (Function<String, Pair<? extends SkriptPatternGroup, String>> parser : GROUP_PARSERS) {
                Pair<? extends SkriptPatternGroup, String> pair = parser.apply(input);

                if (pair == null) {
                    continue;
                }

                groups.add(pair.getX());

                input = pair.getY();
                break;
            }
        }

        return new SkriptPattern(groups);
    }

    /**
     * Tries to match the given input to this pattern. Returns a {@link SkriptMatchResult} detailing information
     * about the success of this match. This is successful even if not the entire string was matched. The string that
     * wasn't matched can be found inside the {@link SkriptMatchResult}. The match has to start at the start of the
     * input, it will not match somewhere in the middle of the string. The list containing these results is mutable.
     *
     * @param input the input to match
     * @return the match result
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public List<SkriptMatchResult> match(@NotNull String input) {
        if (groups.isEmpty()) {
            return new ArrayList<>(0);
        }

        return groups.get(0).match(groups.subList(1, groups.size()).toArray(SkriptPatternGroup[]::new), input);
    }

    /**
     * Parses skript patterns from the given inputs
     *
     * @param input the input to transform into patterns
     * @return the patterns
     * @since 0.1.0
     */
    @NotNull
    public static SkriptPattern[] parse(@NotNull String... input) {
        SkriptPattern[] patterns = new SkriptPattern[input.length];

        for (int i = 0; i < input.length; i++) {
            patterns[i] = parse(input[i]);
        }

        return patterns;
    }

    /**
     * Gets all groups. This will also return groups inside other groups.
     *
     * @return an immutable list of all groups in the order as they appear in the pattern
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public List<SkriptPatternGroup> getGroups() {
        return groups.stream()
            .flatMap(group -> Stream.concat(Stream.of(group), group.getChildren().stream()))
            .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Changes whether this pattern should be greedy or not.
     *
     * @param greedy true if this should be greedy, false if not
     * @return this pattern
     * @see #greedy
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public SkriptPattern greedy(boolean greedy) {
        this.greedy = greedy;

        return this;
    }
}

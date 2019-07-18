package com.github.stefvanschie.quickskript.core.pattern.group;

import com.github.stefvanschie.quickskript.core.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Matches a space character (U+0020). No other spaces should be matched by this group; those belong to
 * {@link LiteralGroup}. This special group is needed, because of the special cases with {@link OptionalGroup}s and
 * spaces around them.
 */
public class SpaceGroup implements SkriptPatternGroup {

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Contract(pure = true)
    @Override
    public List<SkriptPatternGroup> getChildren() {
        return new ArrayList<>();
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
    public static Pair<SpaceGroup, String> parseStarting(@NotNull String input) {
        if (input.charAt(0) == ' ') {
            return new Pair<>(new SpaceGroup(), input.substring(1));
        }

        return null;
    }
}
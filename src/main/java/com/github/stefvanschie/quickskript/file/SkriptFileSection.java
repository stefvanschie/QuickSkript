package com.github.stefvanschie.quickskript.file;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a section of skript lines
 *
 * @since 0.1.0
 */
public class SkriptFileSection extends SkriptFileNode {

    /**
     * The underlying nodes in this section
     */
    @NotNull
    private final List<SkriptFileNode> nodes = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    SkriptFileSection(String text) {
        super(text);
    }

    /**
     * Returns a list of nodes
     *
     * @return the nodes
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public List<SkriptFileNode> getNodes() {
        return nodes;
    }

    /**
     * Parses a section from the given header and strings
     *
     * @param nodes the underlying nodes
     * @since 0.1.0
     */
    void parse(@NotNull List<String> nodes) {
        for (int index = 0; index < nodes.size(); index++) {
            String node = nodes.get(index);

            //isn't a section
            if (!node.endsWith(":")) {
                getNodes().add(new SkriptFileLine(node));
                continue;
            }

            int i = index;
            do {
                if (++i == nodes.size())
                    break;
            } while (nodes.get(i).startsWith("    ") && i != nodes.size() - 1);

            if (i == nodes.size()) {
                continue;
            }

            List<String> strings = new ArrayList<>(nodes).subList(index + 1,
                    i == nodes.size() - 1 && nodes.get(i).startsWith("    ") ? i + 1 : i);

            for (int j = 0; j < strings.size(); j++)
                strings.set(j, strings.get(j).substring(4));

            String header = nodes.get(index);

            SkriptFileSection skriptFileSection =
                    new SkriptFileSection(header.substring(0, header.length() - 1));

            skriptFileSection.parse(strings);

            getNodes().add(skriptFileSection);

            index += strings.size();
        }
    }
}

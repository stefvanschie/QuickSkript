package com.github.stefvanschie.quickskript.file;

import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.skript.SkriptLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    SkriptFileSection(@NotNull String text, int lineNumber) {
        super(text, lineNumber);
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
     * Parses all of the nodes, including the ones inside nested
     * {@link SkriptFileSection}s into a psi structure
     *
     * @return the nodes parsed into a psi structure
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public List<PsiElement<?>> parseNodes() {
        //TODO stuff to look out for:
        // - else statements need to hook into the if statements
        // - the 'if' keyword is optional
        // - there is a 'wait # <timestamp>' expression (maybe a preprocessor for stuff like this? - indent it?)
        // - loops are easy: <while/loop> <some stuff>:
        // - inside if statements, the unhandled Boolean is probably handled just outside: halts execution if false
        // - inside loop and while statements the unhandled boolean means continue
        // - exit (optional number) expression exists

        return getNodes().stream()
                .map(node -> SkriptLoader.get().forceParseElement(node.getText(), node.getLineNumber()))
                .collect(Collectors.toList());
    }

    /**
     * Parses a section from the given header and strings
     *
     * @param nodes the underlying nodes
     * @param lineNumberOffset the offset of the line number from the starting node. This value represents the line
     * number of the first node in the list.
     * @since 0.1.0
     */
    void parse(@NotNull List<String> nodes, int lineNumberOffset) {
        for (int index = 0; index < nodes.size(); index++) {
            String node = nodes.get(index);

            if (node.isEmpty())
                continue;

            int newLineNumberOffset = lineNumberOffset + index;

            //isn't a section
            if (!node.endsWith(":")) {
                getNodes().add(new SkriptFileLine(node, newLineNumberOffset));
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
                    new SkriptFileSection(header.substring(0, header.length() - 1), newLineNumberOffset);

            skriptFileSection.parse(strings, newLineNumberOffset + 1);

            getNodes().add(skriptFileSection);

            index += strings.size();
        }
    }
}

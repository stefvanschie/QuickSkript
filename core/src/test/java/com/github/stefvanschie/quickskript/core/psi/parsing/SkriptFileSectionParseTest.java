package com.github.stefvanschie.quickskript.core.psi.parsing;

import com.github.stefvanschie.quickskript.core.psi.TestClassBase;
import com.github.stefvanschie.quickskript.core.file.SkriptFileNode;
import com.github.stefvanschie.quickskript.core.file.SkriptFileSection;
import com.github.stefvanschie.quickskript.core.util.TriFunction;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

/**
 * Tests whether the {@link SkriptFileSection}
 * class correctly parses the sections.
 */
@SuppressWarnings("HardcodedLineSeparator")
class SkriptFileSectionParseTest extends TestClassBase {

    private static final String INDENTATION = "    ";

    private final TriFunction<String, Integer, List<String>, SkriptFileSection> sectionConstructor;

    SkriptFileSectionParseTest() throws ReflectiveOperationException {
        Constructor<SkriptFileSection> constructor = SkriptFileSection.class.getDeclaredConstructor(String.class, int.class, List.class);
        constructor.setAccessible(true);
        sectionConstructor = (text, lineNumber, nodes) -> {
            try {
                return constructor.newInstance(text, lineNumber, nodes);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        };
    }


    @Test
    void test() {
        String text = "call\n" +
            "branch:\n" +
            "    call\n" +
            "    call\n" +
            "branch:\n" +
            "    call\n" +
            "    branch:\n" +
            "        call" +
            "call";

        SkriptFileSection root = sectionConstructor.apply("", 1, Arrays.asList(text.split("\n")));

        StringBuilder builder = new StringBuilder();
        appendSection(builder, root, 0);

        String result = builder.toString();
        result = result.substring(0, Math.max(result.length() - 1, 0)); //trailing newline
        Assertions.assertEquals(text, result);
    }

    private void appendSection(@NotNull StringBuilder builder, @NotNull SkriptFileSection section,
                               int indentationLevel) {
        for (SkriptFileNode node : section.getNodes()) {
            builder.append(INDENTATION.repeat(Math.max(0, indentationLevel)));

            builder.append(node.getText());

            if (!(node instanceof SkriptFileSection)) {
                builder.append('\n');
                continue;
            }

            builder.append(":\n");
            appendSection(builder, (SkriptFileSection) node, indentationLevel + 1);
        }
    }
}

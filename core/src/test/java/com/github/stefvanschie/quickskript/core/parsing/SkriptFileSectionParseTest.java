package com.github.stefvanschie.quickskript.core.parsing;

import com.github.stefvanschie.quickskript.core.TestClassBase;
import com.github.stefvanschie.quickskript.core.file.SkriptFileNode;
import com.github.stefvanschie.quickskript.core.file.SkriptFileSection;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * Tests whether the {@link SkriptFileSection}
 * class correctly parses the sections.
 */
@SuppressWarnings("HardcodedLineSeparator")
class SkriptFileSectionParseTest extends TestClassBase {

    private static final String INDENTATION = "    ";

    private final BiFunction<String, Integer, SkriptFileSection> sectionConstructor;
    private final BiConsumer<SkriptFileSection, Object[]> sectionParser;

    SkriptFileSectionParseTest() throws ReflectiveOperationException {
        Constructor<SkriptFileSection> constructor = SkriptFileSection.class.getDeclaredConstructor(String.class, int.class);
        constructor.setAccessible(true);
        sectionConstructor = (text, lineNumber) -> {
            try {
                return constructor.newInstance(text, lineNumber);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        };

        Method parser = SkriptFileSection.class.getDeclaredMethod("parse", List.class, int.class);
        parser.setAccessible(true);
        sectionParser = (section, parameters) -> {
            try {
                parser.invoke(section, parameters);
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

        SkriptFileSection root = sectionConstructor.apply("", 1);
        sectionParser.accept(root, new Object[]{Arrays.asList(text.split("\n")), 1});

        StringBuilder builder = new StringBuilder();
        appendSection(builder, root, 0);

        String result = builder.toString();
        result = result.substring(0, result.length() - 1); //trailing newline
        Assertions.assertEquals(text, result);
    }

    private void appendSection(@NotNull StringBuilder builder, @NotNull SkriptFileSection section, int indentationLevel) {
        for (SkriptFileNode node : section.getNodes()) {
            for (int i = 0; i < indentationLevel; i++) {
                builder.append(INDENTATION);
            }

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

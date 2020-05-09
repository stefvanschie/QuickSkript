package com.github.stefvanschie.quickskript.core.psi.parsing;

import com.github.stefvanschie.quickskript.core.TestClassBase;
import com.github.stefvanschie.quickskript.core.file.skript.FileSkript;
import com.github.stefvanschie.quickskript.core.file.skript.SkriptFileLine;
import com.github.stefvanschie.quickskript.core.file.skript.SkriptFileNode;
import com.github.stefvanschie.quickskript.core.psi.exception.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

/**
 * Tests regarding skript files.
 */
class SkriptFileParseTest extends TestClassBase {

    @ParameterizedTest
    @ValueSource(strings = {"Basic-sections", "Dynamic-indentation", "Simple-with-comments"})
    void testValidSamples(String file) {
        FileSkript skript = getSkriptResource(file);
        skript.registerCommands(getSkriptLoader());
        skript.registerEventExecutors(getSkriptLoader());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Invalid-command", "Invalid-content", /*"Invalid-event"*/})
    void testInvalidSamples(String file) {
        Assertions.assertThrows(ParseException.class, () -> {
            FileSkript skript = getSkriptResource(file);
            skript.registerCommands(getSkriptLoader());
            skript.registerEventExecutors(getSkriptLoader());
        });
    }

    @Test
    void testTabCharacterHandling() {
        String tabReplacement = "    ";

        List<SkriptFileNode> nodes = FileSkript.load(getSkriptLoader(), "name", Arrays.asList(
            "\tmessage \"Hello, world!\" to the console",
            "\tmessage \"1532793000\" to the console"
        )).getNodes();
        List<String> expected = List.of(
            tabReplacement + "message \"Hello, world!\" to the console",
            tabReplacement + "message \"1532793000\" to the console"
        );

        for (int i = 0; i < nodes.size(); i++) {
            SkriptFileNode node = nodes.get(i);

            Assertions.assertTrue(node instanceof SkriptFileLine);
            Assertions.assertEquals(expected.get(i), node.getText());
        }
    }
}

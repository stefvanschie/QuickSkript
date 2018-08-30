package com.github.stefvanschie.quickskript.util;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A text message containing multiple message parts
 *
 * @since 0.1.0
 */
public class TextMessage {

    /**
     * A list of all the message parts
     */
    @NotNull
    private final List<TextPart> parts = new ArrayList<>();

    /**
     * Adds an additional text part to the end of the current message
     *
     * @param part the part to add
     * @since 0.1.0
     */
    public void addPart(@NotNull TextPart part) {
        parts.add(part);
    }

    /**
     * Constructs a normal string form the given text message
     *
     * @return the string containing the message
     * @since 0.1.0
     */
    public String construct() {
        StringBuilder message = new StringBuilder();

        parts.forEach(part -> {
            if (part instanceof TextString)
                message.append(((TextString) part).getText());
            else if (part instanceof TextFormat)
                message.append(ChatColor.getByChar(((TextFormat) part).getChar()));
        });

        return message.toString();
    }

    /**
     * A pattern for matching color codes
     */
    private static final Pattern COLOR_PATTERN = Pattern.compile("[&" + ChatColor.COLOR_CHAR + "]([0-9a-fk-or])");

    /**
     * Parses a text message from the given string
     *
     * @param text the text to parse
     * @return the parsed text message
     * @since 0.1.0
     */
    @NotNull
    @Contract("null -> fail")
    public static TextMessage parse(@NotNull String text) {
        TextMessage message = new TextMessage();

        Matcher colorMatcher = COLOR_PATTERN.matcher(text);

        while (colorMatcher.find()) {
            char code = colorMatcher.group(1).charAt(0);

            message.addPart(new TextString(text.substring(0, colorMatcher.start())));
            message.addPart(new TextFormat(code));

            text = text.substring(colorMatcher.end());

            colorMatcher.reset(text);
        }

        message.addPart(new TextString(text));

        return message;
    }
}

package com.github.stefvanschie.quickskript.util;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
}

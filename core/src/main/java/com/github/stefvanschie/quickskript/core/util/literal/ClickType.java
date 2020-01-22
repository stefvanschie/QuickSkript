package com.github.stefvanschie.quickskript.core.util.literal;

/**
 * An enum representing possible ways to click
 *
 * @since 0.1.0
 */
public enum ClickType {

    /**
     * Represents a player doing an action in the creative inventory menu
     *
     * @since 0.1.0
     */
    CREATIVE_ACTION,

    /**
     * Represents a player double clicking with their mouse
     *
     * @since 0.1.0
     */
    DOUBLE_CLICK_USING_MOUSE,

    /**
     * Represents a player pressing the drop key
     *
     * @since 0.1.0
     */
    DROP_KEY,

    /**
     * Represents a player pressing the drop key while holding the control button
     *
     * @since 0.1.0
     */
    DROP_KEY_WITH_CONTROL,

    /**
     * Represents a player clicking with the left mouse button
     *
     * @since 0.1.0
     */
    LEFT_MOUSE_BUTTON,

    /**
     * Represents a player clicking with the left mouse button while holding the shift button
     *
     * @since 0.1.0
     */
    LEFT_MOUSE_BUTTON_WITH_SHIFT,

    /**
     * Represents a player clicking on the middle mouse button (scroll wheel)
     *
     * @since 0.1.0
     */
    MIDDLE_MOUSE_BUTTON,

    /**
     * Represents a player pressing one of the number keys
     *
     * @since 0.1.0
     */
    NUMBER_KEY,

    /**
     * Represents a player clicking with the right mouse button
     *
     * @since 0.1.0
     */
    RIGHT_MOUSE_BUTTON,

    /**
     * Represents a player clicking with the right mouse button while holding the shift button
     *
     * @since 0.1.0
     */
    RIGHT_MOUSE_BUTTON_WITH_SHIFT,

    /**
     * Represents the player performing an unknown action
     *
     * @since 0.1.0
     */
    UNKNOWN,

    /**
     * Represents a player clicking on the window border with the left mouse button
     *
     * @since 0.1.0
     */
    WINDOW_BORDER_USING_LEFT_MOUSE_BUTTON,

    /**
     * Represents a player clicking on the window border with the right mouse button
     *
     * @since 0.1.0
     */
    WINDOW_BORDER_USING_RIGHT_MOUSE_BUTTON,
}

package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a period between two {@link Time}s
 *
 * @since 0.1.0
 */
public class TimePeriod {

    /**
     * The start and end times of the time period
     */
    @NotNull
    private Time start, end;

    /**
     * The dawn time period
     *
     * @since 0.1.0
     */
    @NotNull
    public static final TimePeriod DAWN = new TimePeriod(new Time(22200), new Time(23999));

    /**
     * The day time period
     *
     * @since 0.1.0
     */
    @NotNull
    public static final TimePeriod DAY = new TimePeriod(new Time(0), new Time(11999));

    /**
     * The dusk time period
     *
     * @since 0.1.0
     */
    @NotNull
    public static final TimePeriod DUSK = new TimePeriod(new Time(12000), new Time(12799));

    /**
     * The night time period
     *
     * @since 0.1.0
     */
    @NotNull
    public static final TimePeriod NIGHT = new TimePeriod(new Time(13800), new Time(22199));

    /**
     * Creates a new time period with the given times
     *
     * @param start the start time
     * @param end the end time
     * @since 0.1.0
     */
    public TimePeriod(@NotNull Time start, @NotNull Time end) {
        this.start = start;
        this.end = end;
    }
}

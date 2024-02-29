package com.github.stefvanschie.quickskript.core.util.literal.direction;

import com.github.stefvanschie.quickskript.core.util.literal.Location;
import com.github.stefvanschie.quickskript.core.util.literal.World;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static com.github.stefvanschie.quickskript.core.TestUtil.*;

class HorizontalRelativeDirectionTest {

    @Test
    void testGetRelative() {
        World world = new World("test");
        Location originalLocation = new Location(world, -47, -29, 22, -154, -103);
        HorizontalRelativeDirection direction = new HorizontalRelativeDirection(-36, 9);

        Location newLocation = direction.getRelative(originalLocation);

        assertSame(world, newLocation.getWorld());
        assertInRange(-56, newLocation.getX(), -38);
        assertEquals(-29, newLocation.getY());
        assertInRange(13, newLocation.getZ(), 31);
        assertEquals(-154, newLocation.getYaw());
        assertEquals(-103, newLocation.getPitch());
    }
}

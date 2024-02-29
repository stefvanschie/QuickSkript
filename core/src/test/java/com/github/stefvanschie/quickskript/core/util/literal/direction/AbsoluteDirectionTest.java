package com.github.stefvanschie.quickskript.core.util.literal.direction;

import com.github.stefvanschie.quickskript.core.util.literal.Location;
import com.github.stefvanschie.quickskript.core.util.literal.World;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbsoluteDirectionTest {

    @Test
    void testGetRelative() {
        World world = new World("test");
        Location originalLocation = new Location(world, -71, 31, -83, 132, 112);
        AbsoluteDirection direction = new AbsoluteDirection(3, -46, -84);

        Location newLocation = direction.getRelative(originalLocation);

        assertSame(world, newLocation.getWorld());
        assertEquals(-68, newLocation.getX());
        assertEquals(-15, newLocation.getY());
        assertEquals(-167, newLocation.getZ());
        assertEquals(132, newLocation.getYaw());
        assertEquals(112, newLocation.getPitch());
    }
}

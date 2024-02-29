package com.github.stefvanschie.quickskript.core.util.literal.direction;

import com.github.stefvanschie.quickskript.core.util.literal.Location;
import com.github.stefvanschie.quickskript.core.util.literal.World;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static com.github.stefvanschie.quickskript.core.TestUtil.*;

class RelativeDirectionTest {

    @Test
    void testGetRelative() {
        World world = new World("test");
        Location originalLocation = new Location(world, 49, 25, -53, 120, -17);
        RelativeDirection direction = new RelativeDirection(-100, -72, 5);

        Location newLocation = direction.getRelative(originalLocation);

        assertSame(world, newLocation.getWorld());
        assertInRange(44, newLocation.getX(), 54);
        assertInRange(20, newLocation.getY(), 30);
        assertInRange(-58, newLocation.getZ(), -48);
        assertEquals(120, newLocation.getYaw());
        assertEquals(-17, newLocation.getPitch());
    }
}

package primitives;

import primitives.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link primitives.Ray}.
 */
public class RayTests {

    @Test
    void testGetPoint() {
        Ray ray = new Ray(new Point(1, 2, 3), new Vector(0, 0, 1));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Positive t (point in direction of the ray)
        assertEquals(new Point(1, 2, 5), ray.getPoint(2),
                "TC01: getPoint did not return correct point for positive t");

        // TC02: Negative t (point in opposite direction of the ray)
        assertEquals(new Point(1, 2, 1), ray.getPoint(-2),
                "TC02: getPoint did not return correct point for negative t");

        // =============== Boundary Values Tests ==================

        // TC03: t = 0 (should return the origin point itself)
        assertEquals(new Point(1, 2, 3), ray.getPoint(0),
                "TC03: getPoint at t=0 should return the ray's origin point");

        // TC04: t is a small positive value (epsilon)
        assertEquals(new Point(1, 2, 3.000001), ray.getPoint(0.000001),
                "TC04: getPoint at small positive t failed");

        // TC05: t is a large positive value
        assertEquals(new Point(1, 2, 1000003), ray.getPoint(1000000),
                "TC05: getPoint at large t failed");
    }
}

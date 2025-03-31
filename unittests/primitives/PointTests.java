package primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Point} class.
 * The tests verify the correct behavior of the {@link Point} methods such as subtraction, addition, and distance calculations.
 */
class PointTest {

    /**
     * Tests the {@link Point#subtract(Point)} method.
     * Verifies that subtracting two points results in the correct vector.
     * It also checks subtraction from the zero point.
     */
    @Test
    void testSubtract() {
        Point p1 = new Point(3, 4, 5);
        Point p2 = new Point(1, 2, 3);
        Vector expected = new Vector(2, 2, 2);
        assertEquals(expected, p1.subtract(p2), "Subtracting two different points failed");

        // Subtracting from ZERO
        assertEquals(new Vector(-3, -4, -5), Point.ZERO.subtract(p1), "Subtracting from ZERO failed");
    }

    /**
     * Tests the {@link Point#add(Vector)} method.
     * Verifies that adding a vector to a point results in the correct new point.
     * It also checks adding a negative vector.
     */
    @Test
    void testAdd() {
        Point p = new Point(1, 2, 3);
        Vector v = new Vector(1, 1, 1);
        assertEquals(new Point(2, 3, 4), p.add(v), "Adding a vector failed");

        // Adding a negative vector
        assertEquals(new Point(3, 3, 3), new Point(5, 5, 5).add(new Vector(-2, -2, -2)), "Adding negative vector failed");
    }

    /**
     * Tests the {@link Point#distanceSquared(Point)} method.
     * Verifies that the distance squared calculation between two points is correct.
     * It also checks the distance squared for the same point and for 3D points.
     */
    @Test
    void testDistanceSquared() {
        Point p1 = new Point(0, 0, 0);
        Point p2 = new Point(3, 4, 0);
        assertEquals(25, p1.distanceSquared(p2), "Distance squared calculation failed");

        // Same point
        assertEquals(0, p1.distanceSquared(p1), "Distance squared of the same point should be 0");

        // Points with different coordinates
        Point p3 = new Point(1, 2, 3);
        Point p4 = new Point(4, 6, 9);
        assertEquals(61, p3.distanceSquared(p4), "Distance squared in 3D failed");
    }

    /**
     * Tests the {@link Point#distance(Point)} method.
     * Verifies that the distance calculation between two points is correct.
     * It also checks the distance for the same point and calculates the 3D distance with precision.
     */
    @Test
    void testDistance() {
        Point p1 = new Point(0, 0, 0);
        Point p2 = new Point(3, 4, 0);
        assertEquals(5, p1.distance(p2), "Distance calculation failed");

        // Same point
        assertEquals(0, p1.distance(p1), "Distance of the same point should be 0");

        // Precision check
        Point p3 = new Point(1, 1, 1);
        Point p4 = new Point(4, 5, 6);
        assertEquals(Math.sqrt(50), p3.distance(p4), 0.0001, "Distance in 3D failed");
    }
}
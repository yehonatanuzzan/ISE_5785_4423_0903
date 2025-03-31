package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Cylinder} class.
 * The tests verify the correctness of the normal vector calculations for different parts of the cylinder.
 * <p>
 * The tests include:
 * <ul>
 *     <li>Checking the normal vector on the curved surface of the cylinder.</li>
 *     <li>Checking the normal vector on the bottom base of the cylinder.</li>
 *     <li>Checking the normal vector on the top base of the cylinder.</li>
 * </ul>
 * </p>
 * The normal vector should be:
 * <ul>
 *     <li>Perpendicular to the cylinder's surface at any given point.</li>
 *     <li>Pointing outward from the surface.</li>
 *     <li>Normalized to a unit length.</li>
 * </ul>
 * </p>
 * @author Yehonatan Uzzan and Oz Dahari
 */
class CylinderTests {

    /**
     * Test method for {@link Cylinder#getNormal(Point)}.
     * <p>
     * This test checks:
     * <ul>
     *     <li>That the normal vector is correct on the curved surface.</li>
     *     <li>That the normal vector is correct on the bottom base.</li>
     *     <li>That the normal vector is correct on the top base.</li>
     * </ul>
     * </p>
     * The expected behavior:
     * <ul>
     *     <li>For a point on the curved surface, the normal is perpendicular to the axis and points outward.</li>
     *     <li>For a point on the bottom base, the normal points downward (negative y-axis).</li>
     *     <li>For a point on the top base, the normal points upward (positive y-axis).</li>
     * </ul>
     */
    @Test
    void testGetNormal() {
        // Create a cylinder with radius 1 and height 5
        Cylinder cylinder = new Cylinder(new Ray(new Point(0, 0, 0), new Vector(0, 1, 0)), 1.0, 5.0);

        // Check normal on the cylinder surface
        Point pointOnSide = new Point(1, 2, 0);
        Vector expectedNormal = new Vector(1, 0, 0).normalize();
        assertEquals(expectedNormal, cylinder.getNormal(pointOnSide), "ERROR: Normal on the side is incorrect.");

        // Check normal on the bottom base
        Point pointOnBottomBase = new Point(0.5, 0, 0.5);
        Vector expectedBottomNormal = new Vector(0, -1, 0);
        assertEquals(expectedBottomNormal, cylinder.getNormal(pointOnBottomBase), "ERROR: Normal on bottom base is incorrect.");

        // Check normal on the top base
        Point pointOnTopBase = new Point(0.5, 5, 0.5);
        Vector expectedTopNormal = new Vector(0, 1, 0);
        assertEquals(expectedTopNormal, cylinder.getNormal(pointOnTopBase), "ERROR: Normal on top base is incorrect.");
    }
}

package geometries;

import org.junit.jupiter.api.Test;
import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Tube} class.
 * <p>
 * These tests verify the functionality of the {@link Tube} class,
 * ensuring that the normal vector calculation is correct for various cases,
 * including edge cases and typical surface points.
 * </p>
 *
 * @author Yehonatan Uzzan and Oz Dahari
 */
class TubeTest {

    /**
     * Test method for {@link Tube#getNormal(Point)}.
     * <p>
     * This test verifies that the normal vector is correctly calculated
     * for a point on the surface of the tube, ensuring that the vector
     * is normalized and points in the correct direction.
     * </p>
     */
    @Test
    void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============

        // Create a Tube with a specific radius and axis ray
        Tube tube = new Tube(new Ray(new Point(0, 0, 0), new Vector(0, 1, 0)), 1.0);

        // Test case: Point on the surface of the Tube
        Point pointOnTheSurface = new Point(1, 2, 0);
        Vector theExpectedNormal = new Vector(1, 0, 0).normalize();
        Vector actualNormal = tube.getNormal(pointOnTheSurface);

        // Assert that the calculated normal is as expected
        assertEquals(theExpectedNormal, actualNormal, "Normal computation is incorrect.");

        // Check that the normal vector isn't a unit vector (its length isn't 1)
        assertEquals(theExpectedNormal.dotProduct(actualNormal), 1, 1e-10,
                "The computed normal is not normalized.");



    }

    // =============== Boundary Values Tests ==================

    /**
     * Test method for {@link Tube#getNormal(Point)}.
     * <p>
     * This test checks the behavior when the point is on the cylinder's axis,
     * which is considered an edge case.
     * </p>
     * @throws IllegalArgumentException if the point is on the cylinder's axis.
     */
    @Test
    void testGetNormalEdgeCase() {
        Ray axisRay = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));
        Tube tube = new Tube(axisRay, 1.0);

        Point edgeCasePoint = new Point(0, 1, 0);
        Vector normal = tube.getNormal(edgeCasePoint);

        assertNotNull(normal, "Normal should be computed even for edge cases.");
    }

    public tesrFindIntersection() {
        // Create a tube with a specific radius and axis ray
        Tube tube = new Tube(new Ray(new Point(0, 0, 0), new Vector(0, 1, 0)), 1.0);

        // Test case: Point on the surface of the Tube
        Point pointOnTheSurface = new Point(1, 2, 0);
        Vector theExpectedNormal = new Vector(1, 0, 0).normalize();
        Vector actualNormal = tube.getNormal(pointOnTheSurface);

        // Assert that the calculated normal is as expected
        assertEquals(theExpectedNormal, actualNormal, "Normal computation is incorrect.");
    }
}

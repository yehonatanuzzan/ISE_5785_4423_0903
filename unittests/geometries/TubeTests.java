package geometries;

import org.junit.jupiter.api.Test;
import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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

    @Test
    void testGetNormalEdgeCase() {
        Ray axisRay = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));
        Tube tube = new Tube(axisRay, 1.0);

        Point edgeCasePoint = new Point(0, 0, 0);
        assertThrows(IllegalArgumentException.class, () -> tube.getNormal(edgeCasePoint),
                "Expected IllegalArgumentException for point exactly on the tube's axis");
    }


    @Test
    void testFindIntersections() {
        // Tube centered on Y-axis with radius 1
        Tube tube = new Tube(new Ray(new Point(0, 0, 0), new Vector(0, 1, 0)), 1.0);

        // Ray: comes from the left side and intersects the tube
        Ray ray = new Ray(new Point(-2, 1, 0), new Vector(1, 0, 0));

        // Expecting 2 intersection points
        List<Point> intersections = tube.findIntersections(ray);
        assertNotNull(intersections, "Expected intersections but got null");
        assertEquals(2, intersections.size(), "Expected 2 intersection points");

        // Optional: check specific values
        Point p1 = intersections.get(0);
        Point p2 = intersections.get(1);

        // Check that both points lie on the ray and on the surface of the tube (radius = 1)
        assertEquals(1.0, p1.distance(new Point(0, 1, 0)), 1e-10);
        assertEquals(1.0, p2.distance(new Point(0, 1, 0)), 1e-10);
    }

}

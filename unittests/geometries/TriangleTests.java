package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Triangle} class.
 * <p>
 * These tests verify the functionality of the {@link Triangle} class,
 * ensuring that the normal vector is correctly calculated for a given point on the triangle.
 * </p>
 *
 * @author Yehonatan Uzzan and Oz Dahari
 */
class TriangleTest {

    // ============ Equivalence Partitions Tests ==============

    /**
     * Test method for {@link Triangle#getNormal(Point)}.
     * <p>
     * This test ensures that the normal vector is correctly calculated
     * for a point on the surface of the triangle, and that it is normalized.
     * </p>
     */
    @Test
    void testGetNormal() {

        // Creating a triangle with three points as required
        Point point1 = new Point(0, 0, 1);
        Point point2 = new Point(1, 0, 0);
        Point point3 = new Point(0, 1, 0);
        Triangle triangle = new Triangle(point1, point2, point3);

        // Ensuring that the method does not throw an exception
        assertDoesNotThrow(() -> triangle.getNormal(point1), "getNormal throws an exception");

        // Calculating the expected normal
        Vector vector1 = point2.subtract(point1);
        Vector vector2 = point3.subtract(point1);

        // Checking that the normal vector is a unit vector
        assertEquals(1, triangle.getNormal(point1).length(), 1e-10, "Triangle's normal is not a unit vector");
    }

    // =============== Boundary Values Tests ==================

    /**
     * Test method for {@link Triangle#getNormal(Point)}.
     * <p>
     * This test verifies the behavior of the method when the point is on the edge of the triangle.
     * </p>
     */
    @Test
    void testGetNormalEdgeCase() {

        // Creating a triangle with three points as required
        Point point1 = new Point(0, 0, 1);
        Point point2 = new Point(1, 0, 0);
        Point point3 = new Point(0, 1, 0);
        Triangle triangle = new Triangle(point1, point2, point3);

        // Test edge case with a point on the edge of the triangle
        Point edgeCasePoint = new Point(0.5, 0.5, 0);
        Vector normal = triangle.getNormal(edgeCasePoint);

        // Assert that the normal is calculated for an edge case
        assertNotNull(normal, "Normal should be computed for edge case points.");
    }

    @Test
    void testFindIntersections() {
        Triangle triangle = new Triangle(
                new Point(0, 0, 1),
                new Point(1, 0, 0),
                new Point(0, 1, 0)
        );

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray inside the triangle
        Ray ray1 = new Ray(new Point(-1, -1, -1), new Vector(1, 1, 2));
        List<Point> result1 = triangle.findIntersections(ray1);
        assertNotNull(result1, "Expected intersection inside triangle");
        assertEquals(1, result1.size(), "Wrong number of points");
        assertEquals(new Point(0.25, 0.25, 0.5), result1.get(0), "Wrong intersection point");

        // TC02: Ray outside against edge
        Ray ray2 = new Ray(new Point(-1, -1, -1), new Vector(2, 1, 2));
        assertNull(triangle.findIntersections(ray2), "Expected no intersection (outside against edge)");

        // TC03: Ray outside against vertex
        Ray ray3 = new Ray(new Point(-1, -1, -1), new Vector(1, 2, 2));
        assertNull(triangle.findIntersections(ray3), "Expected no intersection (outside against vertex)");

        // =============== Boundary Values Tests ==================

        // TC04: Ray on edge
        Ray ray4 = new Ray(new Point(-1, -1, -1), new Vector(1, 1, 1));
        assertNull(triangle.findIntersections(ray4), "Expected no intersection (on edge)");

        // TC05: Ray in vertex
        Ray ray5 = new Ray(new Point(-1, -1, -1), new Vector(1, 1, 0));
        assertNull(triangle.findIntersections(ray5), "Expected no intersection (in vertex)");

        // TC06: Ray on edge's continuation
        Ray ray6 = new Ray(new Point(-1, -1, -1), new Vector(3, 3, 1));
        assertNull(triangle.findIntersections(ray6), "Expected no intersection (on edge's continuation)");
    }


}

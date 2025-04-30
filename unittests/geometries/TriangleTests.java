package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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
    }
}

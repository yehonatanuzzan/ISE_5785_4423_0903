package geometries;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Unit tests for the {@link Plane} class.
 * The tests include:
 * <ul>
 *     <li>Checking that the normal vector is perpendicular to at least two different vectors on the plane.</li>
 *     <li>Ensuring the normal vector is normalized.</li>
 *     <li>Testing edge cases where exceptions should be thrown due to invalid plane construction.</li>
 * </ul>
 *
 *  * @author Yehonatan Uzzan and Oz Dahari
 */
class PlaneTest {

    // ============ Equivalence Partitions Tests ==============

    /**
     * Test method to verify that the normal vector is perpendicular to the plane and has a length of 1.
     * <p>
     * This test checks:
     * <ul>
     *     <li>That the normal vector is perpendicular to two vectors on the plane.</li>
     *     <li>That the normal vector has a length of 1 (unit vector).</li>
     * </ul>
     */
    @Test
    void testNormalVector() {
        Point P1 = new Point(1, 2, 3);
        Point P2 = new Point(4, 6, 8);
        Point P3 = new Point(7, 10, 12);

        Plane plane = new Plane(P1, P2, P3);
        Vector normal = plane.getNormal(P1);

        // Check that the normal is perpendicular to at least two vectors on the plane
        Vector v1 = P2.subtract(P1);
        Vector v2 = P3.subtract(P1);

        assertEquals(0, normal.dotProduct(v1), 1e-10,
                "Normal is not perpendicular to v1");
        assertEquals(0, normal.dotProduct(v2), 1e-10,
                "Normal is not perpendicular to v2");

        // Ensure the normal is normalized
        assertEquals(1, normal.length(),  1e-10,
                "Normal is not of unit length");
    }

    // =============== Boundary Values Tests ==================

    /**
     * Test method to validate various edge cases for plane construction.
     * <p>
     * This test ensures that exceptions are thrown for invalid plane construction when:
     * <ul>
     *     <li>Two or more points are identical.</li>
     *     <li>The points are collinear (lie on the same line).</li>
     * </ul>
     */
    @Test
    void testInvalidPlaneConstruction() {
        // Case 1: First and second points are identical
        Point p1_case1 = new Point(1, 2, 3);
        Point p2_case1 = new Point(1, 2, 3); // Identical to p1
        Point p3_case1 = new Point(4, 5, 6);
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1_case1, p2_case1, p3_case1),
                "Exception expected for invalid plane construction");

        // Case 2: Second and third points are identical
        Point p1_case2 = new Point(1, 2, 3);
        Point p2_case2 = new Point(4, 5, 6);
        Point p3_case2 = new Point(4, 5, 6); // Identical to p2
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1_case2, p2_case2, p3_case2),
                "Exception expected for invalid plane construction");

        // Case 3: First and third points are identical
        Point p1_case3 = new Point(1, 2, 3);
        Point p2_case3 = new Point(4, 5, 6);
        Point p3_case3 = new Point(1, 2, 3); // Identical to p1
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1_case3, p2_case3, p3_case3),
                "Exception expected for invalid plane construction");

        // Case 4: All three points are identical
        Point p1_case4 = new Point(1, 1, 1);
        Point p2_case4 = new Point(1, 1, 1);
        Point p3_case4 = new Point(1, 1, 1);
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1_case4, p2_case4, p3_case4),
                "Exception expected for invalid plane construction");

        // Case 5: Points are collinear (lie on the same line)
        Point p1_case5 = new Point(0, 0, 0);
        Point p2_case5 = new Point(1, 1, 1);
        Point p3_case5 = new Point(2, 2, 2); // Collinear with p1 and p2
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1_case5, p2_case5, p3_case5),
                "Exception expected for invalid plane construction");
    }


    @Test
    void testFindIntersections() {
        Plane plane = new Plane(
                new Point(0, 0, 1),
                new Point(1, 0, 1),
                new Point(0, 1, 1)
        );

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray intersects the plane
        Ray ray1 = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        List<Point> result1 = plane.findIntersections(ray1);
        assertNotNull(result1, "Expected intersection with the plane");
        assertEquals(1, result1.size(), "Wrong number of points");
        assertEquals(new Point(0, 0, 1), result1.get(0), "Wrong intersection point");

        // TC02: Ray does not intersect the plane (parallel and outside)
        Ray ray2 = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));
        assertNull(plane.findIntersections(ray2), "Expected no intersection (ray parallel to plane)");

        // =============== Boundary Values Tests ==================

        // TC03: Ray is orthogonal to the plane and starts before
        Ray ray3 = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        List<Point> result3 = plane.findIntersections(ray3);
        assertNotNull(result3, "Expected intersection (orthogonal and before)");
        assertEquals(new Point(0, 0, 1), result3.get(0), "Wrong intersection point");

        // TC04: Ray is orthogonal to the plane and starts in the plane
        Ray ray4 = new Ray(new Point(0, 0, 1), new Vector(0, 0, 1));
        assertNull(plane.findIntersections(ray4), "Expected no intersection (ray starts in the plane)");

        // TC05: Ray is orthogonal to the plane and starts after the plane
        Ray ray5 = new Ray(new Point(0, 0, 2), new Vector(0, 0, 1));
        assertNull(plane.findIntersections(ray5), "Expected no intersection (starts after plane)");

        // TC06: Ray is parallel and lies in the plane
        Ray ray6 = new Ray(new Point(0, 0, 1), new Vector(1, 1, 0));
        assertNull(plane.findIntersections(ray6), "Expected no intersection (ray lies in plane)");
    }

}

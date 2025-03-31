package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Sphere} class.
 * <p>
 * These tests verify the functionality of methods in the {@link Sphere} class,
 * ensuring that the normal vector calculation is correct.
 * </p>
 *
 * @author Yehonatan Uzzan and Oz Dahari
 */
class SphereTest {

    // ============ Equivalence Partitions Tests ==============

    /**
     * Test method for {@link Sphere#getNormal(Point)}.
     * <p>
     * This test ensures that the normal vector is correctly calculated
     * for a point on the surface of the sphere, and that it is normalized.
     * </p>
     */
    @Test
    void testGetNormal() {

        // Creating a sphere with center and radius
        Point center = new Point(0, 0, 0);
        double radius = 1.0;

        Sphere sphere = new Sphere(center, radius);

        Point point = new Point(0, 0, 1);

        // Ensuring that the method does not throw an exception
        assertDoesNotThrow(() -> sphere.getNormal(point), "getNormal throws an exception");

        // Checking that the normal vector is a unit vector
        assertEquals(1, sphere.getNormal(point).length(), 1e-10, "Sphere's normal is not a unit vector");

    }

    // =============== Boundary Values Tests ==================

    /**
     * Test method for {@link Sphere#getNormal(Point)}.
     * <p>
     * This test checks the behavior when the point is located at the top of the sphere's surface.
     * </p>
     */
    @Test
    void testGetNormalBoundary() {

        // Creating a sphere with center and radius
        Point center = new Point(0, 0, 0);
        double radius = 1.0;

        Sphere sphere = new Sphere(center, radius);

        // Test case: Point on the top surface of the sphere
        Point point = new Point(0, 0, 1);

        // Checking that the normal vector is calculated correctly
        Vector expectedNormal = new Vector(0, 0, 1).normalize();
        Vector actualNormal = sphere.getNormal(point);

        // Assert that the calculated normal matches the expected normal
        assertEquals(expectedNormal, actualNormal, "Normal vector is incorrect at top of the sphere.");
    }
}

package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
        Vector expectedNormal = Vector.UNIT_Z.normalize();
        Vector actualNormal = sphere.getNormal(point);

        // Assert that the calculated normal matches the expected normal
        assertEquals(expectedNormal, actualNormal, "Normal vector is incorrect at top of the sphere.");
    }


    /**
     * A point used in some tests
     */
    private final Point p001 = new Point(0, 1, 0);
    /**
     * A point used in some tests
     */
    private final Point p100 = new Point(1, 0, 0);
    /**
     * A vector used in some tests
     */
    private final Vector v001 = Vector.UNIT_Z;

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */

    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(p001, 1);
        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray's line is outside the sphere
        assertNull(sphere.findIntersections(new Ray(new Point(0, 0, 2), new Vector(0, 1, 1))), "There is no intersection");

        //TC02: Ray's line is before the sphere
        final Ray ray02 = new Ray(new Point(0, -1, 0.5), Vector.UNIT_Y);
        final List<Point> result02 = sphere.findIntersections(ray02);
        final List<Point> expected02 = List.of(new Point(0.0, 0.29289321880690444, 0.70710678118), new Point(0.0, 1.7071067811930956, 0.70710678118));

        assertNotNull(result02, "Can't be empty list");
        assertEquals(2, result02.size(), "Wrong number of points");
        assertEquals(expected02, result02, "Ray crosses sphere");

        //TC03: Ray's line is in the sphere
        final Ray ray03 = new Ray(new Point(0, 1, 0.5), Vector.UNIT_Y);
        final List<Point> result03 = sphere.findIntersections(ray03);
        final List<Point> expected03 = List.of(new Point(0.0, 1.7071067811930956, 0.70710678118));

        assertNotNull(result03, "Can't be empty list");
        assertEquals(1, result03.size(), "Wrong number of points");
        assertEquals(expected03, result03, "Ray crosses sphere");

        //TC04: Ray's line is after the sphere
        final Ray ray04 = new Ray(new Point(0, 3, 0.5), Vector.UNIT_Y);
        assertNull(sphere.findIntersections(ray04), "Ray not crosses sphere");

        // =============== Boundary Values Tests ==================
        //Group 1:The ray’s line crosses the sphere twice
        //TC11: Ray starts outside and crosses the sphere completely
        final Ray ray11 = new Ray(new Point(0.0, 0.29289321880690444, 0.70710678118), new Vector(0, -1, 0));
        assertNull(sphere.findIntersections(ray11), "Ray not crosses sphere");
        //TC12: Ray starts inside and exits the sphere (1 point returned, but part of a 2-point path)
        final Ray ray12 = new Ray(new Point(0.0, 0.29289321880690444, 0.70710678118), Vector.UNIT_Y);
        final List<Point> result12 = sphere.findIntersections(ray12);
        final List<Point> expected12 = List.of(new Point(0.0, 1.7071067811930956, 0.70710678118));

        assertNotNull(result12, "Can't be empty list");
        assertEquals(1, result12.size(), "Wrong number of points");
        assertEquals(expected12, result12, "Wrong Point of Ray crosses sphere");

        //Group 2: The ray’s line crosses the Sphere’s center O
        //TC21: Ray starts at the center – 1 intersection outward
        final Ray ray21 = new Ray(new Point(0, 1, 0), Vector.UNIT_Y);
        final List<Point> result21 = sphere.findIntersections(ray21);
        final List<Point> expected21 = List.of(new Point(0, 2, 0));

        assertNotNull(result21, "Can't be empty list");
        assertEquals(1, result21.size(), "Wrong number of points");
        assertEquals(expected21, result21, "Wrong Point of Ray crosses sphere");

        //TC22: Ray starts on surface and goes inward – 1 point
        final Ray ray22 = new Ray(new Point(0, 2, 0), new Vector(0, -1, 0));
        final List<Point> result22 = sphere.findIntersections(ray22);
        final List<Point> expected22 = List.of(new Point(0, 0, 0));

        assertNotNull(result22, "Can't be empty list");
        assertEquals(1, result22.size(), "Wrong number of points");
        assertEquals(expected22, result22, "Wrong Point of Ray crosses sphere");

        //TC23: Ray starts outside and passes through the center – 2 points
        final Ray ray23 = new Ray(new Point(0, 3, 0), new Vector(0, -1, 0));
        final List<Point> result23 = sphere.findIntersections(ray23);
        final List<Point> expected23 = List.of(new Point(0, 2, 0), new Point(0, 0, 0));

        assertNotNull(result23, "Can't be empty list");
        assertEquals(2, result23.size(), "Wrong number of points");
        assertEquals(expected23, result23, "Wrong Point of Ray crosses sphere");

        //TC24: Ray starts inside, not at center – 1 point outward
        final Ray ray24 = new Ray(new Point(0, 0.5, 0), new Vector(0, -1, 0));
        final List<Point> result24 = sphere.findIntersections(ray24);
        final List<Point> expected24 = List.of(new Point(0, 0, 0));

        assertNotNull(result24, "Can't be empty list");
        assertEquals(1, result24.size(), "Wrong number of points");
        assertEquals(expected24, result24, "Wrong Point of Ray crosses sphere");

        //TC25: Ray starts on the surface of sphere and goes away – no intersection
        assertNull(sphere.findIntersections(new Ray(new Point(0, 0, 0), new Vector(0, -1, 0))), "There is no intersection");

        //TC26: Ray starts outside and behind sphere – no intersection
        assertNull(sphere.findIntersections(new Ray(new Point(0, -1, 0), new Vector(0, -1, 0))), "There is no intersection");

        //Group 3: The ray is orthogonal to the segment 𝑃0(we didn’t originally think about this case)
        //TC31:Ray starts outside, direction orthogonal to vector p0 - no intersection
        assertNull(sphere.findIntersections(new Ray(new Point(0, -1, 0), Vector.UNIT_Z)), "There is no intersection");

        //TC32:Ray starts inside, direction orthogonal to vector p0 - 1 intersection
        final Ray ray32 = new Ray(new Point(0, 0.5, 0), Vector.UNIT_Z);
        final List<Point> result32 = sphere.findIntersections(ray32);
        final List<Point> expected32 = List.of(new Point(0, 0.5, 0.8660254038));

        assertNotNull(result32, "Can't be empty list");
        assertEquals(1, result32.size(), "Wrong number of points");
        assertEquals(expected32, result32, "Wrong Point of Ray crosses sphere");

        //Group 4: The ray’s line is tangent to the Sphere
        //TC41:Ray is tangent and starts at point of tangency – no intersection
        assertNull(sphere.findIntersections(new Ray(new Point(0, 1, 1), new Vector(0, 1, 1))), "There is no intersection");

        //TC42:Ray is tangent and points away – no intersection
        assertNull(sphere.findIntersections(new Ray(new Point(0, 1, 1.05),  new Vector(0, 1, 1))), "There is no intersection");

        //TC43: Ray is tangent to sphere – 1 intersection
        final Ray ray43 = new Ray(new Point(-1, 0, 0),  Vector.UNIT_X);
        final List<Point> result43 = sphere.findIntersections(ray43);
        final List<Point> expected43 = List.of(new Point(0, 0,0));

        assertNotNull(result43, "Can't be empty list");
        assertEquals(1, result43.size(), "Wrong number of points");
        assertEquals(expected43, result43, "Wrong Point of Ray crosses sphere");
    }

}












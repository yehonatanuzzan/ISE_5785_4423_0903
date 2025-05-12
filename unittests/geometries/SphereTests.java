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
 * ensuring that the normal vector calculation is correct and intersection logic works correctly.
 * </p>
 */
class SphereTest {

    private static final double DELTA = 1e-10;

    @Test
    void testGetNormal() {
        Sphere sphere = new Sphere(new Point(0, 0, 0), 1.0);
        Point point = new Point(0, 0, 1);

        Vector normal = sphere.getNormal(point);

        // Check direction and normalization
        assertEquals(new Vector(0, 0, 1), normal, "Sphere normal is incorrect");
        assertEquals(1.0, normal.length(), DELTA, "Sphere normal is not normalized");
    }

    @Test
    void testFindIntersections() {
        Sphere sphere = new Sphere(new Point(0, 0, 0), 1.0);

        // TC01: Ray starts outside and intersects twice
        Ray ray1 = new Ray(new Point(-2, 0, 0), Vector.UNIT_X);
        List<Point> intersections1 = sphere.findIntersections(ray1);
        assertNotNull(intersections1, "TC01: Expected intersections");
        assertEquals(2, intersections1.size(), "TC01: Expected 2 intersection points");
        for (Point p : intersections1) {
            assertEquals(1.0, p.distance(Point.ZERO), DELTA, "TC01: Point not on sphere surface");
            assertTrue(p.subtract(ray1.getp1()).dotProduct(ray1.getDirection()) > 0, "TC01: Intersection point not on ray direction");
        }

        // TC02: Ray starts inside and exits
        Ray ray2 = new Ray(new Point(0, 0, 0), Vector.UNIT_X);
        List<Point> intersections2 = sphere.findIntersections(ray2);
        assertNotNull(intersections2, "TC02: Expected intersections");
        assertEquals(1, intersections2.size(), "TC02: Expected 1 intersection point");
        assertEquals(1.0, intersections2.get(0).distance(Point.ZERO), DELTA, "TC02: Point not on sphere surface");

        // TC03: Ray starts outside and misses
        Ray ray3 = new Ray(new Point(2, 2, 0), Vector.UNIT_X);
        assertNull(sphere.findIntersections(ray3), "TC03: Expected no intersection");

        // TC04: Ray is tangent and starts at point of tangency
        Ray ray4 = new Ray(new Point(0, 1, 0), new Vector(0, 1, 0));
        assertNull(sphere.findIntersections(ray4), "TC04: Expected no intersection");

        // TC05: Ray starts outside and goes away
        Ray ray5 = new Ray(new Point(2, 0, 0), Vector.UNIT_X);
        assertNull(sphere.findIntersections(ray5), "TC05: Expected no intersection");

        // TC06: Ray starts at surface and goes inward (expect 1 point)
        Ray ray6 = new Ray(new Point(1, 0, 0), new Vector(-1, 0, 0));
        List<Point> intersections6 = sphere.findIntersections(ray6);
        assertNotNull(intersections6, "TC06: Expected intersection");
        assertEquals(1, intersections6.size(), "TC06: Expected 1 intersection point");
        assertEquals(1.0, intersections6.get(0).distance(Point.ZERO), DELTA, "TC06: Point not on sphere surface");

        // TC07: Ray starts at sphere center (expect 1 point)
        Ray ray7 = new Ray(Point.ZERO, new Vector(0, 1, 0));
        List<Point> intersections7 = sphere.findIntersections(ray7);
        assertNotNull(intersections7, "TC07: Expected intersection");
        assertEquals(1, intersections7.size(), "TC07: Expected 1 intersection point");
        assertEquals(1.0, intersections7.get(0).distance(Point.ZERO), DELTA, "TC07: Point not on sphere surface");

        // TC08: Ray orthogonal to center vector, should miss
        Ray ray8 = new Ray(new Point(0, -2, 0), Vector.UNIT_Z);
        assertNull(sphere.findIntersections(ray8), "TC08: Expected no intersection");
    }
}

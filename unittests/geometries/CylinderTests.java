package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Cylinder} class.
 * These tests verify the correctness of the normal vector and intersections on different parts of the cylinder.
 * <p>
 * Includes tests for:
 * <ul>
 *     <li>Normal on side, bottom base, top base</li>
 *     <li>Intersection with side, bases, and special edge cases</li>
 * </ul>
 * </p>
 * @author Yehonatan Uzzan and Oz Dahari
 */
class CylinderTests {

    @Test
    void testGetNormal() {
        Cylinder cylinder = new Cylinder(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 1.0, 2.0);

        // Side surface
        assertEquals(new Vector(1, 0, 0), cylinder.getNormal(new Point(1, 0, 1)), "Wrong normal for side surface");

        // Bottom base
        assertEquals(new Vector(0, 0, -1), cylinder.getNormal(new Point(0.5, 0.5, 0)), "Wrong normal for bottom base");

        // Top base
        assertEquals(new Vector(0, 0, 1), cylinder.getNormal(new Point(0.5, 0.5, 2)), "Wrong normal for top base");
    }

    @Test
    void testFindIntersections() {
        Cylinder cylinder = new Cylinder(new Ray(new Point(0, 0, 0), new Vector(0, 1, 0)), 1.0, 2.0);

        // TC01: Side intersection
        Ray ray1 = new Ray(new Point(-2, 1, 0), new Vector(1, 0, 0));
        List<Point> result1 = cylinder.findIntersections(ray1);
        assertNotNull(result1, "TC01: Expected intersections");
        assertEquals(2, result1.size(), "TC01: Expected 2 intersections on side");

        // TC02: Ray above cylinder (no intersection)
        assertNull(cylinder.findIntersections(new Ray(new Point(-2, 3, 0), new Vector(1, 0, 0))),
                "TC02: Expected no intersection (above)");

        // TC03: Ray hitting only the top base (perpendicular to base center)
        Ray ray3 = new Ray(new Point(0.5, 3, 0), new Vector(0, -1, 0));
        List<Point> result3 = cylinder.findIntersections(ray3);
        assertNotNull(result3, "TC03: Expected intersection with top base");
        assertEquals(1, result3.size(), "TC03: Expected 1 intersection with top base");
        assertEquals(new Point(0.5, 2, 0), result3.get(0), "TC03: Wrong intersection point with top base");

        // TC04: Ray hitting only the bottom base (perpendicular to base center)
        Ray ray4 = new Ray(new Point(0.5, -1, 0), new Vector(0, 1, 0));
        List<Point> result4 = cylinder.findIntersections(ray4);
        assertNotNull(result4, "TC04: Expected intersection with bottom base");
        assertEquals(1, result4.size(), "TC04: Expected 1 intersection with bottom base");
        assertEquals(new Point(0.5, 0, 0), result4.get(0), "TC04: Wrong intersection point with bottom base");

        // TC05: Ray starting on side, going outward — no intersection
        Ray ray5 = new Ray(new Point(-1, 1, 0), new Vector(-1, 0, 0));
        assertNull(cylinder.findIntersections(ray5), "TC05: Expected no intersection (from surface out)");

        // TC06: Ray tangent to side surface — no intersection
        assertNull(cylinder.findIntersections(new Ray(new Point(1, 1, -1), new Vector(0, 0, 1))),
                "TC06: Expected no intersection (tangent)");

        // TC07: Ray along axis — should intersect both bases only
        Ray ray7 = new Ray(new Point(0, -1, 0), new Vector(0, 1, 0));
        List<Point> result7 = cylinder.findIntersections(ray7);
        assertNotNull(result7, "TC07: Expected intersections along axis");
        assertEquals(2, result7.size(), "TC07: Expected 2 intersections along axis");
        assertTrue(result7.contains(new Point(0, 0, 0)), "TC07: Missing bottom base intersection");
        assertTrue(result7.contains(new Point(0, 2, 0)), "TC07: Missing top base intersection");
    }
}

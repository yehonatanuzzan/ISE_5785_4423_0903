package geometries;

import geometries.*;
import primitives.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link geometries.Geometries}.
 */
public class GeometriesTests {

    @Test
    void testFindIntersections() {
        Sphere sphere = new Sphere(new Point(0, 0, 0), 1);
        Plane plane = new Plane(new Point(0, 0, 1), new Vector(0, 0, 1));
        Triangle triangle = new Triangle(new Point(-1, -1, 1), new Point(1, -1, 1), new Point(0, 1, 1));

        Ray rayThroughAll = new Ray(new Point(0, 0, -2), new Vector(0, 0, 1));

        Geometries geometries = new Geometries(sphere, plane, triangle);

        List<Point> intersections = geometries.findIntersections(rayThroughAll);
        assertNotNull(intersections, "Expected intersection with all geometries");
        assertEquals(4, intersections.size(), "Expected 4 intersection points (2 with sphere, 1 with plane, 1 with triangle)");
    }

}

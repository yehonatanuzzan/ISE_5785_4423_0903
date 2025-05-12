package renderer;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for the Camera class with different geometries.
 * Tests the integration between the camera and the scene's geometries.
 *
 * @author Yehonatan Uzzan
 */
public class CameraIntegrationTests {

    /**
     * Helper method to count the number of intersection points between the rays of the camera and a given geometry.
     *
     * @param camera   The camera from which the rays are cast.
     * @param geometry The geometry to test intersections with.
     * @return The number of intersection points.
     */
    private int countIntersections(Camera camera, Geometry geometry) {
        int count = 0;
        int nX = 3;
        int nY = 3;
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                Ray ray = camera.constructRay(nX, nY, j, i);
                var intersections = geometry.findIntersections(ray);
                if (intersections != null) {
                    count += intersections.size();
                }
            }
        }
        return count;
    }

    /**
     * Integration tests of Camera with Sphere.
     * Tests various sphere configurations with respect to the camera.
     */
    @Test
    public void testCameraSphereIntegration() {
        Camera camera = new Camera.Builder(
                new Point(0, 0, 0),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0)
        ).setVPSize(3, 3).setVPDistance(1).build();

        // TC01: Small Sphere in front of the camera (2 points)
        Sphere sphere = new Sphere(new Point(0, 0, -3), 1);
        assertEquals(2, countIntersections(camera, sphere), "TC01: Small Sphere");

        // TC02: Big Sphere enclosing the view plane (18 points)
        sphere = new Sphere(new Point(0, 0, -2.5), 2.5);
        assertEquals(9, countIntersections(camera, sphere), "TC02: Big Sphere");

        // TC03: Medium Sphere partly in view (10 points)
        sphere = new Sphere(new Point(0, 0, -2), 2);
        assertEquals(9, countIntersections(camera, sphere), "TC03: Medium Sphere");

        // TC04: Sphere behind the camera (0 points)
        sphere = new Sphere(new Point(0, 0, 1), 0.5);
        assertEquals(0, countIntersections(camera, sphere), "TC04: Sphere behind");
    }

    /**
     * Integration tests of Camera with Plane.
     * Tests various plane configurations with respect to the camera.
     */
    @Test
    public void testCameraPlaneIntegration() {
        Camera camera = new Camera.Builder(
                new Point(0, 0, 0),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0)
        ).setVPSize(3, 3).setVPDistance(1).build();

        // TC01: Plane orthogonal to view plane (9 points)
        Plane plane = new Plane(new Point(0, 0, -5), new Vector(0, 0, 1));
        assertEquals(9, countIntersections(camera, plane), "TC01: Plane facing camera");

        // TC02: Plane with slight tilt (9 points)
        plane = new Plane(new Point(0, 0, -5), new Vector(0, -0.5, 1));
        assertEquals(9, countIntersections(camera, plane), "TC02: Tilted Plane");

        // TC03: Plane behind camera (0 points)
        plane = new Plane(new Point(0, 0, 1), new Vector(0, 0, 1));
        assertEquals(0, countIntersections(camera, plane), "TC03: Plane under");
    }

    /**
     * Integration tests of Camera with Triangle.
     * Tests various triangle configurations with respect to the camera.
     */
    @Test
    public void testCameraTriangleIntegration() {
        Camera camera = new Camera.Builder(
                new Point(0, 0, 0),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0)
        ).setVPSize(3, 3).setVPDistance(1).build();

        // TC01: Small Triangle in the center (1 point)
        Triangle triangle = new Triangle(
                new Point(0, 1, -2),
                new Point(1, -1, -2),
                new Point(-1, -1, -2)
        );
        assertEquals(1, countIntersections(camera, triangle), "TC01: Small Triangle");

        // TC02: Larger Triangle covering more area (2 points)
        triangle = new Triangle(
                new Point(0, 20, -2),
                new Point(1, -1, -2),
                new Point(-1, -1, -2)
        );
        assertEquals(2, countIntersections(camera, triangle), "TC02: Large Triangle");
    }

    @Test
    public void testCameraAllPixelsSphereIntegration() {
        Camera camera = new Camera.Builder(
                new Point(0, 0, 0),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0)
        ).setVPSize(3, 3).setVPDistance(1).build();

        Sphere sphere = new Sphere(new Point(0, 0, -3), 2.5);

        int totalIntersections = 0;
        int nX = 3, nY = 3;
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                Ray ray = camera.constructRay(nX, nY, j, i);
                List<Point> intersections = sphere.findIntersections(ray);
                if (intersections != null) {
                    totalIntersections += intersections.size();
                }
            }
        }

        assertEquals(18, totalIntersections, "Total intersections with Sphere (should be 18)");
    }

    @Test
    public void testCameraAllPixelsPlaneIntegration() {
        Camera camera = new Camera.Builder(
                new Point(0, 0, 0),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0)
        ).setVPSize(3, 3).setVPDistance(1).build();

        Plane plane = new Plane(new Point(0, 0, -5), new Vector(0, 0, 1));

        int totalIntersections = 0;
        int nX = 3, nY = 3;
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                Ray ray = camera.constructRay(nX, nY, j, i);
                List<Point> intersections = plane.findIntersections(ray);
                if (intersections != null) {
                    totalIntersections += intersections.size();
                }
            }
        }

        assertEquals(9, totalIntersections, "Total intersections with Plane (should be 9)");
    }

    @Test
    public void testCameraAllPixelsTriangleIntegration() {
        Camera camera = new Camera.Builder(
                new Point(0, 0, 0),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0)
        ).setVPSize(3, 3).setVPDistance(1).build();

        Triangle triangle = new Triangle(
                new Point(0, 1, -2),
                new Point(1, -1, -2),
                new Point(-1, -1, -2)
        );

        int totalIntersections = 0;
        int nX = 3, nY = 3;
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                Ray ray = camera.constructRay(nX, nY, j, i);
                List<Point> intersections = triangle.findIntersections(ray);
                if (intersections != null) {
                    totalIntersections += intersections.size();
                }
            }
        }

        assertEquals(1, totalIntersections, "Total intersections with Triangle (should be 1)");
    }
}
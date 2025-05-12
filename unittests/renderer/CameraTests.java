package renderer;

import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link renderer.Camera}
 */
class CameraTests {

    @Test
    void testConstructorAndBuilder() {
        // TC01: Correct camera creation
        Camera camera = new Camera.Builder(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPSize(8, 6)
                .setVPDistance(10)
                .build();

        assertEquals(new Point(0, 0, 0), camera.getP0(), "TC01: Wrong camera position");
        assertEquals(new Vector(0, 0, -1), camera.getVTo(), "TC01: Wrong VTo vector");
        assertEquals(new Vector(0, 1, 0), camera.getVUp(), "TC01: Wrong VUp vector");
        assertEquals(new Vector(1, 0, 0), camera.getVRight(), "TC01: Wrong VRight vector");
        assertEquals(8, camera.getWidth(), "TC01: Wrong view plane width");
        assertEquals(6, camera.getHeight(), "TC01: Wrong view plane height");
        assertEquals(10, camera.getDistance(), "TC01: Wrong view plane distance");
    }

    @Test
    void testInvalidConstructor() {
        // TC02: Invalid vectors (not orthogonal)
        assertThrows(IllegalArgumentException.class,
                () -> new Camera.Builder(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, -1, -1)),
                "TC02: Should throw exception for non-orthogonal vectors");
    }


    @Test
    void testConstructRay() {
        Camera camera = new Camera.Builder(
                new Point(0, 0, 0),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0)
        ).setVPSize(6, 6).setVPDistance(10).build();

        // Center
        assertEquals(
                new Ray(new Point(0, 0, 0), new Vector(0, 0, -1)),
                camera.constructRay(3, 3, 1, 1),
                "TC01: Incorrect ray through center"
        );

        // Top-left
        assertEquals(
                new Ray(new Point(0, 0, 0), new Vector(-2, 2, -10)),
                camera.constructRay(3, 3, 0, 0),
                "TC02: Incorrect ray through top-left"
        );

        // Top-right
        assertEquals(
                new Ray(new Point(0, 0, 0), new Vector(2, 2, -10)),
                camera.constructRay(3, 3, 2, 0),
                "TC03: Incorrect ray through top-right"
        );

        // Bottom-left
        assertEquals(
                new Ray(new Point(0, 0, 0), new Vector(-2, -2, -10)),
                camera.constructRay(3, 3, 0, 2),
                "TC04: Incorrect ray through bottom-left"
        );

        // Bottom-right
        assertEquals(
                new Ray(new Point(0, 0, 0), new Vector(2, -2, -10)),
                camera.constructRay(3, 3, 2, 2),
                "TC05: Incorrect ray through bottom-right"
        );
    }
}

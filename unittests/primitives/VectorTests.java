package primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Vector} class.
 */
class VectorTest {

    private static final double DELTA = 0.000001; // Accuracy for floating point comparisons

    // ============ Boundary Values Tests ==============

    /**
     * Test: Creating a zero vector should throw an exception.
     */
    @Test
    void testConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0),
                "ERROR: Zero vector should throw an exception");
    }

    // ============ Equivalence Partitions Tests ==============

    /**
     * Test: Adding two vectors should return the correct sum.
     * Test: Adding a vector to its opposite should throw an exception.
     */
    @Test
    void testAdd() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(2, 3, 4);
        assertEquals(new Vector(3, 5, 7), v1.add(v2), "ERROR: Vector addition incorrect");

        assertThrows(IllegalArgumentException.class, () -> v1.add(new Vector(-1, -2, -3)),
                "ERROR: Adding opposite vectors should throw an exception");
    }

    /**
     * Test: Subtracting two vectors should return the correct result.
     * Test: Subtracting a vector from itself should throw an exception.
     */
    @Test
    void testSubtract() {
        Vector v1 = new Vector(3, 5, 7);
        Vector v2 = new Vector(1, 2, 3);
        assertEquals(new Vector(2, 3, 4), v1.subtract(v2), "ERROR: Vector subtraction incorrect");

        assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1),
                "ERROR: Subtracting a vector from itself should throw an exception");
    }

    /**
     * Test: Scaling a vector should return the correctly scaled vector.
     * Test: Scaling by zero should throw an exception.
     */
    @Test
    void testScale() {
        Vector v = new Vector(1, -2, 3);
        assertEquals(new Vector(2, -4, 6), v.scale(2), "ERROR: Vector scaling incorrect");

        assertThrows(IllegalArgumentException.class, () -> v.scale(0),
                "ERROR: Scaling by zero should throw an exception");
    }

    /**
     * Test: Dot product calculations should be correct.
     * Test: Dot product of orthogonal vectors should be zero.
     */
    @Test
    void testDotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(2, 3, 4);
        assertEquals(20, v1.dotProduct(v2), DELTA, "ERROR: Dot product calculation incorrect");

        Vector v3 = new Vector(0, 3, -2);
        assertEquals(0, v1.dotProduct(v3), DELTA, "ERROR: Dot product of orthogonal vectors should be zero");
    }

    /**
     * Test: Cross product should return a perpendicular vector.
     * Test: Cross product of parallel vectors should throw an exception.
     */
    @Test
    void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(0, 3, -2);
        Vector result = v1.crossProduct(v2);

        assertEquals(v1.length() * v2.length(), result.length(), DELTA, "ERROR: Cross product length incorrect");
        assertEquals(0, result.dotProduct(v1), DELTA, "ERROR: Cross product result is not orthogonal to v1");
        assertEquals(0, result.dotProduct(v2), DELTA, "ERROR: Cross product result is not orthogonal to v2");

        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(new Vector(2, 4, 6)),
                "ERROR: Cross product of parallel vectors should throw an exception");
    }

    /**
     * Test: Length squared calculation should be correct.
     */
    @Test
    void testLengthSquared() {
        Vector v = new Vector(1, 2, 2);
        assertEquals(9, v.lengthSquared(), DELTA, "ERROR: Length squared calculation incorrect");
    }

    /**
     * Test: Length calculation should be correct.
     */
    @Test
    void testLength() {
        Vector v = new Vector(0, 3, 4);
        assertEquals(5, v.length(), DELTA, "ERROR: Length calculation incorrect");
    }

    /**
     * Test: Normalization should return a unit vector.
     * Test: Normalized vector should be parallel to the original.
     */
    @Test
    void testNormalize() {
        Vector v = new Vector(3, 4, 0);
        Vector u = v.normalize();

        assertEquals(1, u.length(), DELTA, "ERROR: Normalized vector should have length 1");

        assertThrows(IllegalArgumentException.class, () -> v.crossProduct(u),
                "ERROR: Normalized vector should be parallel to the original");

        assertTrue(v.dotProduct(u) > 0, "ERROR: Normalized vector should be in the same direction as the original");
    }
}

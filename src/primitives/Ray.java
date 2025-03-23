package primitives;

/**
 * Class Ray represents a ray in 3D space.
 * A ray is defined by an origin point and a normalized direction vector.
 * @author Your Name
 */
public class Ray {
    private final Point head;
    private final Vector direction;

    /**
     * Constructor that initializes a ray with a given head and direction.
     * The direction vector is normalized before storing.
     * @param head The starting point of the ray.
     * @param direction The direction of the ray.
     * @throws IllegalArgumentException if the direction vector is zero.
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other) &&
                this.head.equals(other.head) &&
                this.direction.equals(other.direction);
    }

    @Override
    public int hashCode() {
        return head.hashCode() + direction.hashCode();
    }

    @Override
    public String toString() {
        return "Ray(origin: " + head + ", direction: " + direction + ")";
    }
}

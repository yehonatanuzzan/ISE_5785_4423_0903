package primitives;

import static primitives.Util.isZero;

/**
 * Class Ray represents a ray in 3D space.
 * A ray is defined by an origin point and a normalized direction vector.
 *
 * @author Yehonatan Uzzan and Oz Dahari
 */
public class Ray {
    private final Point head;
    private final Vector direction;

    /**
     * Constructor that initializes a ray with a given head and direction.
     * The direction vector is normalized before storing.
     *
     * @param head      The starting point of the ray.
     * @param direction The direction of the ray.
     * @throws IllegalArgumentException if the direction vector is zero.
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray ray)) return false;

        return head.equals(ray.head) && direction.equals(ray.direction);
    }

    @Override
    public int hashCode() {
        int result = head.hashCode();
        result = 31 * result + direction.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Ray(origin: " + head + ", direction: " + direction + ")";
    }

    public Point getp1() {
        return head;
    }

    public Vector getDirection() {
        return direction;
    }

    /**
     * Returns the point at distance t along the ray.
     *
     * @param t the distance from the ray's origin
     * @return the point at distance t along the ray's direction
     */
    public Point getPoint(double t) {
        if (isZero(t)) {
            return head;
        }
        return head.add(direction.scale(t));
    }
}


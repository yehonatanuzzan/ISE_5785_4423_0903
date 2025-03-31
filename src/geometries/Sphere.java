package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Class Sphere represents a three-dimensional sphere in space.
 * A sphere is defined by a center point and a radius.
 *
 * @author Yehonatan Uzzan and Oz Dahari
 */
public class Sphere extends RadialGeometry {
    private final Point center;

    /**
     * Constructor that initializes a sphere with a given center and radius.
     *
     * @param center The center point of the sphere.
     * @param radius The radius of the sphere.
     * @throws IllegalArgumentException if the radius is negative.
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }

    /**
     * Method to calculate the normal vector at a given point on the surface of the sphere.
     *
     * @param point The point on the sphere's surface for which the normal is to be calculated.
     * @return The normalized normal vector at the specified point.
     * @throws IllegalArgumentException If the provided point is the same as the center of the sphere.
     */
    @Override
    public Vector getNormal(Point point) {
        if (point.equals(center)) {
            throw new IllegalArgumentException("ERROR: The center point is the same as the origin of the sphere.");
        }
        Vector vector = point.subtract(center);
        return vector.normalize();
    }
}
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

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
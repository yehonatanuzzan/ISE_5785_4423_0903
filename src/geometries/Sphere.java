package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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

    @Override
    public List<Point> findIntersections(Ray ray) {
        // Calculate the vector from the center of the sphere to the ray's origin
        Vector L = ray.getp1().subtract(center);
        double tca = L.dotProduct(ray.getDirection());
        double d2 = L.dotProduct(L) - tca * tca;
        double radius2 = radius * radius;

        // Check if the ray is outside the sphere
        if (d2 > radius2) {
            return null; // No intersection
        }

        double thc = Math.sqrt(radius2 - d2);
        double t0 = tca - thc;
        double t1 = tca + thc;

        // If t0 is negative, use t1 instead
        if (t0 < 0) {
            t0 = t1;
            if (t0 < 0) {
                return null; // No intersection
            }
        }

        // Calculate the intersection points
        Point intersectionPoint = ray.getp1().add(ray.getDirection().scale(t0));
        return List.of(intersectionPoint);
    }
}
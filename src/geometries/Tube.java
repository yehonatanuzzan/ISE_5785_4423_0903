package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Class Tube represents a three-dimensional infinite tube in space.
 * A tube is defined by a central axis (ray) and a radius.
 * @author Yehonatan Uzzan and Oz Dahari
 */

public class Tube extends RadialGeometry {
    protected final Ray axisRay;

    /**
     * Constructor that initializes a tube with a given axis and radius.
     * @param axisRay The central axis of the tube.
     * @param radius The radius of the tube.
     * @throws IllegalArgumentException if the radius is negative.
     */
    public Tube(Ray axisRay, double radius) {
        super(radius);
        this.axisRay = axisRay;
    }
    private boolean isZero(double num) {
        return Math.abs(num) < 1e-10;
    }

    @Override
    public Vector getNormal(Point p) {
    Point P0 = axisRay.getp1();
    Vector v = axisRay.getDirection();
    Vector P0_P = p.subtract(P0);

    double t = v.dotProduct(P0_P);

    if (isZero(t)) {
        return P0_P.normalize();
    }

    Point projection = P0.add(v.scale(t));
    if (p.equals(projection)) {
        throw new IllegalArgumentException("ERROR: The projection of a tube is incorrect");
    }
    return p.subtract(projection).normalize();
}

    @Override
    public List<Point> findIntersections(Ray ray) {
        // Calculate the vector from the center of the tube to the ray's origin
        Vector L = ray.getp1().subtract(axisRay.getp1());
        double tca = L.dotProduct(axisRay.getDirection());
        double d2 = L.dotProduct(L) - tca * tca;
        double radius2 = radius * radius;

        // Check if the ray is outside the tube
        if (d2 > radius2) {
            return null; // No intersection
        }

        double thc = Math.sqrt(radius2 - d2);
        double t0 = tca - thc;
        double t1 = tca + thc;

        if (t0 < 0 && t1 < 0) {
            return null; // Both intersections are behind the ray
        }

        if (t0 < 0) {
            return List.of(ray.getp1().add(ray.getDirection().scale(t1)));
        } else {
            return List.of(ray.getp1().add(ray.getDirection().scale(t0)));
        }
    }
}
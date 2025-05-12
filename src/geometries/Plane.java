package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Class Plane represents a plane in 3D space.
 * A plane is defined by a point on the plane and a normal vector.
 * The normal is computed when constructed using three points.
 *
 * @author Yehonatan Uzzan and Oz Dahari
 */
public class Plane extends Geometry {
    private final Point q0; // Reference point on the plane
    private final Vector normal; // Normal vector of the plane (always normalized)

    /**
     * Constructs a Plane using three non-collinear points.
     *
     * @param p1 First point on the plane.
     * @param p2 Second point on the plane.
     * @param p3 Third point on the plane.
     * @throws IllegalArgumentException if the points are collinear or identical.
     */
    public Plane(Point p1, Point p2, Point p3) {
        if (p1.equals(p2) || p1.equals(p3) || p2.equals(p3)) {
            throw new IllegalArgumentException("Plane cannot be defined by identical points");
        }
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        Vector n = v1.crossProduct(v2);
        if (n.length() == 0)
            throw new IllegalArgumentException("Plane cannot be defined by collinear points");
        normal = n.normalize();
        q0 = p1;
    }

    /**
     * Constructs a Plane using a point and a normal vector.
     *
     * @param q0     A point on the plane.
     * @param normal The normal vector (will be normalized).
     */
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalize();
    }

    @Override
    public Vector getNormal(Point dummy) {
        return normal;
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        Point p0 = ray.getp1();
        Vector dir = ray.getDirection();
        double numerator;
        try {
            numerator = normal.dotProduct(q0.subtract(p0));
        } catch (IllegalArgumentException e) {
            return null;
        }
        double denominator = normal.dotProduct(dir);
        if (isZero(denominator)) {
            return null;
        }
        double t = alignZero(numerator / denominator);
        if (t <= 0) {
            return null;
        }
        return List.of(ray.getPoint(t));
    }
}

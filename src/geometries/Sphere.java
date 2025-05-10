package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

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

    @Override
    public List<Point> findIntersections(Ray ray) {
        Point p0 = ray.getp1();
        Vector dir = ray.getDirection();
        Point center = this.center;

        // u = center - P0
        Vector u;
        try {
            u = center.subtract(p0);
        } catch (IllegalArgumentException e) {
            // Ray origin is at the sphere center
            Point p = ray.getPoint(radius);
            return List.of(p);
        }

        double tm = alignZero(dir.dotProduct(u));
        double dSquared = alignZero(u.lengthSquared() - tm * tm);
        double rSquared = alignZero(radius * radius);

        // If the perpendicular distance from the center to the ray is more than radius => no intersection
        if (dSquared >= rSquared) {
            return null;
        }

        double th = alignZero(Math.sqrt(rSquared - dSquared));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        // no intersections in front of the ray
        if (t1 <= 0 && t2 <= 0) return null;

        if (t1 > 0 && t2 > 0) {
            Point p1 = ray.getPoint(t1);
            Point p2 = ray.getPoint(t2);
            return List.of(p1, p2);
        }

        if (t1 > 0) return List.of(ray.getPoint(t1));
        return List.of(ray.getPoint(t2));
    }

}
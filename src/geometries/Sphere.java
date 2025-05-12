package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Class Sphere represents a 3D sphere in space.
 * A sphere is defined by its center point and its radius.
 *
 * @author Yehonatan Uzzan and Oz Dahari
 */
public class Sphere extends RadialGeometry {
    private final Point center;

    /**
     * Constructs a Sphere with a given center and radius.
     *
     * @param center The center point of the sphere.
     * @param radius The radius of the sphere.
     * @throws IllegalArgumentException if the radius is not positive.
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }

    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        Point p0 = ray.getp1();
        Vector dir = ray.getDirection();
        Vector u;
        try {
            u = center.subtract(p0);
        } catch (IllegalArgumentException e) {
            return List.of(ray.getPoint(radius));
        }
        double tm = alignZero(dir.dotProduct(u));
        double dSquared = alignZero(u.lengthSquared() - tm * tm);
        double rSquared = alignZero(radius * radius);
        if (dSquared >= rSquared) {
            return null;
        }
        double th = alignZero(Math.sqrt(rSquared - dSquared));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);
        if (t1 <= 0 && t2 <= 0) return null;
        if (t1 > 0 && t2 > 0) {
            return List.of(ray.getPoint(t1), ray.getPoint(t2));
        }
        if (t1 > 0) return List.of(ray.getPoint(t1));
        return List.of(ray.getPoint(t2));
    }
}

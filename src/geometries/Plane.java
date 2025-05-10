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
 *
 * @author Yehonatan Uzzan and Oz Dahari
 */
public class Plane extends Geometry {
    private final Point q0; // A reference point on the plane
    private final Vector normal; // The normal vector to the plane

    /**
     * Constructor that receives three points and saves one as a reference.
     *
     * @param p1 First point on the plane.
     * @param p2 Second point on the plane.
     * @param p3 Third point on the plane.
     *           In this stage, the normal vector is set to null.
     */
    public Plane(Point p1, Point p2, Point p3) {
        q0 = p1;
        normal = null;
    }

    /**
     * Constructor that receives a point and a normal vector.
     * The normal vector is stored as a normalized vector, even if the input is not normalized.
     *
     * @param q0     A point on the plane.
     * @param normal The normal vector to the plane.
     */
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalize();
    }

    @Override
    /**
     * return the stored normal vector
     */
    public Vector getNormal(Point dummy) {
        return normal;
    }

    /**
     * Finds the intersection point(s) between the plane and a given ray.
     *
     * <p>If the ray is parallel to the plane, lies in the plane, or points away from it,
     * there will be no intersection (returns null). Otherwise, returns a list containing
     * the single intersection point.</p>
     *
     * @param ray the ray to intersect with the plane
     * @return a list containing the intersection point, or null if there is no intersection
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        Point p0 = ray.getp1();
        Vector dir = ray.getDirection();

        double numerator;
        try {
            numerator = normal.dotProduct(q0.subtract(p0));
        } catch (IllegalArgumentException e) {
            // Ray origin is on the plane point (Q == P0)
            return null;
        }

        double denominator = normal.dotProduct(dir);

        // Ray is parallel to the plane
        if (isZero(denominator)) {
            return null;
        }

        double t = alignZero(numerator / denominator);

        // No intersection: the ray points away from the plane or lies in the plane
        if (t <= 0) {
            return null;
        }

        Point intersection = ray.getPoint(t);
        return List.of(intersection);
    }

}
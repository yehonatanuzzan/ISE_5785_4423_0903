package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Class Tube represents a three-dimensional infinite tube in space.
 * A tube is defined by a central axis (ray) and a radius.
 * The tube is infinite along its axis.
 * <p>
 * This class provides methods to compute the normal vector and intersections with rays.
 * </p>
 * @author Yehonatan Uzzan and Oz Dahari
 */
public class Tube extends RadialGeometry {
    /**
     * The central axis ray of the tube.
     */
    protected final Ray axisRay;

    /**
     * Constructor that initializes a tube with a given axis and radius.
     *
     * @param axisRay The central axis of the tube.
     * @param radius  The radius of the tube.
     * @throws IllegalArgumentException if the radius is negative.
     */
    public Tube(Ray axisRay, double radius) {
        super(radius);
        this.axisRay = axisRay;
    }

    /**
     * Computes the normal vector at a given point on the tube's surface.
     * <p>
     * The normal is calculated by projecting the given point onto the tube's axis,
     * and then calculating the vector from the projected point to the given point.
     * This method throws an exception if the point is located exactly on the axis of the tube,
     * since in such a case, the normal vector is undefined (will result in a zero vector).
     *
     * @param point the point on the tube's surface
     * @return the normalized normal vector at the given point
     * @throws IllegalArgumentException if the point is located exactly on the tube's axis
     */
    @Override
    public Vector getNormal(Point point) {
        Vector axisDir = axisRay.getDirection();
        Point axisP0 = axisRay.getp1();

        Vector p0ToPoint = point.subtract(axisP0);
        double t = alignZero(axisDir.dotProduct(p0ToPoint));
        Point o = axisP0.add(axisDir.scale(t));

        if (point.equals(o)) {
            throw new IllegalArgumentException("Point is located on the tube's axis; normal is undefined");
        }

        return point.subtract(o).normalize();
    }

    /**
     * Finds the intersection points between the given ray and the tube.
     * <p>
     * The tube is considered infinite and is defined by a central axis (ray) and a radius.
     * This method solves a quadratic equation derived from the geometrical constraint:
     * that the distance from a point on the ray to the tube's axis equals the tube's radius.
     * <p>
     * <b>Important:</b> This method uses an algebraic expression that avoids creating a ZERO vector directly,
     * thus ensuring robustness and avoiding exceptions when the ray is parallel to the tube's axis.
     *
     * @param ray the ray to check for intersections with the tube
     * @return a list of intersection points, or null if there are none
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        Vector v = ray.getDirection();
        Vector va = axisRay.getDirection();
        Point p0 = ray.getp1();
        Point pa = axisRay.getp1();

        Vector vCrossVa;
        try {
            vCrossVa = v.crossProduct(va);
            if (isZero(vCrossVa.length())) {
                // Ray is parallel to the tube axis -> no intersections
                return null;
            }
        } catch (IllegalArgumentException e) {
            // Cross product is zero vector - ray is parallel to axis
            return null;
        }

        Vector deltaP = p0.subtract(pa);
        Vector deltaPCrossVa = deltaP.crossProduct(va);

        double A = vCrossVa.lengthSquared();
        double B = 2 * vCrossVa.dotProduct(deltaPCrossVa);
        double C = deltaPCrossVa.lengthSquared() - radius * radius * va.lengthSquared();

        double discriminant = alignZero(B * B - 4 * A * C);
        if (discriminant < 0) return null;

        double sqrtDiscriminant = Math.sqrt(discriminant);
        double t1 = alignZero((-B + sqrtDiscriminant) / (2 * A));
        double t2 = alignZero((-B - sqrtDiscriminant) / (2 * A));

        List<Point> intersections = new LinkedList<>();
        if (t1 > 0) intersections.add(ray.getPoint(t1));
        if (t2 > 0) intersections.add(ray.getPoint(t2));

        return intersections.isEmpty() ? null : intersections;
    }

}

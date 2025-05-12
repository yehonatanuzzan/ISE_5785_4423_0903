package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Class Cylinder represents a finite cylinder in 3D space.
 * A cylinder is defined by a central axis (ray), a radius, and a height.
 * The cylinder includes its two bases and the side surface.
 * <p>
 * The cylinder extends Tube and adds bounding caps.
 * </p>
 * @author Yehonatan Uzzan and Oz Dahari (refactored)
 */
public class Cylinder extends Tube {
    private final double height;

    /**
     * Constructor that initializes a cylinder with a given axis, radius, and height.
     *
     * @param axisRay The central axis of the cylinder.
     * @param radius  The radius of the cylinder.
     * @param height  The height of the cylinder.
     * @throws IllegalArgumentException if the radius or height is negative.
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        if (height <= 0)
            throw new IllegalArgumentException("Height must be positive");
        this.height = height;
    }

    @Override
    public Vector getNormal(Point point) {
        Point p0 = axisRay.getp1();
        Vector dir = axisRay.getDirection();

        double projection = alignZero(point.subtract(p0).dotProduct(dir));

        if (isZero(projection))
            return dir.scale(-1); // Bottom base normal
        if (isZero(projection - height))
            return dir; // Top base normal

        // Otherwise, on the side surface
        return super.getNormal(point);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> result = new LinkedList<>();

        Vector axisDir = axisRay.getDirection();
        Point bottomCenter = axisRay.getp1();
        Point topCenter = bottomCenter.add(axisDir.scale(height));

        // Check intersections with the side surface (only if not parallel to axis)
        if (!isZero(ray.getDirection().crossProduct(axisDir).length())) {
            List<Point> tubeIntersections = super.findIntersections(ray);
            if (tubeIntersections != null) {
                for (Point p : tubeIntersections) {
                    double projection = alignZero(p.subtract(bottomCenter).dotProduct(axisDir));
                    if (projection > 0 && projection < height) {
                        if (!containsPoint(result, p)) {
                            result.add(p);
                        }
                    }
                }
            }
        }

        // Check intersection with bottom base
        List<Point> bottomHit = new Plane(bottomCenter, axisDir).findIntersections(ray);
        if (bottomHit != null) {
            for (Point p : bottomHit) {
                if (alignZero(p.subtract(bottomCenter).lengthSquared() - radius * radius) <= 0
                        && !containsPoint(result, p)) {
                    result.add(p);
                }
            }
        }

        // Check intersection with top base
        List<Point> topHit = new Plane(topCenter, axisDir).findIntersections(ray);
        if (topHit != null) {
            for (Point p : topHit) {
                if (alignZero(p.subtract(topCenter).lengthSquared() - radius * radius) <= 0
                        && !containsPoint(result, p)) {
                    result.add(p);
                }
            }
        }

        return result.isEmpty() ? null : result;
    }

    /**
     * Utility method to check whether a point already exists in the list.
     * Comparison is based on distance within a small epsilon to handle floating point inaccuracies.
     *
     * @param points List of points to check in.
     * @param target The point to check.
     * @return true if the point already exists (within tolerance), false otherwise.
     */
    private static boolean containsPoint(List<Point> points, Point target) {
        for (Point p : points) {
            if (p.distance(target) < 1e-6) {
                return true;
            }
        }
        return false;
    }
}

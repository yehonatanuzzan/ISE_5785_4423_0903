package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Class Cylinder represents a finite cylinder in 3D space.
 * A cylinder is defined by a central axis (ray), a radius, and a height.
 *
 * @author Yehonatan Uzzan and Oz Dahari
 */
public class Cylinder extends Tube {
    /**
     * The height of the cylinder
     */
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
        if (height < 0)
            throw new IllegalArgumentException("Height cannot be negative");
        this.height = height;
    }


    /**
     * Computes the normal vector to the cylinder at a given point.
     * <p>
     * The normal is determined as follows:
     * <ul>
     *     <li>If the point is on the bottom base, the normal points downward (opposite to the cylinder's axis).</li>
     *     <li>If the point is on the top base, the normal points upward (in the direction of the cylinder's axis).</li>
     *     <li>If the point is on the curved surface, the normal is perpendicular to the axis and points outward.</li>
     * </ul>
     * </p>
     *
     * @param point The point on the cylinder.
     * @return The normal vector at the given point.
     */
    @Override
    public Vector getNormal(Point point) {
        Point P0 = axisRay.getp1(); // Base center
        Vector v = axisRay.getDirection(); // Axis direction

        // Calculate the upper base center
        Point P1 = P0.add(v.scale(height));

        // Check if the point is on the bottom base
        if (point.subtract(P0).dotProduct(v) == 0) {
            return v.scale(-1).normalize();
        }

        // Check if the point is on the top base
        if (point.subtract(P1).dotProduct(v) == 0) {
            return v.normalize();
        }

        // Otherwise, the point is on the curved surface
        return super.getNormal(point);
    }
}
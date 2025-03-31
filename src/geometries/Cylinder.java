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

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
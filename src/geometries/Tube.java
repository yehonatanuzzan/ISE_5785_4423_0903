package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Class Tube represents a three-dimensional infinite tube in space.
 * A tube is defined by a central axis (ray) and a radius.
 * @author Yehonatan Uzzan and Oz Dahari
 */
public class Tube extends RadialGeometry {
    protected final Ray axisRay; // The central axis of the tube

    /**
     * Constructor that initializes a tube with a given axis and radius.
     * @param axisRay The central axis of the tube.
     * @param radius The radius of the tube.
     * @throws IllegalArgumentException if the radius is negative.
     */
    public Tube(Ray axisRay, double radius) {
        super(radius); // Call the parent constructor to set the radius
        this.axisRay = axisRay;
    }

    @Override
    public Vector getNormal(Point point) {
        return null; // To be implemented in the next stage
    }
}
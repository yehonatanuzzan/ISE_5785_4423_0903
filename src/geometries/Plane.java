package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Class Plane represents a plane in 3D space.
 * A plane is defined by a point on the plane and a normal vector.
 * @author Yehonatan Uzzan and Oz Dahari
 */
public class Plane extends Geometry {
    private final Point q0; // A reference point on the plane
    private final Vector normal; // The normal vector to the plane

    /**
     * Constructor that receives three points and saves one as a reference.
     * @param p1 First point on the plane.
     * @param p2 Second point on the plane.
     * @param p3 Third point on the plane.
     * In this stage, the normal vector is set to null.
     */
    public Plane(Point p1, Point p2, Point p3) {
        q0 = p1;
        normal = null;
    }

    /**
     * Constructor that receives a point and a normal vector.
     * The normal vector is stored as a normalized vector, even if the input is not normalized.
     * @param q0 A point on the plane.
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
}
package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Abstract class Geometry represents a geometric object in 3D space.
 * All geometric objects must implement the getNormal method.
 *
 * @author Yehonatan Uzzan and Oz Dahari
 */
public abstract class Geometry implements Intersectable{

    /**
     * Returns the normal vector to the geometry at a given point.
     *
     * @param point A point on the surface of the geometry.
     * @return The normal vector at the given point.
     */
    public abstract Vector getNormal(Point point);
}

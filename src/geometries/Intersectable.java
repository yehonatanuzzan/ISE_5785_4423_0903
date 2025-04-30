package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * This interface defines the contract for geometries that can be intersected by a ray.
 * It contains a single method that returns a list of intersection points between the ray and the geometry.
 */
public interface Intersectable {

    /**
     * Finds the intersection points between a ray and the geometry.
     *
     * @param ray the ray to check for intersections
     * @return a list of intersection points, or null if there are no intersections
     */
    List<Point> findIntersections(Ray ray);
}
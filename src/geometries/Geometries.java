package geometries;

import primitives.*;
import java.util.*;

/**
 * Composite class representing a collection of geometrical objects.
 * Implements the Composite design pattern.
 */
public class Geometries implements Intersectable {
    private final List<Intersectable> geometries = new LinkedList<>();

    /**
     * Default constructor initializing an empty collection.
     */
    public Geometries() {
        // No need to do anything, list initialized above
    }

    /**
     * Constructor with geometries.
     * Uses add method to add the given geometries.
     *
     * @param geometries geometries to add to the collection
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    /**
     * Add geometries to the collection.
     *
     * @param geometries geometries to add
     */
    public void add(Intersectable... geometries) {
        Collections.addAll(this.geometries, geometries);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> result = null;
        for (Intersectable geo : geometries) {
            List<Point> points = geo.findIntersections(ray);
            if (points != null) {
                if (result == null) {
                    result = new LinkedList<>();
                }
                result.addAll(points);
            }
        }
        return result;
    }
}

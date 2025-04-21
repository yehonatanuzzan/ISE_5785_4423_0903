package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * The {@code Triangle} class represents a triangle in 3D space.
 * It extends the {@code Polygon} class and is defined by three vertices.
 *
 * @author Yehonatan Uzzan and Oz Dahari
 */
public class Triangle extends Polygon {

    /**
     * Constructs a new {@code Triangle} with the specified vertices.
     *
     * @param a The first vertex of the triangle.
     * @param b The second vertex of the triangle.
     * @param c The third vertex of the triangle.
     */
    public Triangle(Point a, Point b, Point c) {
        super(a, b, c);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        // Find the intersection points with the triangle
        List<Point> intersectionPoints = super.findIntersections(ray);

        // If there are no intersection points, return null
        if (intersectionPoints == null) {
            return null;
        }

        // If there is one intersection point, return it
        if (intersectionPoints.size() == 1) {
            return intersectionPoints;
        }

        // If there are multiple intersection points, return null
        return null;
    }
}

package geometries;

import primitives.Point;

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
}

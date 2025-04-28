package geometries;

import primitives.Point;

/**
 * Class Triangle represents a three-sided polygon in 3D space.
 * It extends the Polygon class and does not introduce new fields.
 *
 * @author Yehonatan Uzzan and Oz Dahari
 */
public class Triangle extends Polygon {

    /**
     * Constructor that initializes a triangle with three vertices.
     *
     * @param p1 First vertex of the triangle.
     * @param p2 Second vertex of the triangle.
     * @param p3 Third vertex of the triangle.
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }
}
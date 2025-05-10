package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

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

    /**
     * Finds the intersection points between a ray and the triangle.
     * The method first checks for an intersection with the triangle's plane,
     * then verifies if the intersection point lies inside the triangle using the inside-outside test.
     *
     * @param ray the ray to intersect with the triangle
     * @return a list containing the intersection point, or null if no intersection occurs within the triangle
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        // First, find intersection with the triangle's plane
        List<Point> planeIntersections = plane.findIntersections(ray);
        if (planeIntersections == null) {
            return null;
        }

        Point p0 = ray.getp1();
        Vector v = ray.getDirection();

        // Triangle vertices
        Point p1 = vertices.get(0);
        Point p2 = vertices.get(1);
        Point p3 = vertices.get(2);

        // Vectors from ray origin to triangle vertices
        Vector v1 = p1.subtract(p0);
        Vector v2 = p2.subtract(p0);
        Vector v3 = p3.subtract(p0);

        // Normals to the triangle's sides
        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        double sign1 = alignZero(v.dotProduct(n1));
        double sign2 = alignZero(v.dotProduct(n2));
        double sign3 = alignZero(v.dotProduct(n3));

        // Check if all signs are the same (all positive or all negative)
        if ((sign1 > 0 && sign2 > 0 && sign3 > 0) || (sign1 < 0 && sign2 < 0 && sign3 < 0)) {
            return planeIntersections;
        }

        return null; // Intersection point is outside the triangle
    }


}
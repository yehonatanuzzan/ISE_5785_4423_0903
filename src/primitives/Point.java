package primitives;

/**
 * Class Point represents a point in 3D space.
 * It is defined by three coordinates stored as a {@link Double3} object.
 *
 * @author Yehonatan Uzzan and Oz Dahari
 */
public class Point {
    /**
     * The coordinates of the point, represented as a {@link Double3} object.
     */
    protected final Double3 xyz;

    /**
     * Constant representing the origin point (0,0,0).
     */
    public static final Point ZERO = new Point(0, 0, 0);

    /**
     * Constructor that initializes a point with three coordinate values.
     *
     * @param x The X coordinate.
     * @param y The Y coordinate.
     * @param z The Z coordinate.
     */
    public Point(double x, double y, double z) {
        this(new Double3(x, y, z));
    }

    /**
     * Constructor that initializes a point from an existing {@link Double3} object.
     *
     * @param xyz A {@link Double3} object representing the coordinates of the point.
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Subtracts another point from this point, resulting in a vector.
     *
     * @param other The point to subtract.
     * @return A {@link Vector} from the other point to this point.
     */
    public Vector subtract(Point other) {
        return new Vector(this.xyz.subtract(other.xyz));
    }

    /**
     * Adds a vector to this point, resulting in a new point.
     *
     * @param vector The vector to add.
     * @return A new {@link Point} obtained by adding the vector to this point.
     */
    public Point add(Vector vector) {
        return new Point(this.xyz.add(vector.xyz));
    }

    /**
     * Computes the squared distance between this point and another point.
     *
     * @param other The other point.
     * @return The squared distance between the two points.
     */
    public double distanceSquared(Point other) {
        double dx = this.xyz.d1() - other.xyz.d1();
        double dy = this.xyz.d2() - other.xyz.d2();
        double dz = this.xyz.d3() - other.xyz.d3();

        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Computes the distance between this point and another point.
     *
     * @param other The other point.
     * @return The distance between the two points.
     */
    public double distance(Point other) {
        return Math.sqrt(distanceSquared(other));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Point other)) return false;
        return xyz.equals(other.xyz);
    }

    @Override
    public String toString() {
        return xyz.toString();
    }
}

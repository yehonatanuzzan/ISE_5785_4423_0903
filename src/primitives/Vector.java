package primitives;

/**
 * Class Vector represents a three-dimensional vector in Euclidean space.
 * A vector has a direction and a magnitude.
 * It is defined using a Point as its endpoint relative to the origin.
 * @author Your Name
 */
public class Vector extends Point {

    /**
     * Constructor that receives three coordinates and initializes the vector.
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     * @throws IllegalArgumentException if the vector is (0,0,0)
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Zero vector is not allowed");
    }

    /**
     * Constructor that receives a Double3 object.
     * @param xyz A Double3 object representing the vector.
     * @throws IllegalArgumentException if the vector is (0,0,0)
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Zero vector is not allowed");
    }

    /**
     * Adds two vectors.
     * @param other Another vector
     * @return A new vector representing the sum.
     */
    public Vector add(Vector other) {
        return new Vector(this.xyz.add(other.xyz));
    }

    /**
     * Multiplies the vector by a scalar.
     * @param scalar The scalar value.
     * @return A new scaled vector.
     */
    public Vector scale(double scalar) {
        return new Vector(this.xyz.scale(scalar));
    }

    /**
     * Computes the dot product of this vector and another vector.
     * @param other Another vector
     * @return The dot product result.
     */
    public double dotProduct(Vector other) {
        return this.xyz.d1() * other.xyz.d1() +
                this.xyz.d2() * other.xyz.d2() +
                this.xyz.d3() * other.xyz.d3();
    }

    /**
     * Computes the cross product of this vector and another vector.
     * @param other Another vector
     * @return A new vector that is perpendicular to both.
     */
    public Vector crossProduct(Vector other) {
        return new Vector(
                this.xyz.d2() * other.xyz.d3() - this.xyz.d3() * other.xyz.d2(),
                this.xyz.d3() * other.xyz.d1() - this.xyz.d1() * other.xyz.d3(),
                this.xyz.d1() * other.xyz.d2() - this.xyz.d2() * other.xyz.d1()
        );
    }

    /**
     * Computes the squared length of the vector.
     * @return The squared length.
     */
    public double lengthSquared() {
        return this.dotProduct(this);
    }

    /**
     * Computes the length of the vector.
     * @return The length.
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Normalizes the vector.
     * @return A new normalized vector.
     */
    public Vector normalize() {
        double len = length();
        if (len == 0)
            throw new IllegalArgumentException("Cannot normalize zero vector");
        return scale(1 / len);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Vector other) && super.equals(other);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "->" + super.toString();
    }
}

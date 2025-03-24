package geometries;

/**
 * Class RadialGeometry represents geometric bodies that have a radius.
 * It serves as a base class for all radial geometries like spheres and cylinders.
 * @author Yehonatan Uzzan and Oz Dahari
 */
public abstract class RadialGeometry extends Geometry {
    protected final double radius;

    /**
     * Constructor that initializes the radius of the geometry.
     * @param radius The radius of the geometry.
     * @throws IllegalArgumentException if the radius is negative.
     */
    public RadialGeometry(double radius) {
        if (radius < 0)
            throw new IllegalArgumentException("Radius cannot be negative");
        this.radius = radius;
    }
}
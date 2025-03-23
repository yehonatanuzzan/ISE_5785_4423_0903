package primitives;

import java.util.Objects;

public class Point {
    protected final Double3 xyz;

    public static final Point ZERO = new Point(0,0,0);

    public Point(double x, double y, double z) {
        this(new Double3(x,y,z));
    }

    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    public Vector subtract(Point other) {
        return new Vector(this.xyz.subtract(other.xyz));
    }

    public Point add(Vector vector) {
        return new Point(this.xyz.add(vector.xyz));
    }

    public double distanceSquared(Point other) {
        return this.xyz.product(other.xyz).d1() +
                this.xyz.product(other.xyz).d2() +
                this.xyz.product(other.xyz).d3();
    }

    public double distance(Point other) {
        return Math.sqrt(distanceSquared(other));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point other)
                && this.xyz.equals(other.xyz);
    }

    @Override
    public  int hashCode() {
        return xyz.hashCode();
    }

    @Override
    public String toString() {
        return xyz.toString();
    }

}
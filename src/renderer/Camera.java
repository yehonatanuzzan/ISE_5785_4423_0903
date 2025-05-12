package renderer;

import primitives.*;

import static primitives.Util.isZero;

/**
 * Camera class represents a basic camera in a 3D scene.
 * The camera is defined by:
 * <ul>
 *     <li>A point in space - the camera position</li>
 *     <li>Direction vectors: vTo (forward), vUp (up), and vRight (right)</li>
 *     <li>View plane parameters: width, height, and distance from the camera</li>
 * </ul>
 */
public class Camera {

    private final Point p0;
    private final Vector vTo;
    private final Vector vUp;
    private final Vector vRight;
    private double width = 1;
    private double height = 1;
    private double distance = 1;

    /**
     * Builder class for {@link Camera}.
     */
    public static class Builder {
        private final Point p0;
        private final Vector vTo;
        private final Vector vUp;
        private Camera camera;

        /**
         * Builder constructor that receives the camera position and two direction vectors.
         *
         * @param p0  the position of the camera
         * @param vTo the direction to look at
         * @param vUp the up direction
         */
        public Builder(Point p0, Vector vTo, Vector vUp) {
            if (!isZero(vTo.dotProduct(vUp))) {
                throw new IllegalArgumentException("vTo and vUp are not orthogonal");
            }
            this.p0 = p0;
            this.vTo = vTo.normalize();
            this.vUp = vUp.normalize();
            this.camera = new Camera(this);
        }

        /**
         * Set the size of the view plane.
         *
         * @param width  the width of the view plane
         * @param height the height of the view plane
         * @return the builder itself
         */
        public Builder setVPSize(double width, double height) {
            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * Set the distance between the camera and the view plane.
         *
         * @param distance the distance from the camera to the view plane
         * @return the builder itself
         */
        public Builder setVPDistance(double distance) {
            camera.distance = distance;
            return this;
        }

        /**
         * Build the camera.
         *
         * @return the constructed camera
         */
        public Camera build() {
            return camera;
        }
    }

    /**
     * Private constructor to enforce builder pattern.
     *
     * @param builder the builder object
     */
    private Camera(Builder builder) {
        this.p0 = builder.p0;
        this.vTo = builder.vTo;
        this.vUp = builder.vUp;
        this.vRight = vTo.crossProduct(vUp).normalize();
    }

    /**
     * Constructs a ray from the camera through the specified pixel on the view plane.
     *
     * @param nX number of pixels in X direction
     * @param nY number of pixels in Y direction
     * @param j  pixel column index (0-based)
     * @param i  pixel row index (0-based)
     * @return the constructed ray through the given pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point pc = p0.add(vTo.scale(distance));

        double rY = height / nY;
        double rX = width / nX;

        double xJ = (j - (nX - 1) / 2.0) * rX;
        double yI = - (i - (nY - 1) / 2.0) * rY;

        Point pij = pc;
        if (!isZero(xJ)) {
            pij = pij.add(vRight.scale(xJ));
        }
        if (!isZero(yI)) {
            pij = pij.add(vUp.scale(yI));
        }

        Vector dir = pij.subtract(p0);
        return new Ray(p0, dir);
    }


    // Getters
    public Point getP0() {
        return p0;
    }

    public Vector getVTo() {
        return vTo;
    }

    public Vector getVUp() {
        return vUp;
    }

    public Vector getVRight() {
        return vRight;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getDistance() {
        return distance;
    }
}
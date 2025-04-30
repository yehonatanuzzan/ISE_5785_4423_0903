package renderer;

import primitives.*;

import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;


/**
 * @author yehonatanuzzan
 */

public class Camera implements Cloneable {

    private Point position;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private double width = 0;
    private double height = 0;
    private double distance = 0;

    private Camera() {
    }

    ;

    public static Builder getBuilder() {
        return null;
    }

    ;

    public Ray constructRay(int nX, int nY, int j, int i) {
        double rX = width / nX;
        double rY = height / nY;
        double yI = (-1) * (i - (nY-1) / 2.0) * rY;
        double xJ = (j - (nX-1) / 2.0) * rX;
        Point pIJ = position.add(vTo.scale(distance));
        if (!isZero(xJ)) pIJ = pIJ.add(vRight.scale(xJ));
        if (!isZero(yI)) pIJ = pIJ.add(vUp.scale(yI));

        return new Ray(position,pIJ.subtract(position));

    }

    ;

    public static class Builder {
        final private Camera camera = new Camera();
        private Point pDirection = null;
        public Builder setLocation(Point p) {
            camera.position = p;
            return this;
        }

        ;


        public Builder setDirection(Vector vTo, Vector vUp) {
            if (isZero(vTo.dotProduct(vUp))) {
                camera.vTo = vTo.normalize();
                camera.vUp = vUp.normalize();
            } else {
                throw new IllegalArgumentException("Direction must be orthogonal");
            }
            return this;
        }

        ;

        public Builder setDirection(Point pTarget, Vector vUp) {
            this.pDirection = pTarget;
            camera.vUp = vUp.normalize();
            return this;
        }

        ;

        public Builder setDirection(Point pTarget) {
            this.pDirection = pTarget;
            camera.vUp = new Vector(0, 1, 0);
            return this;
        }

        ;

        public Builder setVpSize(double width, double height) {
            camera.width = width;
            camera.height = height;
            return this;

        }

        ;

        public Builder setVpDistance(double distance) {
            camera.distance = distance;
            return this;
        }

        ;

        public Builder setResolution(int nX, int nY) {
            return this;
        }

        ;

        private void calculateDirections() {
            if (camera.vUp == null) {
                throw new MissingResourceException("missing render resource", "Camera", "directions");
            }

            if (camera.vTo != null && pDirection == null) {
                camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
                return;
            }

            if (pDirection == null) {
                throw new MissingResourceException("missing render resource", "Camera", "directions");
            }

            try {
                camera.vTo = pDirection.subtract(camera.position).normalize();
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Camera position and target point must not be the same");
            }

            try {
                camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Camera's up direction must not be parallel to its viewing direction");
            }

            // Recalculate vUp to ensure it is orthogonal to both vTo and vRight
            camera.vUp = camera.vRight.crossProduct(camera.vTo).normalize();
        }

        public Camera build() {
            if (camera.position == null) {
                throw new MissingResourceException("missing render resource", "Camera", "location (position)");
            }
            if (alignZero(camera.width) <= 0) {
                throw new MissingResourceException("missing render resource", "Camera", "view plane width");
            }
            if (alignZero(camera.height) <= 0) {
                throw new MissingResourceException("missing render resource", "Camera", "view plane height");
            }
            if (alignZero(camera.distance) <= 0) {
                throw new MissingResourceException("missing render resource", "Camera", "distance from view plane");
            }

            calculateDirections();

            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }

        ;

    }


}



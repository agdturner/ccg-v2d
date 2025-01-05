/*
 * Copyright 2020 Andy Turner, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.leeds.ccg.v2d.geometry.d;

import java.io.Serializable;

/**
 * For 2D Euclidean geometrical objects. The two dimensions have
 * orthogonal axes X, and Y that meet at the origin point {@code<0, 0>}
 * where {@code x=0 and y=0}. The following depicts the origin and
 * dimensions. {@code
 *                          y
 *                          +
 *                          |
 *                          |
 *                          |
 *                          |
 *                          |
 *                          |
 *                          |
 *                          |
 *                          |
 *                          |
 * x - ---------------------|------------------------ + x
 *                          |
 *                          |
 *                          |
 *                          |
 *                          |
 *                          |
 *                          |
 *                          |
 *                          -
 *                          y
 * }
 *
 * @author Andy Turner
 * @version 1.0
 */
public abstract class V2D_GeometryDouble implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The offset used to position a geometry object relative to the
     * {@link V2D_PointDouble#ORIGIN}.
     */
    protected V2D_VectorDouble offset;

    /**
     * Creates a new instance.
     */
    public V2D_GeometryDouble() {
        this(V2D_VectorDouble.ZERO);
    }

    /**
     * Creates a new instance.
     *
     * @param offset What {@link #offset} is set to.
     */
    public V2D_GeometryDouble(V2D_VectorDouble offset) {
        this.offset = offset;
    }

    /**
     * @param pad The padding.
     * @return A padded description.
     */
    protected String toStringFields(String pad) {
        return pad + "offset=" + offset.toString(pad);
    }

    /**
     * @param pad The padding.
     * @return A padded description.
     */
    protected String toStringFieldsSimple(String pad) {
        return pad + "offset=" + offset.toStringSimple("");
    }

    /**
     * Translate (move relative to the origin).
     *
     * @param v The translation vector.
     */
    public void translate(V2D_VectorDouble v) {
        offset = offset.add(v);
    }

    /**
     * Returns the geometry rotated by the angle theta about the point pt. If 
     * theta is positive the angle is clockwise.
     * 
     * @param pt The point about which the geometry is rotated.
     * @param theta The angle of rotation around the the rotation axis in
     * radians.
     * @param epsilon The tolerance within which two vectors are regarded as 
     * equal.
     * @return The rotated geometry.
     */
    public abstract V2D_GeometryDouble rotate(V2D_PointDouble pt, 
            double theta, double epsilon);

    /**
     * For getting an angle between 0 and 2Pi
     * @param theta The angle to be transformed.
     * @return An angle between 0 and 2Pi.
     */
    public double getAngleM(double theta) {
        double twoPi = Math.PI * 2;
        // Change a negative angle into a positive one.
        while (theta < 0d) {
            theta = theta + twoPi;
        }
        // Only rotate less than 2Pi radians.
        while (theta > twoPi) {
            theta = theta - twoPi;
        }
        return theta;
    }
    
}

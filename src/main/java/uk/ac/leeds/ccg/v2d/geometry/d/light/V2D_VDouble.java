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
package uk.ac.leeds.ccg.v2d.geometry.d.light;

import java.io.Serializable;
import java.util.Objects;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_PointDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_VectorDouble;

/**
 * Intended for use as a vector or a coordinate.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_VDouble implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The x.
     */
    public double x;

    /**
     * The y.
     */
    public double y;

    /**
     * The origin {@code <0,0>}.
     */
    public static final V2D_VDouble ZERO = new V2D_VDouble(0, 0);

    /**
     * @param v Used to construct this.
     */
    public V2D_VDouble(V2D_VDouble v) {
        this.x = v.x;
        this.y = v.y;
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     */
    public V2D_VDouble(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param p A point to construct from.
     */
    public V2D_VDouble(V2D_PointDouble p) {
        this(p.getX(), p.getY());
    }

    /**
     * @param v A vector to construct from.
     */
    public V2D_VDouble(V2D_VectorDouble v) {
        this(v.dx, v.dy);
    }

    /**
     * Effectively this becomes a vector of the difference. What needs to be
     * applied to p to get q.
     *
     * @param p The start.
     * @param q The end.
     */
    public V2D_VDouble(V2D_VDouble p, V2D_VDouble q) {
        this(q.x - p.x, q.y - p.y);
    }

    @Override
    public String toString() {
        return toString("");
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of this.
     */
    public String toString(String pad) {
        return this.getClass().getSimpleName() + "\n"
                + pad + "(\n"
                + toStringFields(pad + " ") + "\n"
                + pad + ")";
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    protected String toStringFields(String pad) {
        return pad + "x=" + x + ",\n"
                + pad + "y=" + y;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V2D_VDouble v) {
            return equals(v);
        }
        return false;
    }

    /**
     * Indicates if {@code this} and {@code v} are equal.
     *
     * @param v The instance to test for equality with {@code this}.
     * @return {@code true} iff {@code this} is the same as {@code v}.
     */
    public boolean equals(V2D_VDouble v) {
        return x == v.x && y == v.y;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.x);
        hash = 23 * hash + Objects.hashCode(this.y);
        return hash;
    }

    /**
     * @return {@code true} if {@code this} equals {@link #ZERO}.
     */
    public boolean isZero() {
        return this.equals(ZERO);
    }

    /**
     * Scale by multiplication.
     *
     * @param s The scalar value to multiply this by.
     */
    public void multiply(double s) {
        x = x * s;
        y = y * s;
    }

    /**
     * Scale by division.
     *
     * @param s The scalar value to divide this by.
     */
    public void divide(double s) {
        x = x / s;
        y = y / s;
    }

    /**
     * Add v to this.
     *
     * @param v The coordinate to apply.
     */
    public void translate(V2D_VDouble v) {
        x = x + v.x;
        y = y + v.y;
    }

    /**
     * @return The magnitude.
     */
    public double getMagnitude() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Scales by the magnitude to give a unit vector. (N.B. There is no check to
     * be sure that the resulting vector has a magnitude of less than 1).
     *
     * @return this scaled by the magnitude.
     */
    public V2D_VDouble getUnitVector() {
        double d = getMagnitude();
        V2D_VDouble r = new V2D_VDouble(this);
        r.divide(d);
        return r;
    }

    /**
     * Calculate and return the
     * <A href="https://en.wikipedia.org/wiki/Dot_product">dot product</A>.
     *
     * @param v The other vector.
     * @return dot product
     */
    public double getDotProduct(V2D_VDouble v) {
        return v.x * x + v.y * y;
    }

    /**
     * Get the distance between this and {@code p} assuming both are points.
     *
     * @param p A point.
     * @return The distance from {@code p} to this.
     */
    public double getDistance(V2D_VDouble p) {
        return Math.sqrt(Math.pow(x - p.x, 2d) + Math.pow(y - p.y, 2d));
    }

    /**
     * @return A new vector which is the opposite to {@code this}.
     */
    public V2D_VDouble reverse() {
        return new V2D_VDouble(-x, -y);
    }
}

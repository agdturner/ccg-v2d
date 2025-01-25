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
package uk.ac.leeds.ccg.v2d.geometry.light;

import ch.obermuhlner.math.big.BigRational;
import java.io.Serializable;
import java.math.RoundingMode;
import java.util.Objects;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Point;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Vector;

/**
 * Intended for use as a vector or a coordinate. The idea is that this is 
 * lightweight despite using heavyweight numbers. It is not as heavyweight as 
 * V2D_Vector which uses even more heavyweight Math_BigRationalSqrt numbers 
 * for coordinates.
 * 
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_V implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The x.
     */
    public BigRational x;

    /**
     * The y.
     */
    public BigRational y;

    /**
     * The origin {@code <0,0,0>}.
     */
    public static final V2D_V ZERO = new V2D_V(0, 0, 0);

    /**
     * @param v Used to construct this.
     */
    public V2D_V(V2D_V v) {
        this.x = v.x;
        this.y = v.y;
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     */
    public V2D_V(BigRational x, BigRational y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param dx What {@link #x} is set to after conversion.
     * @param dy What {@link #y} is set to after conversion.
     */
    public V2D_V(long dx, long dy) {
        this(BigRational.valueOf(dx), BigRational.valueOf(dy));
    }

    /**
     * @param dx What {@link #x} is set to after conversion.
     * @param dy What {@link #y} is set to after conversion.
     * @param dz What {@link #z} is set to after conversion.
     */
    public V2D_V(double dx, double dy, double dz) {
        this(BigRational.valueOf(dx), BigRational.valueOf(dy));
    }

    /**
     * @param p A point to construct from.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V2D_V(V2D_Point p, int oom, RoundingMode rm) {
        this(p.getX(oom, rm), p.getY(oom, rm));
    }

    /**
     * @param v A vector to construct from.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V2D_V(V2D_Vector v, int oom, RoundingMode rm) {
        this(v.getDX(oom, rm), v.getDY(oom, rm));
    }

    /**
     * Effectively this becomes a vector of the difference. What needs to be
     * applied to p to get q.
     *
     * @param p The start.
     * @param q The end.
     */
    public V2D_V(V2D_V p, V2D_V q) {
        this(q.x.subtract(p.x), q.y.subtract(p.y));
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
        if (o instanceof V2D_V v) {
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
    public boolean equals(V2D_V v) {
        return x.compareTo(v.x) == 0 && y.compareTo(v.y) == 0;
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
    public void multiply(BigRational s) {
        x = x.multiply(s);
        y = y.multiply(s);
    }

    /**
     * Scale by division.
     *
     * @param s The scalar value to divide this by.
     */
    public void divide(BigRational s) {
        x = x.divide(s);
        y = y.divide(s);
    }

    /**
     * Add v to this.
     *
     * @param v The coordinate to apply.
     */
    public void translate(V2D_V v) {
        x = x.add(v.x);
        y = y.add(v.y);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The magnitude.
     */
    public BigRational getMagnitude(int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(x.pow(2).add(y.pow(2)), oom, rm).getSqrt(oom, rm);
    }

    /**
     * Scales by the magnitude to give a unit vector. (N.B. There is no check to
     * be sure that the resulting vector has a magnitude of less than 1).
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return this scaled by the magnitude.
     */
    public V2D_V getUnitVector(int oom, RoundingMode rm) {
        BigRational d = getMagnitude(oom, RoundingMode.UP);
        V2D_V r = new V2D_V(x.divide(d), y.divide(d));
        return r;
    }

    /**
     * Calculate and return the
     * <A href="https://en.wikipedia.org/wiki/Dot_product">dot product</A>.
     *
     * @param v The other vector.
     * @return dot product
     */
    public BigRational getDotProduct(V2D_V v) {
        return (v.x.multiply(x)).add(v.y.multiply(y));
    }

    /**
     * Get the distance between this and {@code p} assuming both are points.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The distance from {@code p} to this.
     */
    public BigRational getDistance(V2D_V p, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(x.subtract(p.x).pow(2)
                .add(y.subtract(p.y).pow(2))
                .add(y.subtract(p.y).pow(2)), oom, rm).getSqrt(oom, rm);
    }

    /**
     * @return A new vector which is the opposite to {@code this}.
     */
    public V2D_V reverse() {
        return new V2D_V(x.negate(), y.negate());
    }
}

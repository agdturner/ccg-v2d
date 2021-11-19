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
package uk.ac.leeds.ccg.v2d.geometry;

import ch.obermuhlner.math.big.BigRational;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.v2d.core.V2D_Environment;

/**
 * V2D_Vector
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_Vector implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The change in x.
     */
    public final BigRational dx;

    /**
     * The change in y.
     */
    public final BigRational dy;

    /**
     * For storing the magnitude squared.
     */
    public BigRational magnitudeSquared;

    /**
     * For storing the magnitude.
     */
    public BigDecimal magnitude;

    /**
     * @param dx What {@link #dx} is set to.
     * @param dy What {@link #dy} is set to.
     */
    public V2D_Vector(BigRational dx, BigRational dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Creates a vector from the origin to {@code p}
     *
     * @param p the point to which the vector starting at the origin goes.
     */
    public V2D_Vector(V2D_Point p) {
        this.dx = p.x;
        this.dy = p.y;
    }

    /**
     * Creates a vector from {@code p} to {@code q}
     *
     * @param p the point where the vector starts.
     * @param q the point where the vector ends.
     */
    public V2D_Vector(V2D_Point p, V2D_Point q) {
        this.dx = q.x.subtract(p.x);
        this.dy = q.y.subtract(p.y);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(dx=" + dx + ", dy=" + dy + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V2D_Vector) {
            V2D_Vector v = (V2D_Vector) o;
            if (dx.compareTo(v.dx) == 0 && dy.compareTo(v.dy) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.dx);
        hash = 23 * hash + Objects.hashCode(this.dy);
        return hash;
    }

    /**
     * @return {@code true} if {@code this.equals(V2D_Environment.ZERO_VECTOR)}
     */
    public boolean isZeroVector() {
        return this.equals(V2D_Environment.ZERO_VECTOR);
    }

    /**
     * @param s The scalar value to multiply this by.
     * @return Scaled vector.
     */
    public V2D_Vector multiply(BigRational s) {
        return new V2D_Vector(dx.multiply(s), dy.multiply(s));
    }

    /**
     * @param v The vector to add.
     * @return A new vector which is this add {@code v}.
     */
    public V2D_Vector add(V2D_Vector v) {
        return new V2D_Vector(dx.add(v.dx), dy.add(v.dy));
    }

    /**
     * @param v The vector to subtract.
     * @return A new vector which is this subtract {@code v}.
     */
    public V2D_Vector subtract(V2D_Vector v) {
        return new V2D_Vector(dx.subtract(v.dx), dy.subtract(v.dy));
    }

    /**
     * Calculate and return the
     * <A href="https://en.wikipedia.org/wiki/Dot_product">dot product</A>.
     *
     * @param v V2D_Vector
     * @return dot product
     */
    public BigRational getDotProduct(V2D_Vector v) {
        return (v.dx.multiply(this.dx)).add(v.dy.multiply(this.dy));
    }

    /**
     * Test if this is orthogonal to {@code v}.
     *
     * @param v The
     * @return {@code true} if this and {@code v} are orthogonal.
     */
    public boolean isOrthogonal(V2D_Vector v) {
        return getDotProduct(v).isZero();
    }

    /**
     * If {@code null}, then initialise {@link #magnitudeSquared} and return it.
     *
     * @return {@link #magnitudeSquared} after initialising it if it is
     * {@code null}.
     */
    public BigRational getMagnitudeSquared() {
        if (magnitudeSquared == null) {
            magnitudeSquared = dx.multiply(dx).add(dy.multiply(dy));
        }
        return magnitudeSquared;
    }

    /**
     * Get the magnitude of the vector at the given scale.
     *
     * @param oom The scale for the precision of the result.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #magnitude} initialised with {@code scale} and {@code rm}.
     */
    public BigDecimal getMagnitude(int oom, RoundingMode rm) {
        if (magnitude == null) {
            return initMagnitude(oom, rm);
        }
        if (magnitude.scale() > oom) {
            return magnitude.setScale(oom);
        } else {
            return initMagnitude(oom, rm);
        }
    }

    /**
     * @param scale The scale for the precision of the result.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #magnitude} initialised with {@code scale} and {@code rm}.
     */
    protected BigDecimal initMagnitude(int scale, RoundingMode rm) {
        magnitude = Math_BigDecimal.sqrt(getMagnitudeSquared().toBigDecimal(), 
                scale, rm);
        return magnitude;
    }

    /**
     * Test if this is parallel to {@code v}.
     *
     * @param v The vector to test if it is parallel to this.
     * @return {@code true} if this and {@code v} are orthogonal.
     */
    public boolean isParallel(V2D_Vector v) {
        if (dx.isZero()
                && dy.isZero()) {
            return false;
        }
        if (v.dx.isZero()
                && v.dy.isZero()) {
            return false;
        }
        if (dx.isZero() && !v.dx.isZero()) {
            return false;
        }
        if (dy.isZero() && !v.dy.isZero()) {
            return false;
        }
        if (dx.isZero() && v.dx.isZero()) {
            return true;
        }
        if (dy.isZero() && v.dy.isZero()) {
            return true;
        }
        if (v.dx.isZero()) {
            BigRational c = v.dx.divide(dx);
            return c.multiply(dy).subtract(v.dy).abs().isZero();
        } else {
            BigRational c = dx.divide(v.dx);
            return c.multiply(dy).subtract(v.dy).abs().isZero();
        }
    }

    /**
     * Calculate and return the
     * <A href="https://en.wikipedia.org/wiki/Cross_product">cross product</A>.
     * Treat this as the first vector and {@code v} as the second vector. The
     * resulting vector is in the direction given by the right hand rule.
     *
     * @param v V2D_Vector
     * @return V2D_Vector
     */
    public V2D_Vector getCrossProduct(V2D_Vector v) {
        return new V2D_Vector(dx.multiply(v.dy), dy.multiply(v.dx));
    }

    /**
     * Scales the vector by the magnitude so that it has length 1
     *
     * @param scale The scale for the precision of the result.
     * @param rm The RoundingMode for any rounding.
     * @return this scaled by the magnitude.
     */
    public V2D_Vector getUnitVector(int scale, RoundingMode rm) {
        BigRational m = BigRational.valueOf(getMagnitude(scale + 2, rm));
        return new V2D_Vector(dx.divide(m), dy.divide(m));
    }

    /**
     * @return A vector which is orthogonal to this.
     */
    public V2D_Vector getOrthogonalVector() {
        return new V2D_Vector(dy, dx);
    }
}

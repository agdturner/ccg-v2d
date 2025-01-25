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

import ch.obermuhlner.math.big.BigDecimalMath;
import ch.obermuhlner.math.big.BigRational;
import java.io.Serializable;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigRational;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.math.number.Math_Quaternion_BigRational;
import uk.ac.leeds.ccg.v2d.core.V2D_Environment;
import uk.ac.leeds.ccg.v2d.geometry.light.V2D_V;

/**
 * A vector used to translate geometries. The magnitude of the vector is not
 * generally calculated and stored, but this is done so upon request to an Order
 * of Magnitude. Other than the magnitude which may be calculated and stored at
 * higher levels of precision, instances are immutable.
 *
 * @author Andy Turner
 * @version 1.1
 */
public class V2D_Vector implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The change in x.
     */
    protected final Math_BigRationalSqrt dx;

    /**
     * The change in y.
     */
    protected final Math_BigRationalSqrt dy;

    /**
     * For storing the magnitude.
     */
    protected Math_BigRationalSqrt m;

    /**
     * The zero vector {@code <0,0>} where:
     * {@link #dx} = {@link #dy} = 0.
     */
    public static final V2D_Vector ZERO = new V2D_Vector(0, 0);

    /**
     * The I vector {@code <1,0>}.
     */
    public static final V2D_Vector I = new V2D_Vector(1, 0);

    /**
     * The J vector {@code <0,1>}.
     */
    public static final V2D_Vector J = new V2D_Vector(0, 1);

    /**
     * The IJ vector {@code <1,1>}.
     */
    public static final V2D_Vector IJ = new V2D_Vector(1, 1);

    /**
     * The I vector {@code <-1,0>}.
     */
    public static final V2D_Vector NI = new V2D_Vector(-1, 0);

    /**
     * The J vector {@code <0,-1>}.
     */
    public static final V2D_Vector NJ = new V2D_Vector(0, -1);
    
    /**
     * The IJ vector {@code <1,-1>}.
     */
    public static final V2D_Vector INJ = new V2D_Vector(1, -1);

    /**
     * The IJ vector {@code <-1,1>}.
     */
    public static final V2D_Vector NIJ = new V2D_Vector(-1, 1);

    /**
     * The IJ vector {@code <-1,-1>}.
     */
    public static final V2D_Vector NINJ = new V2D_Vector(-1, -1);

    /**
     * Create a new instance.
     */
    public V2D_Vector() {
        this.dx = Math_BigRationalSqrt.ZERO;
        this.dy = Math_BigRationalSqrt.ZERO;
    }

    /**
     * Create a new instance.
     *
     * @param v Used to initialise this. A deep copy of all components is made
     * so that {@code this} is completely independent of {@code v}.
     */
    public V2D_Vector(V2D_Vector v) {
        this.dx = v.dx;
        this.dy = v.dy;
    }

    /**
     * Create a new instance.
     *
     * @param dx What {@link #dx} is set to.
     * @param dy What {@link #dy} is set to.
     */
    public V2D_Vector(Math_BigRationalSqrt dx, Math_BigRationalSqrt dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * @param dx Used to initialise {@link #dx}.
     * @param dy Used to initialise {@link #dy}.
     */
    public V2D_Vector(BigRational dx, BigRational dy) {
        this.dx = new Math_BigRationalSqrt(dx.pow(2), dx);
        this.dy = new Math_BigRationalSqrt(dy.pow(2), dy);
    }

    /**
     * @param dx Used to initialise {@link #dx}.
     * @param dy Used to initialise {@link #dy}.
     * @param m What {@link #m} is set to.
     */
    public V2D_Vector(BigRational dx, BigRational dy,
            Math_BigRationalSqrt m) {
        this(dx, dy);
        this.m = m;
    }

    /**
     * @param dx What {@link #dx} is set to.
     * @param dy Used to initialise {@link #dy}.
     */
    public V2D_Vector(Math_BigRationalSqrt dx, BigRational dy) {
        this.dx = dx;
        this.dy = new Math_BigRationalSqrt(dy.pow(2), dy);
    }

    /**
     * @param dx Used to initialise {@link #dx}.
     * @param dy What {@link #dy} is set to.
     */
    public V2D_Vector(BigRational dx, Math_BigRationalSqrt dy) {
        this.dx = new Math_BigRationalSqrt(dx.pow(2), dx);
        this.dy = dy;
    }

    /**
     * @param dx What {@link #dx} is set to.
     * @param dy What {@link #dy} is set to.
     */
    public V2D_Vector(long dx, long dy) {
        this(BigRational.valueOf(dx),
                BigRational.valueOf(dy));
    }

    /**
     * @param dx What {@link #dx} is set to.
     * @param dy What {@link #dy} is set to.
     */
    public V2D_Vector(double dx, double dy) {
        this(BigRational.valueOf(dx),
                BigRational.valueOf(dy));
    }

    /**
     * Creates a vector from {@code p} to {@code q}.
     *
     * @param p the point where the vector starts.
     * @param q the point where the vector ends.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode used in the calculation.
     */
    public V2D_Vector(V2D_Point p, V2D_Point q, int oom, RoundingMode rm) {
        this(q.getX(oom, rm).subtract(p.getX(oom, rm)),
                q.getY(oom, rm).subtract(p.getY(oom, rm)));
    }

    /**
     * Creates a vector from the Origin to {@code p} to {@code q}.
     *
     * @param p the point where the vector starts.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode used in the calculation.
     */
    public V2D_Vector(V2D_Point p, int oom, RoundingMode rm) {
        this(p.getVector(oom, rm));
    }

    /**
     * Creates a vector from {@code v}.
     *
     * @param v the V2D_V vector.
     */
    public V2D_Vector(V2D_V v) {
        this(v.x, v.y);
    }

    @Override
    public String toString() {
        //return toString("");
        return toStringSimple("");
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
     * @return A description of this.
     */
    public String toStringSimple(String pad) {
        return pad + this.getClass().getSimpleName()
                + "(" + toStringFieldsSimple("") + ")";
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    protected String toStringFields(String pad) {
        return pad + "dx=" + dx + ",\n"
                + pad + "dy=" + dy;
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    protected String toStringFieldsSimple(String pad) {
        return pad + "dx=" + dx.toStringSimple()
                + ", dy=" + dy.toStringSimple();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj instanceof V2D_Vector v3D_Vector) {
            return equals(v3D_Vector);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.dx);
        hash = 89 * hash + Objects.hashCode(this.dy);
        return hash;
    }

    /**
     * Indicates if {@code this} and {@code v} are equal.
     *
     * @param v The vector to test for equality with {@code this}.
     * @return {@code true} iff {@code this} is the same as {@code v}.
     */
    public boolean equals(V2D_Vector v) {
        return dx.compareTo(v.dx) == 0
                && dy.compareTo(v.dy) == 0;
    }

    /**
     * Indicates if {@code this} and {@code v} are equal at the oom precision
     * using the RoundingMode rm.
     *
     * @param v The vector to test for equality with {@code this}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode used in the calculation.
     * @return {@code true} iff {@code this} is the same as {@code v}.
     */
    public boolean equals(V2D_Vector v, int oom, RoundingMode rm) {
        if (getDX(oom, rm).compareTo(v.getDX(oom, rm)) == 0) {
            if (getDY(oom, rm).compareTo(v.getDY(oom, rm)) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Indicates if {@code this} is the reverse of {@code v}.
     *
     * @param v The vector to compare with {@code this}.
     * @return {@code true} iff {@code this} is the reverse of {@code v}.
     */
    public boolean isReverse(V2D_Vector v) {
        return equals(v.reverse());
    }

    /**
     * @return {@code true} if {@code this.equals(e.zeroVector)}
     */
    public boolean isZero() {
        return this.equals(ZERO);
    }

    /**
     * @return {@code true} if {@code this.equals(e.zeroVector)}
     */
    public boolean isZero(int oom, RoundingMode rm) {
        return this.equals(ZERO, oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode used in the calculation.
     * @return The value of {@link #dx} as a BigRational.
     */
    public BigRational getDX(int oom, RoundingMode rm) {
        return dx.getSqrt(oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode used in the calculation.
     * @return The value of {@link #dy} as a BigRational.
     */
    public BigRational getDY(int oom, RoundingMode rm) {
        return dy.getSqrt(oom, rm);
    }

    /**
     * @return {@link #dx}
     */
    public Math_BigRationalSqrt getDX() {
        return dx;
    }

    /**
     * @return {@link #dy}
     */
    public Math_BigRationalSqrt getDY() {
        return dy;
    }

    /**
     * @param s The scalar value to multiply this by.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode used in the calculation.
     * @return Scaled vector.
     */
    public V2D_Vector multiply(long s, int oom, RoundingMode rm) {
        return multiply(BigRational.valueOf(s), oom, rm);
    }

    /**
     * @param s The scalar value to multiply this by.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode used in the calculation.
     * @return Scaled vector.
     */
    public V2D_Vector multiply(BigRational s, int oom, RoundingMode rm) {
        return new V2D_Vector(
                getDX(oom, rm).multiply(s),
                getDY(oom, rm).multiply(s));
    }

    /**
     * @param s The scalar value to divide this by.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode used in the calculation.
     * @return Scaled vector.
     */
    public V2D_Vector divide(BigRational s, int oom, RoundingMode rm) {
        return new V2D_Vector(
                getDX(oom, rm).divide(s),
                getDY(oom, rm).divide(s));
    }

    /**
     * Add/apply/translate.
     *
     * @param v The vector to add.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A new vector which is {@code this} add {@code v}.
     */
    public V2D_Vector add(V2D_Vector v, int oom, RoundingMode rm) {
        return new V2D_Vector(
                getDX(oom, rm).add(v.getDX(oom, rm)),
                getDY(oom, rm).add(v.getDY(oom, rm)));
    }

    /**
     * @param v The vector to subtract.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A new vector which is {@code this} minus {@code v}.
     */
    public V2D_Vector subtract(V2D_Vector v, int oom, RoundingMode rm) {
        return new V2D_Vector(
                getDX(oom, rm).subtract(v.getDX(oom, rm)),
                getDY(oom, rm).subtract(v.getDY(oom, rm)));
    }

    /**
     * @return A new vector which is the opposite to {@code this}.
     */
    public V2D_Vector reverse() {
        return new V2D_Vector(dx.negate(), dy.negate());
    }

    /**
     * Calculate and return the
     * <A href="https://en.wikipedia.org/wiki/Dot_product">dot product</A>.
     *
     * @param v V2D_Vector
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return dot product
     */
    public BigRational getDotProduct(V2D_Vector v, int oom,
            RoundingMode rm) {
        return (v.getDX(oom, rm).multiply(getDX(oom, rm)))
                .add(v.getDY(oom, rm).multiply(getDY(oom, rm)));
    }

    /**
     * Test if this is orthogonal to {@code v}.
     *
     * @param v The vector to test for orthogonality with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this and {@code v} are orthogonal.
     */
    public boolean isOrthogonal(V2D_Vector v, int oom, RoundingMode rm) {
        // Special case
        if (isScalarMultiple(v, oom, rm)) {
            return false;
        }
        return getDotProduct(v, oom, rm).isZero();
    }

    /**
     * @return The magnitude of the vector squared.
     */
    public BigRational getMagnitudeSquared() {
        return getMagnitude().getX();
    }

    /**
     * @return The magnitude of m.
     */
    private Math_BigRationalSqrt getMagnitude() {
        if (m == null) {
            initM(V2D_Environment.DEFAULT_OOM, V2D_Environment.DEFAULT_RM);
        }
        return m;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    private void initM(int oom, RoundingMode rm) {
        m = new Math_BigRationalSqrt(dx.getX().add(dy.getX()), oom, rm);
    }

    /**
     * Returns the magnitude of m to at least oom precision.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The magnitude of m.
     */
    protected Math_BigRationalSqrt getMagnitude(int oom, RoundingMode rm) {
        if (m == null) {
            initM(oom, rm);
        } else {
            if (oom < m.getOom()) {
                initM(oom, rm);
            } else {
                if (m.getRoundingMode().equals(rm)) {
                    return m;
                }
                initM(oom, rm);
            }
        }
        return m;
    }
    
    /**
     * Test if {@code v} is a scalar multiple of {@code this}.
     *
     * @param v The vector to test if it is a scalar multiple of {@code this}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if {@code this} and {@code v} are scalar multiples.
     */
    public boolean isScalarMultiple(V2D_Vector v, int oom, RoundingMode rm) {
        if (equals(v)) {
            return true;
        } else {
            // Special cases
            boolean isZero = isZero();
            if (isZero) {
                /**
                 * Can't multiply the zero vector by a scalar to get a non-zero
                 * vector.
                 */
                return v.isZero();
            }
            if (v.isZero()) {
                /**
                 * Already tested that this is not equal to v, so the scalar is
                 * zero.
                 */
                return true;
            }
            if (dx.compareTo(Math_BigRationalSqrt.ZERO) == 0D) {
                if (v.dx.compareTo(Math_BigRationalSqrt.ZERO) == 0D) {
                    return true;
                }
            }
            if (dy.compareTo(Math_BigRationalSqrt.ZERO) == 0D) {
                if (v.dy.compareTo(Math_BigRationalSqrt.ZERO) == 0D) {
                    return true;
                }
            }
            return (v.dx.divide(dx, oom, rm)).compareTo(v.dy.divide(dy, oom, rm)) == 0;
        }
    }

    /**
     * Compute and return the angle (in radians) between {@code #this} and
     * {@code v}. The algorithm is to:
     * <ol>
     * <li>Find the dot product of the vectors.</li>
     * <li>Divide the dot product with the magnitude of the first vector.</li>
     * <li>the second vector.</li>
     * </ol>
     *
     * @param v The vector to find the angle between.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The angle in radians between {@code this} and {@code v}.
     */
    public BigRational getAngle(V2D_Vector v, int oom, RoundingMode rm) {
        int oomn2 = oom - 2;
        BigRational dp = getDotProduct(v, oomn2, rm);
        BigRational mag = getMagnitude(oomn2, rm).getSqrt(oomn2, rm);
        BigRational vmag = v.getMagnitude(oomn2, rm).getSqrt(oomn2, rm);
        MathContext mc = new MathContext(1 - oom); // This needs checking!
        return BigRational.valueOf(BigDecimalMath.acos(
                dp.divide(mag.multiply(vmag)).toBigDecimal(mc), mc));
    }

    /**
     * Calculate and return {@code #this} rotated using the parameters.(see
 Doug (https://math.stackexchange.com/users/102399/doug), How do you
 rotate a vector by a unit quaternion?, URL (version: 2019-06-12):
 https://math.stackexchange.com/q/535223)
     *
     * @param pt The point of rotatio.
     * @param theta The angle of rotation.
     * @param bd The Math_BigDecimal.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The vector which is {@code #this} rotated using the parameters.
     */
    public V2D_Vector rotate(V2D_Point pt, BigRational theta, 
            Math_BigDecimal bd, int oom, RoundingMode rm) {
        theta = Math_AngleBigRational.normalise(theta, bd, oom, rm);
        if (theta.compareTo(BigRational.ZERO) == 0d) {
            return new V2D_Vector(this);
        } else {
            // Rotate counter clockwise
//            BigRational dx2 = dx.getX().multiply(
//                    Math_BigRational.cos(theta, bd.bi, oom, rm)).subtract(
//                            dy.getX().multiply(
//                    Math_BigRational.sin(theta, bd.bi, oom, rm)));
//            BigRational dy2 = dy.getX().multiply(
//                    Math_BigRational.cos(theta, bd.bi, oom, rm)).add(
//                            dx.getX().multiply(
//                    Math_BigRational.sin(theta, bd.bi, oom, rm)));
            // Rotate clockwise
            //BigRational dxr = dx.getX();
            //BigRational dxr = dx.getSqrt();
            BigRational dxr = dx.getSqrt(oom, rm);
            BigRational dyr = dy.getSqrt(oom, rm);
            BigRational dx2 = dxr.multiply(
                    Math_BigRational.cos(theta, bd.bi, oom, rm)).add(
                            dyr.multiply(
                    Math_BigRational.sin(theta, bd.bi, oom, rm)));
            BigRational dy2 = dyr.multiply(
                    Math_BigRational.cos(theta, bd.bi, oom, rm)).subtract(
                            dxr.multiply(
                    Math_BigRational.sin(theta, bd.bi, oom, rm)));
            return new V2D_Vector(dx2, dy2);
        }
    }
    
    /**
     * Calculate and return {@code #this} rotated 90 degrees clockwise.
     *
     * @return A new vector which is this rotated 90 degrees.
     */
    public V2D_Vector rotate90() {
        return new V2D_Vector(dy.negate(), dx);
    }

    /**
     * Scales by {@link #m} to give a unit vector with length 1. Six further
     * orders of magnitude are used to produce the result.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return this scaled by {@link #m}.
     */
    public V2D_Vector getUnitVector(int oom, RoundingMode rm) {
        BigRational d = getMagnitude(oom, rm).getSqrt(oom, rm);
//        return new V2D_Vector(
//                dx.getSqrt(oom).divide(d),
//                dy.getSqrt(oom).divide(d),
//                dz.getSqrt(oom).divide(d), oom);
//        /**
//         * Force the magnitude to be equal to one.
//         */
//        return new V2D_Vector(
//                getDX(oomn6, rm).divide(d),
//                getDY(oomn6, rm).divide(d),
//                getDZ(oomn6, rm).divide(d), Math_BigRationalSqrt.ONE);
        return new V2D_Vector(getDX(oom, rm).divide(d),
                getDY(oom, rm).divide(d), Math_BigRationalSqrt.ONE);
    }

    /**
     * The unit vector direction is given as being towards the point.
     *
     * @param pt The point controlling the direction of the unit vector.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return this scaled by {@link #m}.
     */
    public V2D_Vector getUnitVector(V2D_Point pt, int oom, RoundingMode rm) {
        int direction = (getDotProduct(pt.getVector(oom, rm), oom, rm)
                .divide(getDotProduct(this, oom, rm)))
                .compareTo(BigRational.ZERO);
        V2D_Vector r = getUnitVector(oom, rm);
        if (direction == -1) {
            r = r.reverse();
        }
        return r;
    }

    /**
     * @return The direction of the vector:
     * <Table>
     * <caption>Directions</caption>
     * <thead>
     * <tr><td>ID</td><td>Description (P is positive, N is Negative)</td></tr>
     * </thead>
     * <tbody>
     * <tr><td>0</td><td>0dx, 0dy</td></tr>
     * <tr><td>1</td><td>Pdx, Pdy</td></tr>
     * <tr><td>2</td><td>Pdx, Ndy</td></tr>
     * <tr><td>3</td><td>Ndx, Pdy</td></tr>
     * <tr><td>4</td><td>Ndx, Ndy</td></tr>
     * </tbody>
     * </Table>
     */
    public int getDirection() {
        if (dx.compareTo(Math_BigRationalSqrt.ZERO) != -1) {
            if (dy.compareTo(Math_BigRationalSqrt.ZERO) != -1) {
                if (isZero()) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                return 2;
            }
        } else {
            if (dy.compareTo(Math_BigRationalSqrt.ZERO) != -1) {
                return 3;
            } else {
                return 4;
            }
        }
    }
}

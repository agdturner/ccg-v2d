/*
 * Copyright 2019 Andy Turner, University of Leeds.
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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v2d.core.V2D_Environment;
import uk.ac.leeds.ccg.v2d.geometry.envelope.V2D_Envelope;

/**
 * 2D points.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_Point extends V2D_Geometry implements V2D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * The x coordinate.
     */
    public Math_BigRational x;

    /**
     * The y coordinate.
     */
    public Math_BigRational y;

    /**
     * @param p Vector_Point
     */
    public V2D_Point(V2D_Point p) {
        x = p.x;
        y = p.y;
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     */
    public V2D_Point(Math_BigRational x, Math_BigRational y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     */
    public V2D_Point(BigDecimal x, BigDecimal y) {
        this.x = Math_BigRational.valueOf(x);
        this.y = Math_BigRational.valueOf(y);
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     */
    public V2D_Point(BigInteger x, BigInteger y) {
        this.x = Math_BigRational.valueOf(x);
        this.y = Math_BigRational.valueOf(y);
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     */
    public V2D_Point(double x, double y) {
        this.x = Math_BigRational.valueOf(x);
        this.y = Math_BigRational.valueOf(y);
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     */
    public V2D_Point(float x, float y) {
        this.x = Math_BigRational.valueOf(x);
        this.y = Math_BigRational.valueOf(y);
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     */
    public V2D_Point(long x, long y) {
        this.x = Math_BigRational.valueOf(x);
        this.y = Math_BigRational.valueOf(y);
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     */
    public V2D_Point(int x, int y) {
        this.x = Math_BigRational.valueOf(x);
        this.y = Math_BigRational.valueOf(y);
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     */
    public V2D_Point(short x, short y) {
        this.x = Math_BigRational.valueOf(x);
        this.y = Math_BigRational.valueOf(y);
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     */
    public V2D_Point(byte x, byte y) {
        this.x = Math_BigRational.valueOf(x);
        this.y = Math_BigRational.valueOf(y);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(x=" + x.toString() + " y="
                + y.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V2D_Point) {
            return equals((V2D_Point) o);
        }
        return false;
    }

    /**
     * @param p The point to test for equality with this.
     * @return {@code true} if and only if the points are coincident.
     */
    public boolean equals(V2D_Point p) {
        if (p.x.compareTo(x) == 0) {
            if (p.y.compareTo(y) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.x != null ? this.x.hashCode() : 0);
        hash = 67 * hash + (this.y != null ? this.y.hashCode() : 0);
        return hash;
    }

    /**
     * Get the distance squared between this and {@code p}.
     *
     * @param p A point.
     * @return The distance squared from {@code p} to this.
     */
    public Math_BigRational getDistanceSquared(V2D_Point p) {
        return x.subtract(p.x).pow(2).add(y.subtract(p.y).pow(2));
    }

    /**
     * Get the distance between this and {@code p}.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision.
     * @return The distance from {@code p} to this.
     */
    public Math_BigRational getDistance(V2D_Point p, int oom) {
        if (this.equals(p)) {
            return Math_BigRational.ZERO;
        }
        return new Math_BigRationalSqrt(getDistanceSquared(p), oom).getSqrt(oom);
    }

    /**
     * Get the angle from {@code p} to the Y-Axis clockwise.
     *
     * @param p A point.
     * @return Angle to the y axis clockwise.
     */
    @Deprecated
    public double getAngle_double(V2D_Point p) {
        double dx = p.x.toDouble() - x.toDouble();
        double dy = p.y.toDouble() - y.toDouble();
        if (dy == 0.0d) {
            if (dx == 0.0d) {
                return 0.0d;
            } else {
                if (dx > 0.0d) {
                    return Math.PI / 2.0d;
                } else {
                    return (3.0d * Math.PI) / 2.0d;
                }
            }
        } else {
            if (dy > 0.0d) {
                if (dx == 0.0d) {
                    return 0.0d;
                } else {
                    if (dx > 0.0d) {
                        return Math.atan(dx / dy);
                    } else {
                        return (2.0d * Math.PI) - Math.atan(Math.abs(dx) / dy);
                    }
                }
            } else {
                // dy < 0.0d
                if (dx == 0.0d) {
                    return Math.PI;
                } else {
                    if (dx > 0.0d) {
                        return Math.PI - Math.atan(dx / Math.abs(dy));
                    } else {
                        return Math.PI + Math.atan(Math.abs(dx) / Math.abs(dy));
                    }
                }
            }
        }
    }

    /**
     * Get the angle from {@code p} to the Y-Axis clockwise.Uses BigMath default
     * precision.
     *
     * @param p A point.
     * @param e V2D_Environment
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for the calculation.
     * @return Angle to the Y-Axis clockwise. Default 0.0d.
     */
    public BigDecimal getAngle(V2D_Point p, V2D_Environment e, int oom,
            RoundingMode rm) {
        Math_BigRational dx = p.x.subtract(x);
        Math_BigRational dy = p.y.subtract(y);
        if (dy.compareTo(Math_BigRational.ZERO) == 0) {
            if (dx.compareTo(Math_BigRational.ZERO) == 0) {
                return BigDecimal.ZERO;
            } else {
                BigDecimal piBy2 = e.bd.getPiBy2(oom, rm);
                if (dx.compareTo(Math_BigRational.ZERO) == 1) {
                    return piBy2;
                } else {
                    return BigDecimal.valueOf(3).multiply(piBy2);
                }
            }
        } else {
            if (dy.compareTo(Math_BigRational.ZERO) == 1) {
                if (dx.compareTo(Math_BigRational.ZERO) == 0) {
                    return BigDecimal.ZERO;
                } else {
                    if (dx.compareTo(Math_BigRational.ZERO) == 1) {
                        //return Math.BigMath.ATAN.invoke(BigMath.DIVIDE.invoke(dx, dy));
                        return BigDecimalMath.atan(dx.divide(dy).toBigDecimal(oom - 2),
                                new MathContext(oom - 1, rm));
                    } else {
                        return BigDecimalMath.atan(dx.abs().divide(dy).toBigDecimal(oom - 2),
                                new MathContext(oom - 1, rm));
//                        return BigMath.SUBTRACT.invoke(BigMath.TWO_PI,
//                                BigMath.ATAN.invoke(BigMath.DIVIDE.invoke(
//                                        BigMath.ABS.invoke(dx), dy)));
                        //return (2.0d * Math.PI) - Math.atan(Math.abs(dx) / dy);
                    }
                }
            } else {
                // dy < 0.0d
                BigDecimal pi = e.bd.getPi(oom, rm);
                if (dx.compareTo(Math_BigRational.ZERO) == 0) {
                    return pi;
                } else {
                    MathContext mc = new MathContext(1 - oom, rm); // TODO: Check MathContext
                    if (dx.compareTo(Math_BigRational.ZERO) == 1) {
                        return pi.subtract(BigDecimalMath.atan(dx.divide(
                                dy.abs()).toBigDecimal(oom - 2), mc));
                    } else {
                        return pi.add(BigDecimalMath.atan(dx.abs().divide(
                                dy.abs()).toBigDecimal(oom - 2), mc));
                    }
                }
            }
        }
    }

    /**
     * Rise over run.
     * 
     * @param p The other point.
     * @return The gradient.
     * @deprecated
     */
    @Deprecated
    public Math_BigRational getGradient(V2D_Point p) {
        Math_BigRational dx = x.subtract(p.x);
        Math_BigRational dy = y.subtract(p.y);
        if (dy.compareTo(Math_BigRational.ZERO) == 0) {
            return Math_BigRational.ONE;
        } else {
            if (dx.compareTo(Math_BigRational.ZERO) == 0) {
                return Math_BigRational.ZERO;
            }
            return dx.divide(dy);
        }
    }

    @Override
    public V2D_Envelope getEnvelope() {
        return new V2D_Envelope(x, y);
    }

    @Override
    public boolean isIntersectedBy(V2D_Point p) {
        return this.equals(p);
    }

    @Override
    public V2D_Geometry getIntersection(V2D_Line l) {
        if (l.isIntersectedBy(this)) {
            return this;
        }
        return null;
    }

    @Override
    public V2D_Geometry getIntersection(V2D_LineSegment l, boolean b) {
        if (l.isIntersectedBy(this)) {
            return this;
        }
        return null;
    }

    @Override
    public boolean isIntersectedBy(V2D_Line l) {
        return l.isIntersectedBy(this);
    }

    @Override
    public boolean isIntersectedBy(V2D_LineSegment l, boolean b) {
        return l.isIntersectedBy(this);
    }

    @Override
    public boolean isEnvelopeIntersectedBy(V2D_Line l) {
        return l.isIntersectedBy(this);
    }
}

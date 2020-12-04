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
import ch.obermuhlner.math.big.BigRational;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import uk.ac.leeds.ccg.math.Math_BigDecimal;
import uk.ac.leeds.ccg.v2d.core.V2D_Environment;
import uk.ac.leeds.ccg.v2d.geometry.envelope.V2D_Envelope;

/**
 * 2D points.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_Point extends V2D_Geometry implements V2D_FiniteGeometry,
        Comparable<V2D_Point> {

    private static final long serialVersionUID = 1L;

    /**
     * The x coordinate.
     */
    public BigRational x;

    /**
     * The y coordinate.
     */
    public BigRational y;

    /**
     * @param p Vector_Point2D
     */
    public V2D_Point(V2D_Point p) {
        x = p.x;
        y = p.y;
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     */
    public V2D_Point(BigRational x, BigRational y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     */
    public V2D_Point(BigDecimal x, BigDecimal y) {
        this.x = BigRational.valueOf(x);
        this.y = BigRational.valueOf(y);
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     */
    public V2D_Point(BigInteger x, BigInteger y) {
        this.x = BigRational.valueOf(x);
        this.y = BigRational.valueOf(y);
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     */
    public V2D_Point(double x, double y) {
        this.x = BigRational.valueOf(x);
        this.y = BigRational.valueOf(y);
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     */
    public V2D_Point(float x, float y) {
        this.x = BigRational.valueOf(x);
        this.y = BigRational.valueOf(y);
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     */
    public V2D_Point(long x, long y) {
        this.x = BigRational.valueOf(x);
        this.y = BigRational.valueOf(y);
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     */
    public V2D_Point(int x, int y) {
        this.x = BigRational.valueOf(x);
        this.y = BigRational.valueOf(y);
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     */
    public V2D_Point(short x, short y) {
        this.x = BigRational.valueOf(x);
        this.y = BigRational.valueOf(y);
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     */
    public V2D_Point(byte x, byte y) {
        this.x = BigRational.valueOf(x);
        this.y = BigRational.valueOf(y);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(x=" + x.toString() + " y="
                + y.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V2D_Point) {
            V2D_Point p = (V2D_Point) o;
            if (p.x.compareTo(x) == 0) {
                if (p.y.compareTo(y) == 0) {
                    return true;
                }
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
     * @param p V2D_Point to compare to.
     * @return 0 if this is the same as o and +1 or -1 otherwise.
     */
    @Override
    public int compareTo(V2D_Point p) {
        int xcomp = x.compareTo(p.x);
        if (xcomp == 0) {
            return y.compareTo(p.y);
        }
        return xcomp;
    }

    /**
     * Get the distance squared between this and {@code p}.
     *
     * @param p A point.
     * @return The distance squared from {@code p} to this.
     */
    public BigRational getDistanceSquared(V2D_Point p) {
        BigRational dx = this.x.subtract(p.x);
        BigRational dy = this.y.subtract(p.y);
        return dx.pow(2).add(dy.pow(2));
    }

    /**
     * Get the distance between this and {@code p}.
     *
     * @param p A point.
     * @param scale The scale. A positive value gives the number of decimal
     * places. A negative value rounds to the left of the decimal point.
     * @param rm The RoundingMode for the calculation.
     * @return The distance from {@code p} to this.
     */
    public BigDecimal getDistance(V2D_Point p, int scale, RoundingMode rm) {
        if (this.equals(p)) {
            return BigDecimal.ZERO;
        }
        return Math_BigDecimal.sqrt(getDistanceSquared(p).toBigDecimal(), scale,
                rm);
    }

    /**
     * Get the angle from {@code p} to the Y-Axis clockwise.
     *
     * @param p A point.
     * @return Angle to the y axis clockwise.
     */
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
     * @param rm The RoundingMode for the calculation.
     * @param dp The number of decimal places the result is accurate to.
     * @return Angle to the Y-Axis clockwise. Default 0.0d.
     */
    public BigDecimal getAngle(V2D_Point p, V2D_Environment e, int dp,
            RoundingMode rm) {
        BigRational dx = p.x.subtract(x);
        BigRational dy = p.y.subtract(y);
        if (dy.compareTo(BigRational.ZERO) == 0) {
            if (dx.compareTo(BigRational.ZERO) == 0) {
                return BigDecimal.ZERO;
            } else {
                if (dx.compareTo(BigRational.ZERO) == 1) {
                    return Math_BigDecimal.divideRoundIfNecessary(
                            e.bd.getPi(dp, rm),
                            BigDecimal.valueOf(2), dp, rm);
                } else {
                    return BigDecimal.valueOf(3).multiply(e.bd.getPiBy2(dp, rm));
                }
            }
        } else {
            if (dy.compareTo(BigRational.ZERO) == 1) {
                if (dx.compareTo(BigRational.ZERO) == 0) {
                    return BigDecimal.ZERO;
                } else {
                    if (dx.compareTo(BigRational.ZERO) == 1) {
                        //return Math.BigMath.ATAN.invoke(BigMath.DIVIDE.invoke(dx, dy));
                        return BigDecimalMath.atan(dx.divide(dy).toBigDecimal(),
                                new MathContext(dp + 1, rm));
                    } else {
                        return BigDecimalMath.atan(dx.abs().divide(dy).toBigDecimal(),
                                new MathContext(dp + 1, rm));
//                        return BigMath.SUBTRACT.invoke(BigMath.TWO_PI,
//                                BigMath.ATAN.invoke(BigMath.DIVIDE.invoke(
//                                        BigMath.ABS.invoke(dx), dy)));
                        //return (2.0d * Math.PI) - Math.atan(Math.abs(dx) / dy);
                    }
                }
            } else {
                // dy < 0.0d
                if (dx.compareTo(BigRational.ZERO) == 0) {
                    return new Math_BigDecimal().getPi(dp, rm);
                } else {
                    if (dx.compareTo(BigRational.ZERO) == 1) {
                        return new Math_BigDecimal().getPi(dp, rm).subtract(
                                BigDecimalMath.atan(dx.divide(dy.abs()).toBigDecimal(),
                                        new MathContext(dp + 1, rm)));
//                        return BigMath.SUBTRACT.invoke(
//                                BigMath.PI,
//                                BigMath.ATAN.invoke(BigMath.DIVIDE.invoke(dx, BigMath.ABS.invoke(dy))));
                        //return Math.PI - Math.atan(dx / Math.abs(dy));
                    } else {
                        return new Math_BigDecimal().getPi(dp, rm).add(
                                BigDecimalMath.atan(dx.abs().divide(
                                                dy.abs()).toBigDecimal(),
                                        new MathContext(dp + 1, rm)));
//                        return BigMath.ADD.invoke(
//                                BigMath.PI, BigMath.ATAN.invoke(
//                                        BigMath.DIVIDE.invoke(
//                                                BigMath.ABS.invoke(dx),
//                                                BigMath.ABS.invoke(dy))));
                        //return Math.PI + Math.atan(Math.abs(dx) / Math.abs(dy));
                    }
                }
            }
        }
    }

    public BigRational getGradient(V2D_Point p) {
        BigRational dx = x.subtract(p.x);
        BigRational dy = y.subtract(p.y);
        if (dy.compareTo(BigRational.ZERO) == 0) {
            return BigRational.ONE;
        } else {
            if (dx.compareTo(BigRational.ZERO) == 0) {
                return BigRational.ZERO;
            }
            return dx.divide(dy);
        }
    }

    @Override
    public V2D_Envelope getEnvelope() {
        return new V2D_Envelope(x, y);
    }
}

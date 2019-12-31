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
package uk.ac.leeds.ccg.vector.geometry;

import java.io.Serializable;
import java.math.BigDecimal;
import org.ojalgo.function.constant.BigMath;
import org.ojalgo.function.BigFunction;
import uk.ac.leeds.ccg.math.Math_BigDecimal;
import uk.ac.leeds.ccg.vector.core.Vector_Environment;

/**
 * Class for points in 2D.
 */
public class Vector_Point2D extends Vector_AbstractGeometry2D
        implements Comparable, Serializable {

    /**
     * The x coordinate of the Vector_Point2D
     */
    public BigDecimal x;
    /**
     * The y coordinate of the Vector_Point2D
     */
    public BigDecimal y;

    /**
     * Creates a default Vector_Point2D with: x = null; y = null;
     *
     * @param ve
     */
    public Vector_Point2D(Vector_Environment ve) {
        super(ve);
    }

    /**
     * @param p Vector_Point2D
     */
    public Vector_Point2D(Vector_Point2D p) {
        super(p.e);
        x = new BigDecimal(p.x.toString());
        y = new BigDecimal(p.y.toString());
        DecimalPlacePrecision = p.DecimalPlacePrecision;
    }

    /**
     * @param p Vector_Point2D
     * @param dp What {@link #DecimalPlacePrecision} is set to.
     */
    public Vector_Point2D(Vector_Point2D p, int dp) {
        super(p.e);
        x = new BigDecimal(p.x.toString());
        y = new BigDecimal(p.y.toString());
        DecimalPlacePrecision = dp;
        applyDecimalPlacePrecision();
    }

    /**
     * x = new BigDecimal(a_Point2D.x.toString()); y = new
 BigDecimal(a_Point2D.y.toString()); x =
 VectorStaticBigDecimal.getRounded_BigDecimal( x, toRoundToX_BigDecimal);
 y = VectorStaticBigDecimal.getRounded_BigDecimal( y,
 toRoundToY_BigDecimal); setDecimalPlacePrecision( Math.max(
 toRoundToX_BigDecimal.scale(), toRoundToY_BigDecimal.scale()));
     *
     * @param p Vector_Point2D
     * @param toRoundToX_BigDecimal BigDecimal toRoundToX_BigDecimal
     * @param toRoundToY_BigDecimal BigDecimal toRoundToY_BigDecimal
     */
    public Vector_Point2D(
            Vector_Point2D p,
            BigDecimal toRoundToX_BigDecimal,
            BigDecimal toRoundToY_BigDecimal) {
        super(p.e);
        x = new BigDecimal(p.x.toString());
        y = new BigDecimal(p.y.toString());
        x = e.getRounded_BigDecimal(x,
                toRoundToX_BigDecimal);
        y = e.getRounded_BigDecimal(y,
                toRoundToY_BigDecimal);
        DecimalPlacePrecision = Math.max(
                toRoundToX_BigDecimal.scale(),
                toRoundToY_BigDecimal.scale());
    }

    /**
     * this.x = new BigDecimal(x.toString()); this.y = new
 BigDecimal(y.toString());
 setDecimalPlacePrecision(Math.max(x.scale(),y.scale()));
     *
     * @param x BigDecimal
     * @param y BigDecimal
     * @param ve
     */
    public Vector_Point2D(
            Vector_Environment ve,
            BigDecimal x,
            BigDecimal y) {
        super(ve);
        this.x = new BigDecimal(x.toString());
        this.y = new BigDecimal(y.toString());
        DecimalPlacePrecision = Math.max(x.scale(), y.scale());
    }

    /**
     * this.x = new BigDecimal(x.toString()); this.y = new
 BigDecimal(y.toString());
 setDecimalPlacePrecision(DecimalPlacePrecision_Integer);
     *
     * @param x BigDecimal
     * @param y BigDecimal
     * @param decimalPlacePrecision Precision...
     * @param ve
     */
    public Vector_Point2D(
            Vector_Environment ve,
            BigDecimal x,
            BigDecimal y,
            int decimalPlacePrecision) {
        super(ve);
        this.x = new BigDecimal(x.toString());
        this.y = new BigDecimal(y.toString());
        DecimalPlacePrecision = decimalPlacePrecision;
        applyDecimalPlacePrecision();
    }

    /**
     * this.x = VectorStaticBigDecimal.getRounded_BigDecimal( x,
 toRoundToX_BigDecimal); this.y =
 VectorStaticBigDecimal.getRounded_BigDecimal( y, toRoundToY_BigDecimal);
 setDecimalPlacePrecision(toRoundTo_BigDecimal.scale());
     *
     * @param x BigDecimal
     * @param y BigDecimal
     * @param toRoundToX_BigDecimal BigDecimal toRoundToX_BigDecimal
     * @param toRoundToY_BigDecimal BigDecimal
     * @param ve
     */
    public Vector_Point2D(
            Vector_Environment ve,
            BigDecimal x,
            BigDecimal y,
            BigDecimal toRoundToX_BigDecimal,
            BigDecimal toRoundToY_BigDecimal) {
        super(ve);
        this.x = ve.getRounded_BigDecimal(x, toRoundToX_BigDecimal);
        this.y = ve.getRounded_BigDecimal(y, toRoundToY_BigDecimal);
        DecimalPlacePrecision = Math.max(
                toRoundToX_BigDecimal.scale(),
                toRoundToY_BigDecimal.scale());
    }

    /**
     * x = new BigDecimal(x); y = new BigDecimal(y);
 setDecimalPlacePrecision(Math.max(x.scale(),y.scale()));
     *
     * @param ve
     * @param x String
     * @param y String
     */
    public Vector_Point2D(
            Vector_Environment ve,
            String x,
            String y) {
        super(ve);
        this.x = new BigDecimal(x);
        this.y = new BigDecimal(y);
        DecimalPlacePrecision = Math.max(this.x.scale(), this.y.scale());
    }

    /**
     * x = new BigDecimal(x); y = new BigDecimal(y);
 setDecimalPlacePrecision(DecimalPlacePrecision_Integer);
     *
     * @param ve
     * @param x String
     * @param y String
     * @param decimalPlacePrecision Precision
     */
    public Vector_Point2D(
            Vector_Environment ve,
            String x,
            String y,
            int decimalPlacePrecision) {
        super(ve);
        this.x = new BigDecimal(x);
        this.y = new BigDecimal(y);
        DecimalPlacePrecision = decimalPlacePrecision;
        applyDecimalPlacePrecision();
    }

    /**
     * x = new BigDecimal(x); y = new BigDecimal(y);
 setDecimalPlacePrecision(Math.max(x.scale(),y.scale()));
     *
     * @param ve
     * @param x double
     * @param y double
     */
    public Vector_Point2D(
            Vector_Environment ve,
            double x,
            double y) {
        super(ve);
        this.x = new BigDecimal(x);
        this.y = new BigDecimal(y);
        DecimalPlacePrecision = Math.max(this.x.scale(), this.y.scale());
    }

    /**
     * x = new BigDecimal(x); y = new BigDecimal(y);
 setDecimalPlacePrecision(DecimalPlacePrecision_Integer);
     *
     * @param ve
     * @param x double
     * @param y double
     * @param decimalPlacePrecision Precision...
     */
    public Vector_Point2D(
            Vector_Environment ve,
            double x,
            double y,
            int decimalPlacePrecision) {
        super(ve);
        this.x = new BigDecimal(x);
        this.y = new BigDecimal(y);
        DecimalPlacePrecision = decimalPlacePrecision;
        applyDecimalPlacePrecision();
    }

    /**
     * this.x = VectorStaticBigDecimal.getRounded_BigDecimal( new BigDecimal(x),
 toRoundToX_BigDecimal); this.y =
 VectorStaticBigDecimal.getRounded_BigDecimal( new BigDecimal(y),
 toRoundToY_BigDecimal); setDecimalPlacePrecision( Math.max(
 toRoundToX_BigDecimal.scale(), toRoundToY_BigDecimal.scale()));
     *
     * @param ve
     * @param x double
     * @param y double
     * @param toRoundToX_BigDecimal BigDecimal
     * @param toRoundToY_BigDecimal BigDecimal
     */
    public Vector_Point2D(
            Vector_Environment ve,
            double x,
            double y,
            BigDecimal toRoundToX_BigDecimal,
            BigDecimal toRoundToY_BigDecimal) {
        super(ve);
        this.x = ve.getRounded_BigDecimal(
                new BigDecimal(x),
                toRoundToX_BigDecimal);
        this.y = ve.getRounded_BigDecimal(
                new BigDecimal(y),
                toRoundToY_BigDecimal);
        DecimalPlacePrecision = Math.max(
                toRoundToX_BigDecimal.scale(),
                toRoundToY_BigDecimal.scale());
    }

    public void roundTo(BigDecimal toRoundTo_BigDecimal) {
        x = e.getRounded_BigDecimal(x, toRoundTo_BigDecimal);
        y = e.getRounded_BigDecimal(y, toRoundTo_BigDecimal);
        setDecimalPlacePrecision(toRoundTo_BigDecimal.scale());
    }

    @Override
    public String toString() {
        return "Point2D("
                + super.toString()
                + "X(" + x.toString() + "),"
                + "Y(" + y.toString() + "))";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Vector_Point2D) {
            if (hashCode() == ((Vector_Point2D) o).hashCode()) {
                if (compareTo(o) == 0) {
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
     * Default is 1.
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Object o) {
        if (o instanceof Vector_Point2D) {
            Vector_Point2D p = (Vector_Point2D) o;
            int compareTo_y = y.compareTo(p.y);
            if (compareTo_y != 0) {
                return compareTo_y;
            } else {
                return x.compareTo(p.x);
            }
        }
        return 1;
    }

//    public boolean getIntersectsFailFast(
//            LineSegment2D a_LineSegment2D,
//            MathContext a_MathContextForCalculations) {
//        return a_LineSegment2D.getIntersectsFailFast(
//                this,
//                a_MathContextForCalculations);
//    }
    public boolean getIntersects(
            Vector_LineSegment2D a_LineSegment2D,
            int a_DecimalPlacePrecisionForCalculations) {
        return a_LineSegment2D.getIntersects(
                this,
                a_DecimalPlacePrecisionForCalculations);
    }

    /**
     * Precise to a_DecimalPlacePrecision number of decimal places given
     * precision of a_VectorPoint2D and this.
     *
     * @param a_VectorPoint2D Vector_Point2D
     * @param a_DecimalPlacePrecision Precision
     * @return The distance from a_VectorPoint2D to this.
     */
    public BigDecimal getDistance(
            Vector_Point2D a_VectorPoint2D,
            int a_DecimalPlacePrecision) {
        if (this.equals(a_VectorPoint2D)) {
            return BigDecimal.ZERO;
        }
        BigDecimal diffx = this.x.subtract(a_VectorPoint2D.x);
        BigDecimal diffy = this.y.subtract(a_VectorPoint2D.y);
//        return BigFunction.POW.invoke(
//                diffx.multiply(diffx).add(diffy.multiply(diffy)),
//                BigMath.HALF);
        return Math_BigDecimal.sqrt(
                diffx.multiply(diffx).add(diffy.multiply(diffy)),
                a_DecimalPlacePrecision,
                e.bd.getRoundingMode());
    }

    /**
     * Get the angle from {@code p} to the Y-Axis clockwise.
     * @param p A point.
     * @return Angle to the y axis clockwise.
     */
    public double getAngle_double(Vector_Point2D p) {
        double dx = p.x.doubleValue() - x.doubleValue();
        double dy = p.y.doubleValue() - y.doubleValue();
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
     * Get the angle from {@code p} to the Y-Axis clockwise.
     * Uses BigMath and BigFunction default precision
     *
     * @param p A point.
     * @return Angle to the Y-Axis clockwise. Default 0.0d.
     */
    public BigDecimal getAngle_BigDecimal(Vector_Point2D p) {
        BigDecimal dx = p.x.subtract(x);
        BigDecimal dy = p.y.subtract(y);
        if (dy.compareTo(BigDecimal.ZERO) == 0) {
            if (dx.compareTo(BigDecimal.ZERO) == 0) {
                return BigDecimal.ZERO;
            } else {
                if (dx.compareTo(BigDecimal.ZERO) == 1) {
                    return BigMath.HALF_PI;
                } else {
                    return new BigDecimal("3").multiply(BigMath.HALF_PI);
                }
            }
        } else {
            if (dy.compareTo(BigDecimal.ZERO) == 1) {
                if (dx.compareTo(BigDecimal.ZERO) == 0) {
                    return BigDecimal.ZERO;
                } else {
                    if (dx.compareTo(BigDecimal.ZERO) == 1) {
                        return BigFunction.ATAN.invoke(BigFunction.DIVIDE.invoke(dx, dy));
                    } else {
                        return BigFunction.SUBTRACT.invoke(
                                BigMath.TWO_PI,
                                BigFunction.ATAN.invoke(BigFunction.DIVIDE.invoke(BigFunction.ABS.invoke(dx), dy)));
                        //return (2.0d * Math.PI) - Math.atan(Math.abs(dx) / dy);
                    }
                }
            } else {
                // dy < 0.0d
                if (dx.compareTo(BigDecimal.ZERO) == 0) {
                    return BigMath.PI;
                } else {
                    if (dx.compareTo(BigDecimal.ZERO) == 1) {
                        return BigFunction.SUBTRACT.invoke(
                                BigMath.PI,
                                BigFunction.ATAN.invoke(BigFunction.DIVIDE.invoke(dx, BigFunction.ABS.invoke(dy))));
                        //return Math.PI - Math.atan(dx / Math.abs(dy));
                    } else {
                        return BigFunction.ADD.invoke(
                                BigMath.PI,
                                BigFunction.ATAN.invoke(BigFunction.DIVIDE.invoke(BigFunction.ABS.invoke(dx), BigFunction.ABS.invoke(dy))));
                        //return Math.PI + Math.atan(Math.abs(dx) / Math.abs(dy));
                    }
                }
            }
        }
    }

    public BigDecimal getGradient(
            Vector_Point2D p,
            int decimalPlacePrecision) {
        BigDecimal xDiff0 = x.subtract(p.x);
        BigDecimal yDiff0 = y.subtract(p.y);
        if (yDiff0.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ONE;
        } else {
            if (xDiff0.compareTo(BigDecimal.ZERO) == 0) {
                return BigDecimal.ZERO;
            }
            return xDiff0.divide(
                    yDiff0,
                    decimalPlacePrecision,
                    get_RoundingMode());
        }
    }

    @Override
    public Vector_Envelope2D getEnvelope2D() {
        return new Vector_Envelope2D(e, x, y);
    }

    @Override
    public final void applyDecimalPlacePrecision() {
        x = x.setScale(
                getDecimalPlacePrecision(),
                get_RoundingMode());
        y = y.setScale(
                getDecimalPlacePrecision(),
                get_RoundingMode());
    }

    /**
     * double[] result = new double[2]; result[0] = this.x.doubleValue();
 result[1] = this.y.doubleValue(); return result;
     *
     * @return double[}
     */
    public double[] to_doubleArray() {
        double[] result = new double[2];
        result[0] = x.doubleValue();
        result[1] = y.doubleValue();
        return result;
    }
}

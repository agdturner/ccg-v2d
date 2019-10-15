/**
 * Component of a library for handling spatial vector data. Copyright (C) 2009
 * Andy Turner, CCG, University of Leeds, UK.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */
package uk.ac.leeds.ccg.andyt.vector.geometry;

import java.io.Serializable;
import java.math.BigDecimal;
import org.ojalgo.constant.BigMath;
import org.ojalgo.function.implementation.BigFunction;
import uk.ac.leeds.ccg.andyt.math.Math_BigDecimal;
import uk.ac.leeds.ccg.andyt.vector.core.Vector_Environment;

/**
 * Class for points in 2D.
 */
public class Vector_Point2D
        extends Vector_AbstractGeometry2D
        implements Comparable, Serializable {

    /**
     * The x coordinate of the Vector_Point2D
     */
    public BigDecimal X;
    /**
     * The y coordinate of the Vector_Point2D
     */
    public BigDecimal Y;

    /**
     * Creates a default Vector_Point2D with: X = null; Y = null;
     *
     * @param ve
     */
    public Vector_Point2D(Vector_Environment ve) {
        super(ve);
    }

    /**
     * @param p Vector_Point2D
     */
    public Vector_Point2D(
            Vector_Point2D p) {
        super(p.ve);
        X = new BigDecimal(p.X.toString());
        Y = new BigDecimal(p.Y.toString());
        DecimalPlacePrecision = p.DecimalPlacePrecision;
    }

    /**
     * @param p Vector_Point2D
     * @param decimalPlacePrecision Precision
     */
    public Vector_Point2D(
            Vector_Point2D p,
            int decimalPlacePrecision) {
        super(p.ve);
        X = new BigDecimal(p.X.toString());
        Y = new BigDecimal(p.Y.toString());
        DecimalPlacePrecision = decimalPlacePrecision;
        applyDecimalPlacePrecision();
    }

    /**
     * X = new BigDecimal(a_Point2D.X.toString()); Y = new
     * BigDecimal(a_Point2D.Y.toString()); X =
     * VectorStaticBigDecimal.getRounded_BigDecimal( X, toRoundToX_BigDecimal);
     * Y = VectorStaticBigDecimal.getRounded_BigDecimal( Y,
     * toRoundToY_BigDecimal); setDecimalPlacePrecision( Math.max(
     * toRoundToX_BigDecimal.scale(), toRoundToY_BigDecimal.scale()));
     *
     * @param p Vector_Point2D
     * @param toRoundToX_BigDecimal BigDecimal toRoundToX_BigDecimal
     * @param toRoundToY_BigDecimal BigDecimal toRoundToY_BigDecimal
     */
    public Vector_Point2D(
            Vector_Point2D p,
            BigDecimal toRoundToX_BigDecimal,
            BigDecimal toRoundToY_BigDecimal) {
        super(p.ve);
        X = new BigDecimal(p.X.toString());
        Y = new BigDecimal(p.Y.toString());
        X = ve.getRounded_BigDecimal(X,
                toRoundToX_BigDecimal);
        Y = ve.getRounded_BigDecimal(Y,
                toRoundToY_BigDecimal);
        DecimalPlacePrecision = Math.max(
                toRoundToX_BigDecimal.scale(),
                toRoundToY_BigDecimal.scale());
    }

    /**
     * this.X = new BigDecimal(X.toString()); this.Y = new
     * BigDecimal(Y.toString());
     * setDecimalPlacePrecision(Math.max(X.scale(),Y.scale()));
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
        this.X = new BigDecimal(x.toString());
        this.Y = new BigDecimal(y.toString());
        DecimalPlacePrecision = Math.max(x.scale(), y.scale());
    }

    /**
     * this.X = new BigDecimal(X.toString()); this.Y = new
     * BigDecimal(Y.toString());
     * setDecimalPlacePrecision(DecimalPlacePrecision_Integer);
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
        this.X = new BigDecimal(x.toString());
        this.Y = new BigDecimal(y.toString());
        DecimalPlacePrecision = decimalPlacePrecision;
        applyDecimalPlacePrecision();
    }

    /**
     * this.X = VectorStaticBigDecimal.getRounded_BigDecimal( X,
     * toRoundToX_BigDecimal); this.Y =
     * VectorStaticBigDecimal.getRounded_BigDecimal( Y, toRoundToY_BigDecimal);
     * setDecimalPlacePrecision(toRoundTo_BigDecimal.scale());
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
        this.X = ve.getRounded_BigDecimal(x, toRoundToX_BigDecimal);
        this.Y = ve.getRounded_BigDecimal(y, toRoundToY_BigDecimal);
        DecimalPlacePrecision = Math.max(
                toRoundToX_BigDecimal.scale(),
                toRoundToY_BigDecimal.scale());
    }

    /**
     * X = new BigDecimal(x); Y = new BigDecimal(y);
     * setDecimalPlacePrecision(Math.max(X.scale(),Y.scale()));
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
        X = new BigDecimal(x);
        Y = new BigDecimal(y);
        DecimalPlacePrecision = Math.max(X.scale(), Y.scale());
    }

    /**
     * X = new BigDecimal(x); Y = new BigDecimal(y);
     * setDecimalPlacePrecision(DecimalPlacePrecision_Integer);
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
        X = new BigDecimal(x);
        Y = new BigDecimal(y);
        DecimalPlacePrecision = decimalPlacePrecision;
        applyDecimalPlacePrecision();
    }

    /**
     * X = new BigDecimal(x); Y = new BigDecimal(y);
     * setDecimalPlacePrecision(Math.max(X.scale(),Y.scale()));
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
        X = new BigDecimal(x);
        Y = new BigDecimal(y);
        DecimalPlacePrecision = Math.max(X.scale(), Y.scale());
    }

    /**
     * X = new BigDecimal(x); Y = new BigDecimal(y);
     * setDecimalPlacePrecision(DecimalPlacePrecision_Integer);
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
        X = new BigDecimal(x);
        Y = new BigDecimal(y);
        DecimalPlacePrecision = decimalPlacePrecision;
        applyDecimalPlacePrecision();
    }

    /**
     * this.X = VectorStaticBigDecimal.getRounded_BigDecimal( new BigDecimal(x),
     * toRoundToX_BigDecimal); this.Y =
     * VectorStaticBigDecimal.getRounded_BigDecimal( new BigDecimal(y),
     * toRoundToY_BigDecimal); setDecimalPlacePrecision( Math.max(
     * toRoundToX_BigDecimal.scale(), toRoundToY_BigDecimal.scale()));
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
        X = ve.getRounded_BigDecimal(
                new BigDecimal(x),
                toRoundToX_BigDecimal);
        Y = ve.getRounded_BigDecimal(
                new BigDecimal(y),
                toRoundToY_BigDecimal);
        DecimalPlacePrecision = Math.max(
                toRoundToX_BigDecimal.scale(),
                toRoundToY_BigDecimal.scale());
    }

    public void roundTo(BigDecimal toRoundTo_BigDecimal) {
        X = ve.getRounded_BigDecimal(X, toRoundTo_BigDecimal);
        Y = ve.getRounded_BigDecimal(Y, toRoundTo_BigDecimal);
        setDecimalPlacePrecision(toRoundTo_BigDecimal.scale());
    }

    @Override
    public String toString() {
        return "Point2D("
                + super.toString()
                + "X(" + X.toString() + "),"
                + "Y(" + Y.toString() + "))";
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
        hash = 67 * hash + (this.X != null ? this.X.hashCode() : 0);
        hash = 67 * hash + (this.Y != null ? this.Y.hashCode() : 0);
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
            int compareTo_y = Y.compareTo(p.Y);
            if (compareTo_y != 0) {
                return compareTo_y;
            } else {
                return X.compareTo(p.X);
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
        BigDecimal diffx = this.X.subtract(a_VectorPoint2D.X);
        BigDecimal diffy = this.Y.subtract(a_VectorPoint2D.Y);
//        return BigFunction.POW.invoke(
//                diffx.multiply(diffx).add(diffy.multiply(diffy)),
//                BigMath.HALF);
        return Math_BigDecimal.sqrt(
                diffx.multiply(diffx).add(diffy.multiply(diffy)),
                a_DecimalPlacePrecision,
                ve.bd.getRoundingMode());
    }

    /**
     * Imprecise: Uses Java double precision for all calculations
     *
     * @param a_Point2D Vector_Point2D
     * @return Angle to the y axis clockwise. Default 0.0d.
     */
    public double getAngle_double(Vector_Point2D a_Point2D) {
        double dx = a_Point2D.X.doubleValue() - X.doubleValue();
        double dy = a_Point2D.Y.doubleValue() - Y.doubleValue();
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
     * Imprecise, but uses BigMath and BigFunction default precision
     *
     * @param a_Point2D Vector_Point2D
     * @return Angle to the y axis clockwise. Default 0.0d.
     */
    public BigDecimal getAngle_BigDecimal(Vector_Point2D a_Point2D) {
        BigDecimal dx = a_Point2D.X.subtract(X);
        BigDecimal dy = a_Point2D.Y.subtract(Y);
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
        BigDecimal xDiff0 = X.subtract(p.X);
        BigDecimal yDiff0 = Y.subtract(p.Y);
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
        return new Vector_Envelope2D(ve, X, Y);
    }

    @Override
    public final void applyDecimalPlacePrecision() {
        X = X.setScale(
                getDecimalPlacePrecision(),
                get_RoundingMode());
        Y = Y.setScale(
                getDecimalPlacePrecision(),
                get_RoundingMode());
    }

    /**
     * double[] result = new double[2]; result[0] = this.X.doubleValue();
     * result[1] = this.Y.doubleValue(); return result;
     *
     * @return double[}
     */
    public double[] to_doubleArray() {
        double[] result = new double[2];
        result[0] = X.doubleValue();
        result[1] = Y.doubleValue();
        return result;
    }
}

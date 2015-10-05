/**
 * Component of a library for handling spatial vector data.
 * Copyright (C) 2009 Andy Turner, CCG, University of Leeds, UK.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA.
 */
package uk.ac.leeds.ccg.andyt.vector.geometry;

import java.io.Serializable;
import java.math.BigDecimal;
//import java.math.MathContext;
//import org.ojalgo.function.implementation.BigFunction;
//import org.ojalgo.constant.BigMath;
import org.ojalgo.constant.BigMath;
import org.ojalgo.function.implementation.BigFunction;
import uk.ac.leeds.ccg.andyt.generic.math.Generic_BigDecimal;
//import uk.ac.leeds.ccg.andyt.vector.core.Vector_Environment;
import uk.ac.leeds.ccg.andyt.vector.misc.VectorStaticBigDecimal;

/**
 * Class for points in 2D.
 */
public class Vector_Point2D
        extends Vector_AbstractGeometry2D
        implements Comparable, Serializable {

    /**
     * The x coordinate of the Vector_Point2D
     */
    public BigDecimal _x;
    /**
     * The y coordinate of the Vector_Point2D
     */
    public BigDecimal _y;

    /**
     * Creates a default Vector_Point2D with:
     * _x = null;
     * _y = null;
     */
    public Vector_Point2D() {
    }

    /**
     * _x = new BigDecimal(aPoint2D._x.toString());
     * _y = new BigDecimal(aPoint2D._y.toString());
     *  set_DecimalPlacePrecision(Math.max(_x.scale(),_y.scale()));
     * @param a_Point2D Vector_Point2D
     */
    public Vector_Point2D(
            Vector_Point2D a_Point2D) {
        _x = new BigDecimal(a_Point2D._x.toString());
        _y = new BigDecimal(a_Point2D._y.toString());
        this._DecimalPlacePrecision_Integer = a_Point2D._DecimalPlacePrecision_Integer;
        //set_DecimalPlacePrecision(Math.max(_x.scale(), _y.scale()));
    }

    /**
     * _x = new BigDecimal(aPoint2D._x.toString());
     * _y = new BigDecimal(aPoint2D._y.toString());
     * set_DecimalPlacePrecision(_DecimalPlacePrecision_Integer);
     * @param a_Point2D Vector_Point2D
     * @param _DecimalPlacePrecision Precision
     */
    public Vector_Point2D(
            Vector_Point2D a_Point2D,
            int _DecimalPlacePrecision) {
        _x = new BigDecimal(a_Point2D._x.toString());
        _y = new BigDecimal(a_Point2D._y.toString());
        set_DecimalPlacePrecision(_DecimalPlacePrecision);
    }

    /**
     * _x = new BigDecimal(a_Point2D._x.toString());
     * _y = new BigDecimal(a_Point2D._y.toString());
     * _x = VectorStaticBigDecimal.getRounded_BigDecimal(
     *         _x,
     *         toRoundToX_BigDecimal);
     * _y = VectorStaticBigDecimal.getRounded_BigDecimal(
     *         _y,
     *         toRoundToY_BigDecimal);
     * set_DecimalPlacePrecision(
     *         Math.max(
     *         toRoundToX_BigDecimal.scale(),
     *         toRoundToY_BigDecimal.scale()));
     * @param a_Point2D Vector_Point2D
     * @param toRoundToX_BigDecimal BigDecimal toRoundToX_BigDecimal
     * @param toRoundToY_BigDecimal BigDecimal toRoundToY_BigDecimal
     */
    public Vector_Point2D(
            Vector_Point2D a_Point2D,
            BigDecimal toRoundToX_BigDecimal,
            BigDecimal toRoundToY_BigDecimal) {
        _x = new BigDecimal(a_Point2D._x.toString());
        _y = new BigDecimal(a_Point2D._y.toString());
        _x = VectorStaticBigDecimal.getRounded_BigDecimal(
                _x,
                toRoundToX_BigDecimal);
        _y = VectorStaticBigDecimal.getRounded_BigDecimal(
                _y,
                toRoundToY_BigDecimal);
        set_DecimalPlacePrecision(
                Math.max(
                toRoundToX_BigDecimal.scale(),
                toRoundToY_BigDecimal.scale()));
    }

    /**
     * this._x = new BigDecimal(_x.toString());
     * this._y = new BigDecimal(_y.toString());
     * set_DecimalPlacePrecision(Math.max(_x.scale(),_y.scale()));
     * @param _x BigDecimal
     * @param _y BigDecimal
     */
    public Vector_Point2D(
            BigDecimal _x,
            BigDecimal _y) {
        this._x = new BigDecimal(_x.toString());
        this._y = new BigDecimal(_y.toString());
        set_DecimalPlacePrecision(Math.max(_x.scale(), _y.scale()));
    }

    /**
     * this._x = new BigDecimal(_x.toString());
     * this._y = new BigDecimal(_y.toString());
     * set_DecimalPlacePrecision(_DecimalPlacePrecision_Integer);
     * @param _x BigDecimal
     * @param _y BigDecimal
     * @param _DecimalPlacePrecision Precision...
     */
    public Vector_Point2D(
            BigDecimal _x,
            BigDecimal _y,
            int _DecimalPlacePrecision) {
        this._x = new BigDecimal(_x.toString());
        this._y = new BigDecimal(_y.toString());
        set_DecimalPlacePrecision(_DecimalPlacePrecision);
    }

    /**
     * this._x = VectorStaticBigDecimal.getRounded_BigDecimal(
     *         _x,
     *         toRoundToX_BigDecimal);
     * this._y = VectorStaticBigDecimal.getRounded_BigDecimal(
     *         _y,
     *         toRoundToY_BigDecimal);
     * set_DecimalPlacePrecision(toRoundTo_BigDecimal.scale());
     * @param _x BigDecimal
     * @param _y BigDecimal
     * @param toRoundToX_BigDecimal BigDecimal toRoundToX_BigDecimal
     * @param toRoundToY_BigDecimal BigDecimal
     */
    public Vector_Point2D(
            BigDecimal _x,
            BigDecimal _y,
            BigDecimal toRoundToX_BigDecimal,
            BigDecimal toRoundToY_BigDecimal) {
        this._x = VectorStaticBigDecimal.getRounded_BigDecimal(
                _x,
                toRoundToX_BigDecimal);
        this._y = VectorStaticBigDecimal.getRounded_BigDecimal(
                _y,
                toRoundToY_BigDecimal);
        set_DecimalPlacePrecision(
                Math.max(
                toRoundToX_BigDecimal.scale(),
                toRoundToY_BigDecimal.scale()));
    }

    /**
     * _x = new BigDecimal(x);
     * _y = new BigDecimal(y);
     * set_DecimalPlacePrecision(Math.max(_x.scale(),_y.scale()));
     * @param x String
     * @param y String
     */
    public Vector_Point2D(
            String x,
            String y) {
        _x = new BigDecimal(x);
        _y = new BigDecimal(y);
        set_DecimalPlacePrecision(Math.max(_x.scale(), _y.scale()));
    }

    /**
     * _x = new BigDecimal(x);
     * _y = new BigDecimal(y);
     * set_DecimalPlacePrecision(_DecimalPlacePrecision_Integer);
     * @param x String
     * @param y String
     * @param _DecimalPlacePrecision Precision
     */
    public Vector_Point2D(
            String x,
            String y,
            int _DecimalPlacePrecision) {
        _x = new BigDecimal(x);
        _y = new BigDecimal(y);
        set_DecimalPlacePrecision(_DecimalPlacePrecision);
    }

    /**
     * _x = new BigDecimal(x);
     * _y = new BigDecimal(y);
     * set_DecimalPlacePrecision(Math.max(_x.scale(),_y.scale()));
     * @param x double
     * @param y double
     * @deprecated
     */
    public Vector_Point2D(
            double x,
            double y) {
        _x = new BigDecimal(x);
        _y = new BigDecimal(y);
        set_DecimalPlacePrecision(Math.max(_x.scale(), _y.scale()));
    }

    /**
     * _x = new BigDecimal(x);
     * _y = new BigDecimal(y);
     * set_DecimalPlacePrecision(_DecimalPlacePrecision_Integer);
     * @param x double
     * @param y double
     * @param _DecimalPlacePrecision Precision...
     */
    public Vector_Point2D(
            double x,
            double y,
            int _DecimalPlacePrecision) {
        _x = new BigDecimal(x);
        _y = new BigDecimal(y);
        set_DecimalPlacePrecision(_DecimalPlacePrecision);
    }

    /**
     * this._x = VectorStaticBigDecimal.getRounded_BigDecimal(
     *         new BigDecimal(x),
     *         toRoundToX_BigDecimal);
     * this._y = VectorStaticBigDecimal.getRounded_BigDecimal(
     *         new BigDecimal(y),
     *         toRoundToY_BigDecimal);
     * set_DecimalPlacePrecision(
     *         Math.max(
     *         toRoundToX_BigDecimal.scale(),
     *         toRoundToY_BigDecimal.scale()));
     * @param x double
     * @param y double
     * @param toRoundToX_BigDecimal BigDecimal
     * @param toRoundToY_BigDecimal BigDecimal
     */
    public Vector_Point2D(
            double x,
            double y,
            BigDecimal toRoundToX_BigDecimal,
            BigDecimal toRoundToY_BigDecimal) {
        this._x = VectorStaticBigDecimal.getRounded_BigDecimal(
                new BigDecimal(x),
                toRoundToX_BigDecimal);
        this._y = VectorStaticBigDecimal.getRounded_BigDecimal(
                new BigDecimal(y),
                toRoundToY_BigDecimal);
        set_DecimalPlacePrecision(
                Math.max(
                toRoundToX_BigDecimal.scale(),
                toRoundToY_BigDecimal.scale()));
    }

    @Override
    public final int set_DecimalPlacePrecision(int _DecimalPlacePrecision) {
        int result = super.set_DecimalPlacePrecision(_DecimalPlacePrecision);
        applyDecimalPlacePrecision();
        return result;
    }

    public void roundTo(BigDecimal toRoundTo_BigDecimal) {
        _x = VectorStaticBigDecimal.getRounded_BigDecimal(
                _x,
                toRoundTo_BigDecimal);
        _y = VectorStaticBigDecimal.getRounded_BigDecimal(
                _y,
                toRoundTo_BigDecimal);
        set_DecimalPlacePrecision(toRoundTo_BigDecimal.scale());
    }

    @Override
    public String toString() {
        return "Point2D("
                + super.toString()
                + "x(" + _x.toString() + "),"
                + "y(" + _y.toString() + "))";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Vector_Point2D) {
            if (compareTo(o) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this._x != null ? this._x.hashCode() : 0);
        hash = 67 * hash + (this._y != null ? this._y.hashCode() : 0);
        return hash;
    }

    /**
     * Default is 1.
     */
    @Override
    public int compareTo(Object o) {
        if (o instanceof Vector_Point2D) {
            Vector_Point2D aPoint2D = (Vector_Point2D) o;
            int compareTo_y = _y.compareTo(aPoint2D._y);
            if (compareTo_y != 0) {
                return compareTo_y;
            } else {
                return _x.compareTo(aPoint2D._x);
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
        BigDecimal diffx = this._x.subtract(a_VectorPoint2D._x);
        BigDecimal diffy = this._y.subtract(a_VectorPoint2D._y);
//        return BigFunction.POW.invoke(
//                diffx.multiply(diffx).add(diffy.multiply(diffy)),
//                BigMath.HALF);
        Generic_BigDecimal t_Generic_BigDecimal =
                _Vector_Environment.get_Generic_BigDecimal();
        return Generic_BigDecimal.sqrt(
                diffx.multiply(diffx).add(diffy.multiply(diffy)),
                a_DecimalPlacePrecision,
                t_Generic_BigDecimal.get_RoundingMode());
    }

    /**
     * Imprecise: Uses Java double precision for all calculations
     * @param a_Point2D Vector_Point2D
     * @return Angle to the y axis clockwise. Default 0.0d.
     */
    public double getAngle_double(Vector_Point2D a_Point2D) {
        double dx = a_Point2D._x.doubleValue() - _x.doubleValue();
        double dy = a_Point2D._y.doubleValue() - _y.doubleValue();
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
     * @param a_Point2D Vector_Point2D
     * @return Angle to the y axis clockwise. Default 0.0d.
     */
    public BigDecimal getAngle_BigDecimal(Vector_Point2D a_Point2D) {
        BigDecimal dx = a_Point2D._x.subtract(_x);
        BigDecimal dy = a_Point2D._y.subtract(_y);
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
                        return BigFunction.ATAN.invoke(BigFunction.DIVIDE.invoke(dx,dy));
                    } else {
                        return BigFunction.SUBTRACT.invoke(
                                BigMath.TWO_PI,
                                BigFunction.ATAN.invoke(BigFunction.DIVIDE.invoke(BigFunction.ABS.invoke(dx),dy)));
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
                                BigFunction.ATAN.invoke(BigFunction.DIVIDE.invoke(dx,BigFunction.ABS.invoke(dy))));
                        //return Math.PI - Math.atan(dx / Math.abs(dy));
                    } else {
                        return BigFunction.ADD.invoke(
                                BigMath.PI,
                                BigFunction.ATAN.invoke(BigFunction.DIVIDE.invoke(BigFunction.ABS.invoke(dx),BigFunction.ABS.invoke(dy))));
                        //return Math.PI + Math.atan(Math.abs(dx) / Math.abs(dy));
                    }
                }
            }
        }
    }

    public BigDecimal getGradient(
            Vector_Point2D a_Point2D,
            int a_DecimalPlacePrecision) {
        BigDecimal xDiff0 = _x.subtract(
                a_Point2D._x);
        BigDecimal yDiff0 = _y.subtract(
                a_Point2D._y);
        if (yDiff0.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ONE;
        } else {
            if (xDiff0.compareTo(BigDecimal.ZERO) == 0) {
                return BigDecimal.ZERO;
            }
            return xDiff0.divide(
                    yDiff0,
                    a_DecimalPlacePrecision,
                    get_RoundingMode());
        }
    }

    @Override
    public Vector_Envelope2D getEnvelope2D() {
        return new Vector_Envelope2D(_x, _y);
    }

    @Override
    public void applyDecimalPlacePrecision() {
        _x = _x.setScale(
                get_DecimalPlacePrecision(),
                get_RoundingMode());
        _y = _y.setScale(
                get_DecimalPlacePrecision(),
                get_RoundingMode());
    }

    /**
     * double[] result = new double[2];
     * result[0] = this._x.doubleValue();
     * result[1] = this._y.doubleValue();
     * return result;
     * @return double[}
     */
    public double[] to_doubleArray() {
        double[] result = new double[2];
        result[0] = this._x.doubleValue();
        result[1] = this._y.doubleValue();
        return result;
    }
}

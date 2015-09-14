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
import java.math.RoundingMode;
//import org.ojalgo.function.implementation.BigFunction;
//import org.ojalgo.constant.BigMath;

/**
 * Class for a line segment in 2D represented by two Point2D instances, one is
 * designated a start point and the other an end point. In this way a line
 * segment explicitly has a direction. Two Vector_LineSegment2D instances are
 * regarded as equal iff their start and end points are the same.
 */
public class Vector_LineSegment2D
        extends Vector_AbstractGeometry2D
        implements Comparable, Serializable {

    public Vector_Point2D _Start_Point2D;
    public Vector_Point2D _End_Point2D;

    /**
     * Creates a default Vector_LineSegment2D with: _Start_Point2D = null;
     * _End_Point2D = null;
     */
    public Vector_LineSegment2D() {
    }

    /**
     * Creates a Vector_LineSegment2D with: this._Start_Point2D = new
     * Point2D(_StartPoint); this._End_Point2D = new Point2D(_EndPoint);
     * set_DecimalPlacePrecision(Math.max(
     * _Start_Point2D.get_DecimalPlacePrecision(),
     * _End_Point2D.get_DecimalPlacePrecision()));
     *
     * @param _StartPoint Vector_Point2D
     * @param _EndPoint Vector_Point2D
     */
    public Vector_LineSegment2D(
            Vector_Point2D _StartPoint,
            Vector_Point2D _EndPoint) {
        this._Start_Point2D = new Vector_Point2D(_StartPoint);
        this._End_Point2D = new Vector_Point2D(_EndPoint);
        set_DecimalPlacePrecision(Math.max(
                _Start_Point2D.get_DecimalPlacePrecision(),
                _End_Point2D.get_DecimalPlacePrecision()));
    }

    /**
     * Creates a Vector_LineSegment2D with: _StartPoint = _StartPoint; _EndPoint
     * = _EndPoint; _DecimalPlacePrecision_Integer =
     * _DecimalPlacePrecision_Integer; _RoundingMode = _RoundingMode.
     *
     * @param _StartPoint Vector_Point2D
     * @param _EndPoint Vector_Point2D
     * @param _DecimalPlacePrecision int
     */
    public Vector_LineSegment2D(
            Vector_Point2D _StartPoint,
            Vector_Point2D _EndPoint,
            int _DecimalPlacePrecision) {
        this._Start_Point2D = new Vector_Point2D(_StartPoint, _DecimalPlacePrecision);
        this._End_Point2D = new Vector_Point2D(_EndPoint, _DecimalPlacePrecision);
        set_DecimalPlacePrecision(_DecimalPlacePrecision);
    }

    /**
     * Creates a Vector_LineSegment2D with: this._Start_Point2D = new
     * Point2D(a_LineSegment2D._Start_Point2D); this._End_Point2D = new
     * Point2D(a_LineSegment2D._End_Point2D);
     * this._DecimalPlacePrecision_Integer =
     * a_LineSegment2D._DecimalPlacePrecision_Integer;
     *
     * @param a_LineSegment2D Vector_LineSegment2D
     */
    public Vector_LineSegment2D(
            Vector_LineSegment2D a_LineSegment2D) {
        this._Start_Point2D = new Vector_Point2D(a_LineSegment2D._Start_Point2D);
        this._End_Point2D = new Vector_Point2D(a_LineSegment2D._End_Point2D);
        this._DecimalPlacePrecision_Integer = a_LineSegment2D._DecimalPlacePrecision_Integer;
//        set_DecimalPlacePrecision(Math.max(
//                this._Start_Point2D.get_DecimalPlacePrecision(),
//                this._End_Point2D.get_DecimalPlacePrecision()));
    }

    @Override
    protected final int set_DecimalPlacePrecision(int _DecimalPlacePrecision) {
        int result = super.set_DecimalPlacePrecision(_DecimalPlacePrecision);
        applyDecimalPlacePrecision();
        return result;
    }

    @Override
    public String toString() {
        return "LineSegment2D(" + super.toString()
                + "_Start_Point2D(" + _Start_Point2D.toString() + ")"
                + "_End_Point2D(" + _End_Point2D.toString() + "))";
    }

    /**
     * Default is 1. Calls compareTo on the _StartPoint.
     */
    @Override
    public int compareTo(Object o) {
        if (o instanceof Vector_LineSegment2D) {
            Vector_LineSegment2D a_LineSegment2D = (Vector_LineSegment2D) o;
            int compareStart = _Start_Point2D.compareTo(a_LineSegment2D._Start_Point2D);
            if (compareStart == 0) {
                return _Start_Point2D.compareTo(a_LineSegment2D._Start_Point2D);
            } else {
                return compareStart;
            }
        }
        return 1;
    }

    /**
     * @param a_DecimalPlacePrecision The decimal place precision to be used for
     * this.
     * @return The length of this as a BigDecimal
     */
    public BigDecimal getLength(int a_DecimalPlacePrecision) {
        BigDecimal length;
        length = _Start_Point2D.getDistance(
                _End_Point2D,
                a_DecimalPlacePrecision);
        return length;
    }

    /**
     * TODO: Correct with regard _DecimalPlacePrecision_Integer!?
     *
     * @return Vector_Envelope2D 
     */
    @Override
    public Vector_Envelope2D getEnvelope2D() {
        return new Vector_Envelope2D(
                _Start_Point2D,
                _End_Point2D);
    }

    /**
     * Intersection done by first seeing if Envelope intersects....
     *
     * @param a_LineSegment2D A Vector_LineSegment2D to test.
     * @param a_DecimalPlacePrecision The decimal place precision to be used for
     * this.
     *
     * @return True iff a_LineSegment2D getIntersects this.
     */
    public boolean getIntersects(
            Vector_LineSegment2D a_LineSegment2D,
            int a_DecimalPlacePrecision) {
        int a_Envelope2DIntersect = getEnvelope2D().getIntersects(a_LineSegment2D);
        if (a_Envelope2DIntersect == 0) {
            return false;
        } else {
            if (a_Envelope2DIntersect == 1) {
                return true;
            } else {
                int b_Envelope2DIntersect = a_LineSegment2D.getEnvelope2D().getIntersects(
                        this);
                if (b_Envelope2DIntersect == 0) {
                    return false;
                } else {
                    if (b_Envelope2DIntersect == 1) {
                        return true;
                    } else {
                        Vector_AbstractGeometry2D intersection;
                        intersection = getIntersection(
                                a_LineSegment2D,
                                a_DecimalPlacePrecision);
                        if (intersection != null) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
    }

    /**
     * For optimisation reasons, intersection done by first seeing if there is
     * Envelope intersection...
     *
     * @param a_LineSegment2D Vector_LineSegment2D to test for intersection.
     * @param ignore_this_Start_Point2D if true then if
     * this._Start_Point2D.getIntersects(a_LineSegment2D) return false
     * @param a_DecimalPlacePrecision The decimal place precision to be used for
     * this.
     * @return true iff a_LineSegment2D getIntersects this.
     */
    public boolean getIntersects(
            Vector_LineSegment2D a_LineSegment2D,
            boolean ignore_this_Start_Point2D,
            int a_DecimalPlacePrecision) {
        if (ignore_this_Start_Point2D) {
            if (this._Start_Point2D.getIntersects(
                    a_LineSegment2D,
                    a_DecimalPlacePrecision)) {
                return false;
            } else {
                return getIntersects(
                        a_LineSegment2D,
                        a_DecimalPlacePrecision);
            }
        } else {
            return getIntersects(
                    a_LineSegment2D,
                    a_DecimalPlacePrecision);
        }
    }

    /**
     * Intersection done by calculating angle or gradient of the line and
     * comparing this with that of a_Point.
     *
     * @param a_Point2D A Vector_Point2D to test for intersection.
     * @param a_DecimalPlacePrecision The decimal place precision to be used for
     * this.
     * @return true iff a_Point2D getIntersects this.
     */
    public boolean getIntersects(
            Vector_Point2D a_Point2D,
            int a_DecimalPlacePrecision) {
        if (getEnvelope2D().getIntersects(a_Point2D) == false) {
            return false;
        }
        if (_Start_Point2D._y.compareTo(_End_Point2D._y) == -1) {
            // StartPoint is Below EndPoint
            if (_Start_Point2D._x.compareTo(_End_Point2D._x) == -1) {
                // StartPoint is Left of EndPoint
                return isOnGradient(
                        a_Point2D,
                        a_DecimalPlacePrecision);
            } else {
                if (_Start_Point2D._x.compareTo(_End_Point2D._x) == 0) {
                    // StartPoint has same _x as EndPoint
                    return true;
                } else {
                    // StartPoint is Right of EndPoint
                    return isOnGradient(
                            a_Point2D,
                            a_DecimalPlacePrecision);
                }
            }
        } else {
            if (_Start_Point2D._y.compareTo(_End_Point2D._y) == 0) {
                // StartPoint has same _y as EndPoint
                return true;
            } else {
                // StartPoint is Above EndPoint
                if (_Start_Point2D._x.compareTo(_End_Point2D._x) == -1) {
                    // StartPoint is Left of EndPoint
                    return isOnGradient(
                            a_Point2D,
                            a_DecimalPlacePrecision);
                } else {
                    if (_Start_Point2D._x.compareTo(_End_Point2D._x) == 0) {
                        // StartPoint has same _x as EndPoint
                        return true;
                    } else {
                        // StartPoint is Right of EndPoint
                        return isOnGradient(
                                a_Point2D,
                                a_DecimalPlacePrecision);
                    }
                }
            }
        }
    }

    protected BigDecimal getGradient(
            int a_DecimalPlacePrecision) {
        return _Start_Point2D.getGradient(
                _End_Point2D,
                a_DecimalPlacePrecision);
    }

    protected boolean isOnGradient(
            Vector_Point2D a_Point2D,
            int a_DecimalPlacePrecision) {
        //Point2D b_Point2D = a_Point2D.
        //MathContext a_MathContext = new MathContext(_DecimalPlacePrecision_Integer + 5);
        BigDecimal xDiff0 = _End_Point2D._x.subtract(
                _Start_Point2D._x);
        BigDecimal yDiff0 = _End_Point2D._y.subtract(
                _Start_Point2D._y);
        BigDecimal xDiff1 = a_Point2D._x.subtract(
                _Start_Point2D._x);
        BigDecimal yDiff1 = a_Point2D._y.subtract(
                _Start_Point2D._y);
        BigDecimal gradient0;
        if (yDiff0.compareTo(BigDecimal.ZERO) == 0) {
            if (yDiff1.compareTo(BigDecimal.ZERO) == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            gradient0 = xDiff0.divide(
                    yDiff0,
                    a_DecimalPlacePrecision + 2, // + 2 sufficient?
                    RoundingMode.CEILING);
        }
        BigDecimal gradient1;
        if (yDiff1.compareTo(BigDecimal.ZERO) == 0) {
            return false;
        } else {
            gradient1 = xDiff1.divide(
                    yDiff1,
                    a_DecimalPlacePrecision + 2, // + 2 sufficient?
                    RoundingMode.CEILING);
        }
        if (gradient0.compareTo(gradient1) == 0) {
            return true;
        }
        return false;
    }

    public Vector_LineSegment2D getOrderedLineSegment2D() {
        if (_Start_Point2D._y.compareTo(_End_Point2D._y) == -1) {
            return new Vector_LineSegment2D(this);
        } else {
            if (_Start_Point2D._y.compareTo(_End_Point2D._y) == 0) {
                if (_Start_Point2D._x.compareTo(_End_Point2D._x) != 1) {
                    return new Vector_LineSegment2D(this);
                }
            }
        }
        return new Vector_LineSegment2D(
                this._End_Point2D,
                this._Start_Point2D,
                this.get_DecimalPlacePrecision());
    }

    /**
     * Intersection method adapted from
     * <a href="http://local.wasp.uwa.edu.au/~pbourke/geometry/lineline2d/">http://local.wasp.uwa.edu.au/~pbourke/geometry/lineline2d/</a>
     *
     * @param a_LineSegment2D Vector_LineSegment2D
     * @param a_DecimalPlacePrecision Precision...
     * @return null if this does not intersect a_LineSegment2D; a Point2D if
     * this getIntersects a_LineSegment2D at a point; and, a
     * Vector_LineSegment2D if this getIntersects a_LineSegment2D in a line.
     * TODO: Not sure about using Rounding Modes here, it might be best to
     * handle arithmetic exceptions and provide another method into which a
     * MathContext can be passed...
     */
    public Vector_AbstractGeometry2D getIntersection(
            Vector_LineSegment2D a_LineSegment2D,
            int a_DecimalPlacePrecision) {
        //MathContext a_MathContext = new MathContext(this._DecimalPlacePrecision_Integer + 100);
        BigDecimal x2minusx1 = _End_Point2D._x.subtract(_Start_Point2D._x);
        BigDecimal y2minusy1 = _End_Point2D._y.subtract(_Start_Point2D._y);
        BigDecimal x4minusx3 = a_LineSegment2D._End_Point2D._x.subtract(a_LineSegment2D._Start_Point2D._x);
        BigDecimal y4minusy3 = a_LineSegment2D._End_Point2D._y.subtract(a_LineSegment2D._Start_Point2D._y);
        BigDecimal denominator = (y4minusy3.multiply(x2minusx1)).subtract(x4minusx3.multiply(y2minusy1));
        boolean parallel = false;
        // Not sure about use of _RoundingMode here!!
        if (denominator.setScale(a_DecimalPlacePrecision, RoundingMode.FLOOR).compareTo(BigDecimal.ZERO) == 0) {
            //System.out.println("parallel lines");
            parallel = true;
        }
        BigDecimal y1minusy3 = _Start_Point2D._y.subtract(a_LineSegment2D._Start_Point2D._y);
        BigDecimal x1minusx3 = _Start_Point2D._x.subtract(a_LineSegment2D._Start_Point2D._x);
        BigDecimal uamultiplicand = (x4minusx3.multiply(y1minusy3)).subtract(y4minusy3.multiply(x1minusx3));
        BigDecimal ubmultiplicand = (x2minusx1.multiply(y1minusy3)).subtract(y2minusy1.multiply(x1minusx3));
        if (uamultiplicand.setScale(a_DecimalPlacePrecision, RoundingMode.FLOOR).compareTo(BigDecimal.ZERO) == 0
                && ubmultiplicand.setScale(a_DecimalPlacePrecision, RoundingMode.FLOOR).compareTo(BigDecimal.ZERO) == 0
                && parallel) {
            //System.out.println("lines coincident");
            Vector_LineSegment2D ot = getOrderedLineSegment2D();
            Vector_LineSegment2D oa = a_LineSegment2D.getOrderedLineSegment2D();
            boolean ts = ot._Start_Point2D.getIntersects(
                    oa,
                    a_DecimalPlacePrecision);
            boolean te = ot._End_Point2D.getIntersects(
                    oa,
                    a_DecimalPlacePrecision);
            boolean as = oa._Start_Point2D.getIntersects(
                    ot,
                    a_DecimalPlacePrecision);
            boolean ae = oa._End_Point2D.getIntersects(
                    ot,
                    a_DecimalPlacePrecision);
            if (ts) {
                if (te) {
                    if (as) {
                        if (ae) {
                            return new Vector_LineSegment2D(a_LineSegment2D);
                        } else {
                            return new Vector_LineSegment2D(
                                    ot._Start_Point2D,
                                    ot._End_Point2D);
                        }
                    } else {
                        if (ae) {
                            return new Vector_LineSegment2D(
                                    ot._Start_Point2D,
                                    oa._End_Point2D);
                        } else {
                            return new Vector_LineSegment2D(
                                    this);
                        }
                    }
                } else {
                    if (as) {
                        if (ae) {
                            return new Vector_LineSegment2D(a_LineSegment2D);
                        } else {
                            return new Vector_Point2D(ot._Start_Point2D);
                        }
                    } else {
                        System.out.println("Wierd...");
                        return null;
                    }
                }
            } else {
                if (te) {
                    if (as) {
                        if (ae) {
                            return new Vector_LineSegment2D(
                                    oa._Start_Point2D,
                                    oa._End_Point2D);
                        } else {
                            return new Vector_LineSegment2D(
                                    oa._Start_Point2D,
                                    ot._End_Point2D);
                        }
                    } else {
                        System.out.println("Wierd...");
                        return null;
                    }
                } else {
                    return new Vector_LineSegment2D(a_LineSegment2D);
                }
            }
        }
        if (parallel) {
            return null;
        }
        BigDecimal ua = uamultiplicand.divide(
                denominator,
                a_DecimalPlacePrecision,
                RoundingMode.CEILING);
        if (ua.compareTo(BigDecimal.ONE) != 1
                && ua.compareTo(BigDecimal.ZERO) != -1) {
            BigDecimal intersectx1 = _Start_Point2D._x.add(
                    ua.multiply(x2minusx1));
            BigDecimal intersecty1 = _Start_Point2D._y.add(
                    ua.multiply(y2minusy1));
//            BigDecimal ub = ubmultiplicand.divide(
//                    denominator,
//                    a_DecimalPlacePrecision,
//                RoundingMode.HALF_UP);
            return new Vector_Point2D(
                    intersectx1,
                    intersecty1,
                    a_DecimalPlacePrecision);
        } else {
            BigDecimal ub = ubmultiplicand.divide(
                    denominator,
                    a_DecimalPlacePrecision,
                    RoundingMode.CEILING);
            if (ub.compareTo(BigDecimal.ONE) != 1
                    && ub.compareTo(BigDecimal.ZERO) != -1) {
                BigDecimal intersectx2 = a_LineSegment2D._Start_Point2D._x.add(
                        ub.multiply(x4minusx3));
                BigDecimal intersecty2 = a_LineSegment2D._Start_Point2D._y.add(
                        ub.multiply(y4minusy3));
                return new Vector_Point2D(
                        intersectx2,
                        intersecty2,
                        a_DecimalPlacePrecision);
            }
        }
        return null;
    }

    /**
     * TODO: Control precision! The angle returned is the smallest angle between
     * this and the X axis and so is between Math.PI and -Math.PI.
     * @return The angle in radians to the X axis
     */
    public double getAngleToX_double() {
        BigDecimal dx = this._End_Point2D._x.subtract(this._Start_Point2D._x);
        BigDecimal dy = this._End_Point2D._y.subtract(this._Start_Point2D._y);
        return Math.atan2(dy.doubleValue(), dx.doubleValue());
    }

//    /**
//     * The angle returned is the smallest angle between this and the X axis and
//     * so is between Math.PI and -Math.PI.
//     * @return The angle in radians to the X axis
//     */
//    public double getAngleToX_double(){
//        double result;
//        BigDecimal dx = this._End_Point2D._x.subtract(this._Start_Point2D._x);
//        BigDecimal dy = this._End_Point2D._y.subtract(this._Start_Point2D._y);
//        double angleToX = Math.atan2(dy.doubleValue(),dx.doubleValue());
//        if (this._Start_Point2D._x.compareTo(this._End_Point2D._x) == -1 ){
//            result = Math.PI - angleToX;
//        } else {
//            result = angleToX;
//        }
//        return result;
//    }
    /**
     * TODO: Control precision! The angle returned is the smallest angle between
     * this and the Y axis and so is between Math.PI and -Math.PI.
     *
     * @return The angle in radians to the Y axis
     */
    public double getAngleToY_double() {
        double result;
        BigDecimal dx = this._End_Point2D._x.subtract(this._Start_Point2D._x);
        BigDecimal dy = this._End_Point2D._y.subtract(this._Start_Point2D._y);
        double angleToY = Math.atan2(dx.doubleValue(), dy.doubleValue());
        if (this._Start_Point2D._y.compareTo(this._End_Point2D._y) == -1) {
            result = Math.PI - angleToY;
        } else {
            result = angleToY;
        }
        return result;
    }

    /**
     * Assuming a_LineSegment.StartPoint == this.
     * @param a_LineSegment2D Vector_LineSegment2D
     * @return BigDecimal
     */
    public BigDecimal getScalarProduct(
            Vector_LineSegment2D a_LineSegment2D) {
        if (a_LineSegment2D._Start_Point2D.compareTo(this._Start_Point2D) != 0) {
            System.out.println(
                    "a_LineSegment2D._Start_Point2D.compareTo(this._Start_Point2D) != 0 in "
                    + this.getClass().getName()
                    + ".getScalarProduct(LineSegment2D)");
        }
        return (a_LineSegment2D._End_Point2D._x.multiply(
                this._End_Point2D._x)).add(
                        (a_LineSegment2D._End_Point2D._y.multiply(
                                this._End_Point2D._y)));
    }

    /**
     * Assuming a_LineSegment.StartPoint == this.
     * @param a_LineSegment2D Vector_LineSegment2D
     * @return BigDecimal
     */
    public BigDecimal getCrossProduct(
            Vector_LineSegment2D a_LineSegment2D) {
        if (a_LineSegment2D._Start_Point2D.compareTo(this._Start_Point2D) != 0) {
            System.out.println(
                    "a_LineSegment2D._Start_Point2D.compareTo(this._Start_Point2D) != 0 in "
                    + this.getClass().getName()
                    + ".getCrossProduct(LineSegment2D)");
        }
        return (a_LineSegment2D._End_Point2D._x.multiply(
                this._End_Point2D._y)).subtract(
                        (this._End_Point2D._x.multiply(
                                a_LineSegment2D._End_Point2D._y)));
    }

    @Override
    public void applyDecimalPlacePrecision() {
        this._Start_Point2D.applyDecimalPlacePrecision();
        this._End_Point2D.applyDecimalPlacePrecision();
    }
}

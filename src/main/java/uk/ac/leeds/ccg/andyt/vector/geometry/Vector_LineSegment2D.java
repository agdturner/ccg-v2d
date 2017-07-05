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
import uk.ac.leeds.ccg.andyt.grids.core.Grids_AbstractGrid2DSquareCell;
import uk.ac.leeds.ccg.andyt.vector.core.Vector_Environment;
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
    public Vector_LineSegment2D(
            Vector_Environment ve) {
        _Vector_Environment = ve;
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
        _Vector_Environment = _StartPoint._Vector_Environment;
        this._Start_Point2D = new Vector_Point2D(
                _StartPoint);
        this._End_Point2D = new Vector_Point2D(
                _EndPoint);
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
        _Vector_Environment = _StartPoint._Vector_Environment;
        this._Start_Point2D = new Vector_Point2D(
                _StartPoint, _DecimalPlacePrecision);
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
        _Vector_Environment = a_LineSegment2D._Vector_Environment;
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
     *
     * @param g
     * @param l
     * @param a_DecimalPlacePrecision
     * @param handleOutOfMemoryError
     * @return true iff l intersects g
     * @throws Exception
     */
    public static boolean getIntersects(
            Grids_AbstractGrid2DSquareCell g,
            Vector_LineSegment2D l,
            BigDecimal tollerance,
            int a_DecimalPlacePrecision,
            boolean handleOutOfMemoryError) {
        BigDecimal[] dimensions;
        dimensions = g.get_Dimensions(handleOutOfMemoryError);
        return getIntersects(
                dimensions[1],
                dimensions[2],
                dimensions[3],
                dimensions[4],
                l,
                tollerance,
                a_DecimalPlacePrecision,
                handleOutOfMemoryError);
    }

    /**
     * @param xmin
     * @param ymin
     * @param xmax
     * @param ymax
     * @param l
     * @param a_DecimalPlacePrecision
     * @param handleOutOfMemoryError
     * @return true iff l intersects the envelope defined by minx, miny, maxx,
     * maxy.
     */
    public static boolean getIntersects(
            BigDecimal xmin,
            BigDecimal ymin,
            BigDecimal xmax,
            BigDecimal ymax,
            Vector_LineSegment2D l,
            BigDecimal tollerance,
            int a_DecimalPlacePrecision,
            boolean handleOutOfMemoryError) {
        if (l._Start_Point2D._x.compareTo(xmin) != -1
                && l._Start_Point2D._x.compareTo(xmax) != 1
                && l._Start_Point2D._y.compareTo(ymin) != -1
                && l._Start_Point2D._y.compareTo(ymax) != 1) {
            return true;
        }
        if (l._End_Point2D._x.compareTo(xmin) != -1
                && l._End_Point2D._x.compareTo(xmax) != 1
                && l._End_Point2D._y.compareTo(ymin) != -1
                && l._End_Point2D._y.compareTo(ymax) != 1) {
            return true;
        }
        Vector_Point2D bottomLeft;
        bottomLeft = new Vector_Point2D(
                l._Vector_Environment,
                xmin,
                ymin);
        Vector_Point2D bottomRight;
        bottomRight = new Vector_Point2D(
                l._Vector_Environment, xmax, ymin);
        // Check bottom
        Vector_LineSegment2D section;
        section = new Vector_LineSegment2D(
                bottomLeft, bottomRight, a_DecimalPlacePrecision);
        if (l.getIntersects(section, tollerance, a_DecimalPlacePrecision, handleOutOfMemoryError)) {
            return true;
        }
        Vector_Point2D topRight;
        topRight = new Vector_Point2D(
                l._Vector_Environment,
                xmax,
                ymax);
        // Check right
        section = new Vector_LineSegment2D(
                bottomRight, topRight, a_DecimalPlacePrecision);
        if (l.getIntersects(section, tollerance, a_DecimalPlacePrecision, handleOutOfMemoryError)) {
            return true;
        }
        Vector_Point2D topLeft;
        topLeft = new Vector_Point2D(
                l._Vector_Environment, xmin, ymax);
        // Check left
        section = new Vector_LineSegment2D(
                bottomLeft, topLeft, a_DecimalPlacePrecision);
        if (l.getIntersects(section, tollerance, a_DecimalPlacePrecision, handleOutOfMemoryError)) {
            return true;
        }
        // Check top
        Vector_LineSegment2D top;
        section = new Vector_LineSegment2D(
                topLeft, topRight, a_DecimalPlacePrecision);
        return l.getIntersects(section, tollerance, a_DecimalPlacePrecision, handleOutOfMemoryError);
    }

    public static boolean getIntersects(
            Vector_LineSegment2D l,
            Vector_LineSegment2D[] lines,
            BigDecimal tollerance,
            int a_DecimalPlacePrecision,
            boolean handleOutOfMemoryError) {
        for (int i = 0; i < lines.length; i++) {
            boolean intersects;
            intersects = l.getIntersects(
                    lines[i],
                    tollerance,
                    a_DecimalPlacePrecision,
                    handleOutOfMemoryError);
            if (intersects) {
                return true;
            }
        }
        return false;
    }

    /**
     * Intersection done by first seeing if Envelope intersects....
     *
     * @param a_LineSegment2D A Vector_LineSegment2D to test.
     * @param a_DecimalPlacePrecision The decimal place precision to be used for
     * this.
     * @param handleOutOfMemoryError
     *
     * @return True iff a_LineSegment2D getIntersects this.
     */
    public boolean getIntersects(
            Vector_LineSegment2D a_LineSegment2D,
            BigDecimal tollerance,
            int a_DecimalPlacePrecision,
            boolean handleOutOfMemoryError) {
        Vector_AbstractGeometry2D intersection;
        intersection = getIntersection(
                a_LineSegment2D,
                tollerance,
                a_DecimalPlacePrecision);
        return intersection != null;
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
            BigDecimal tollerance,
            int a_DecimalPlacePrecision,
            boolean handleOutOfMemoryError) {
        if (ignore_this_Start_Point2D) {
            if (this._Start_Point2D.getIntersects(
                    a_LineSegment2D,
                    a_DecimalPlacePrecision)) {
                return false;
            } else {
                return getIntersects(
                        a_LineSegment2D,
                        tollerance,
                        a_DecimalPlacePrecision,
                        handleOutOfMemoryError);
            }
        } else {
            return getIntersects(
                    a_LineSegment2D,
                    tollerance,
                    a_DecimalPlacePrecision,
                    handleOutOfMemoryError);
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
            BigDecimal tollerance,
            int a_DecimalPlacePrecision) {
        // Special cases
        if (this._Start_Point2D._y.compareTo(a_LineSegment2D._Start_Point2D._y) == 1
                && this._Start_Point2D._y.compareTo(a_LineSegment2D._End_Point2D._y) == 1
                && this._End_Point2D._y.compareTo(a_LineSegment2D._Start_Point2D._y) == 1
                && this._End_Point2D._y.compareTo(a_LineSegment2D._End_Point2D._y) == 1) {
            return null;
        }
        if (this._Start_Point2D._x.compareTo(a_LineSegment2D._Start_Point2D._x) == 1
                && this._Start_Point2D._x.compareTo(a_LineSegment2D._End_Point2D._x) == 1
                && this._End_Point2D._x.compareTo(a_LineSegment2D._Start_Point2D._x) == 1
                && this._End_Point2D._x.compareTo(a_LineSegment2D._End_Point2D._x) == 1) {
            return null;
        }
        if (this._Start_Point2D._y.compareTo(a_LineSegment2D._Start_Point2D._y) == -1
                && this._Start_Point2D._y.compareTo(a_LineSegment2D._End_Point2D._y) == -1
                && this._End_Point2D._y.compareTo(a_LineSegment2D._Start_Point2D._y) == -1
                && this._End_Point2D._y.compareTo(a_LineSegment2D._End_Point2D._y) == -1) {
            return null;
        }
        if (this._Start_Point2D._x.compareTo(a_LineSegment2D._Start_Point2D._x) == -1
                && this._Start_Point2D._x.compareTo(a_LineSegment2D._End_Point2D._x) == -1
                && this._End_Point2D._x.compareTo(a_LineSegment2D._Start_Point2D._x) == -1
                && this._End_Point2D._x.compareTo(a_LineSegment2D._End_Point2D._x) == -1) {
            return null;
        }
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
            BigDecimal intersectx = _Start_Point2D._x.add(
                    ua.multiply(x2minusx1));
            BigDecimal intersecty = _Start_Point2D._y.add(
                    ua.multiply(y2minusy1));
            BigDecimal deltaXEndX;
            deltaXEndX = intersectx.subtract(a_LineSegment2D._End_Point2D._x);
            BigDecimal deltaXStartX;
            deltaXStartX = intersectx.subtract(a_LineSegment2D._Start_Point2D._x);
            BigDecimal deltaYEndY;
            deltaYEndY = intersecty.subtract(a_LineSegment2D._End_Point2D._y);
            BigDecimal deltaYStartY;
            deltaYStartY = intersecty.subtract(a_LineSegment2D._Start_Point2D._y);
            if (((deltaXEndX.compareTo(tollerance) == 1 && deltaXEndX.compareTo(tollerance.negate()) == 1)
                    && (deltaXStartX.compareTo(tollerance) == 1 && deltaXStartX.compareTo(tollerance.negate()) == 1))
                    || ((deltaXEndX.compareTo(tollerance) == -1 && deltaXEndX.compareTo(tollerance.negate()) == -1)
                    && (deltaXStartX.compareTo(tollerance) == -1) && deltaXStartX.compareTo(tollerance.negate()) == -1)
                    || ((deltaYEndY.compareTo(tollerance) == 1 && deltaYEndY.compareTo(tollerance.negate()) == 1)
                    && deltaYStartY.compareTo(tollerance) == 1 && deltaYStartY.compareTo(tollerance.negate()) == 1)
                    || ((deltaYEndY.compareTo(tollerance) == -1 && deltaYEndY.compareTo(tollerance.negate()) == -1)
                    && (deltaYStartY.compareTo(tollerance) == -1 && deltaYStartY.compareTo(tollerance.negate()) == -1))) {
                return null;
            }
//            if ((intersectx1.compareTo(a_LineSegment2D._End_Point2D._x) == 1
//                    && intersectx1.compareTo(a_LineSegment2D._Start_Point2D._x) == 1)
//                    || (intersectx1.compareTo(a_LineSegment2D._End_Point2D._x) == -1
//                    && intersectx1.compareTo(a_LineSegment2D._Start_Point2D._x) == -1)
//                    || (intersecty1.compareTo(a_LineSegment2D._End_Point2D._y) == 1
//                    && intersecty1.compareTo(a_LineSegment2D._Start_Point2D._y) == 1)
//                    || (intersecty1.compareTo(a_LineSegment2D._End_Point2D._y) == -1
//                    && intersecty1.compareTo(a_LineSegment2D._Start_Point2D._y) == -1)) {
//                return null;
//            }
//            BigDecimal ub = ubmultiplicand.divide(
//                    denominator,
//                    a_DecimalPlacePrecision,
//                RoundingMode.HALF_UP);
            return new Vector_Point2D(
                    _Vector_Environment,
                    intersectx,
                    intersecty,
                    a_DecimalPlacePrecision);
        } else {
            BigDecimal ub = ubmultiplicand.divide(
                    denominator,
                    a_DecimalPlacePrecision,
                    RoundingMode.CEILING);
            if (ub.compareTo(BigDecimal.ONE) != 1
                    && ub.compareTo(BigDecimal.ZERO) != -1) {
                BigDecimal intersectx = a_LineSegment2D._Start_Point2D._x.add(
                        ub.multiply(x4minusx3));
                BigDecimal intersecty = a_LineSegment2D._Start_Point2D._y.add(
                        ub.multiply(y4minusy3));
                BigDecimal deltaXEndX;
                deltaXEndX = intersectx.subtract(a_LineSegment2D._End_Point2D._x);
                BigDecimal deltaXStartX;
                deltaXStartX = intersectx.subtract(a_LineSegment2D._Start_Point2D._x);
                BigDecimal deltaYEndY;
                deltaYEndY = intersecty.subtract(a_LineSegment2D._End_Point2D._y);
                BigDecimal deltaYStartY;
                deltaYStartY = intersecty.subtract(a_LineSegment2D._Start_Point2D._y);
                if (((deltaXEndX.compareTo(tollerance) == 1 && deltaXEndX.compareTo(tollerance.negate()) == 1)
                        && (deltaXStartX.compareTo(tollerance) == 1 && deltaXStartX.compareTo(tollerance.negate()) == 1))
                        || ((deltaXEndX.compareTo(tollerance) == -1 && deltaXEndX.compareTo(tollerance.negate()) == -1)
                        && (deltaXStartX.compareTo(tollerance) == -1) && deltaXStartX.compareTo(tollerance.negate()) == -1)
                        || ((deltaYEndY.compareTo(tollerance) == 1 && deltaYEndY.compareTo(tollerance.negate()) == 1)
                        && deltaYStartY.compareTo(tollerance) == 1 && deltaYStartY.compareTo(tollerance.negate()) == 1)
                        || ((deltaYEndY.compareTo(tollerance) == -1 && deltaYEndY.compareTo(tollerance.negate()) == -1)
                        && (deltaYStartY.compareTo(tollerance) == -1 && deltaYStartY.compareTo(tollerance.negate()) == -1))) {
                    return null;
                }

//                if ((deltaXEndX.compareTo(tollerance) == 1
//                        && deltaXStartX.compareTo(tollerance) == 1)
//                        || (deltaXEndX.compareTo(tollerance) == -1
//                        && deltaXStartX.compareTo(tollerance) == -1)
//                        || (deltaYEndY.compareTo(tollerance) == 1
//                        && deltaYStartY.compareTo(tollerance) == 1)
//                        || (deltaYEndY.compareTo(tollerance) == -1
//                        && deltaYStartY.compareTo(tollerance) == -1)) {
//                    return null;
//                }
//                if ((intersectx2.compareTo(a_LineSegment2D._End_Point2D._x) == 1
//                        && intersectx2.compareTo(a_LineSegment2D._Start_Point2D._x) == 1)
//                        || (intersectx2.compareTo(a_LineSegment2D._End_Point2D._x) == -1
//                        && intersectx2.compareTo(a_LineSegment2D._Start_Point2D._x) == -1)
//                        || (intersecty2.compareTo(a_LineSegment2D._End_Point2D._y) == 1
//                        && intersecty2.compareTo(a_LineSegment2D._Start_Point2D._y) == 1)
//                        || (intersecty2.compareTo(a_LineSegment2D._End_Point2D._y) == -1
//                        && intersecty2.compareTo(a_LineSegment2D._Start_Point2D._y) == -1)) {
//                    return null;
//                }
                return new Vector_Point2D(
                        _Vector_Environment,
                        intersectx,
                        intersecty,
                        a_DecimalPlacePrecision);
            }
        }
        return null;
    }

    /**
     * l._Start_Point2D._x >= xmin && l._Start_Point2D._x < xmax &&
     * l._Start_Point2D._y >= ymin && l._Start_Point2D._y < ymax
     *
     * @param tollerance
     * @param xmin
     * @param ymin
     * @param xmax
     * @param ymax
     * @param l
     * @param directionIn
     * @param a_DecimalPlacePrecision
     * @param handleOutOfMemoryError
     * @return
     */
    public static Object[] getLineToIntersectLineRemainingDirection(
            BigDecimal tollerance,
            BigDecimal xmin,
            BigDecimal ymin,
            BigDecimal xmax,
            BigDecimal ymax,
            Vector_LineSegment2D l,
            Integer directionIn,
            int a_DecimalPlacePrecision,
            boolean handleOutOfMemoryError) {
        Object[] result;
        result = new Object[3];
        if (directionIn == null) {
            //if (l._Start_Point2D._x == xmin) {
            BigDecimal deltaXmin;
            deltaXmin = l._Start_Point2D._x.subtract(xmin);
            if (deltaXmin.compareTo(tollerance) == -1 && deltaXmin.compareTo(tollerance.negate()) == 1) {
                //if (l._Start_Point2D._y == ymin) {
                BigDecimal deltaYmin;
                deltaYmin = l._Start_Point2D._y.subtract(ymin);
                if (deltaYmin.compareTo(tollerance) == -1 && deltaYmin.compareTo(tollerance.negate()) == 1) {
                    directionIn = 1;
                } else {
                    //if (l._Start_Point2D._y == ymax) {
                    BigDecimal deltaYmax;
                    deltaYmax = l._Start_Point2D._y.subtract(ymax);
                    if (deltaYmax.compareTo(tollerance) == -1 && deltaYmax.compareTo(tollerance.negate()) == 1) {
                        directionIn = 3;
                    } else {
                        directionIn = 2;
                    }
                }
            } else {
                //if (l._Start_Point2D._x == xmax) {
                BigDecimal deltaXmax;
                deltaXmax = l._Start_Point2D._y.subtract(xmax);
                if (deltaXmax.compareTo(tollerance) == -1 && deltaXmax.compareTo(tollerance.negate()) == 1) {
                    //if (l._Start_Point2D._y == ymin) {
                    BigDecimal deltaYmin;
                    deltaYmin = l._Start_Point2D._y.subtract(ymin);
                    if (deltaYmin.compareTo(tollerance) == -1 && deltaYmin.compareTo(tollerance.negate()) == 1) {
                        directionIn = 7;
                    } else {
                        //if (l._Start_Point2D._y == ymax) {
                        BigDecimal deltaYmax;
                        deltaYmax = l._Start_Point2D._y.subtract(ymax);
                        if (deltaYmax.compareTo(tollerance) == -1 && deltaYmax.compareTo(tollerance.negate()) == 1) {
                            directionIn = 5;
                        } else {
                            directionIn = 6;
                        }
                    }
                } else {
                    //if (l._Start_Point2D._y == ymin) {
                    BigDecimal deltaYmin;
                    deltaYmin = l._Start_Point2D._y.subtract(ymin);
                    if (deltaYmin.compareTo(tollerance) == -1 && deltaYmin.compareTo(tollerance.negate()) == 1) {
                        directionIn = 4;
                    } else {
                        //if (l._Start_Point2D._y == ymax) {
                        BigDecimal deltaYmax;
                        deltaYmax = l._Start_Point2D._y.subtract(ymax);
                        if (deltaYmax.compareTo(tollerance) == -1 && deltaYmax.compareTo(tollerance.negate()) == 1) {
                            directionIn = 0;
                        } else {
                            if (l._Start_Point2D._y.compareTo(l._End_Point2D._y) == -1) {
                                if (l._Start_Point2D._x.compareTo(l._End_Point2D._x) == -1) {
                                    directionIn = 1;
                                } else {
                                    if (l._Start_Point2D._x.compareTo(l._End_Point2D._x) == 0) {
                                        directionIn = 0;
                                    } else {
                                        directionIn = 7;
                                    }
                                }
                            } else {
                                if (l._Start_Point2D._y.compareTo(l._End_Point2D._y) == 0) {
                                    if (l._Start_Point2D._x.compareTo(l._End_Point2D._x) == -1) {
                                        directionIn = 2;
                                    } else {
                                        if (l._Start_Point2D._x.compareTo(l._End_Point2D._x) == 0) {
                                            directionIn = 0; // This should not happen
                                        } else {
                                            directionIn = 6;
                                        }
                                    }
                                } else {
                                    if (l._Start_Point2D._x.compareTo(l._End_Point2D._x) == -1) {
                                        directionIn = 3;
                                        //directionIn = 7;
                                    } else {
                                        if (l._Start_Point2D._x.compareTo(l._End_Point2D._x) == 0) {
                                            directionIn = 4;
                                        } else {
                                            directionIn = 5;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Vector_Point2D bottomLeft;
        Vector_Point2D bottomRight;
        Vector_Point2D topRight;
        Vector_Point2D topLeft;
        bottomLeft = new Vector_Point2D(
                l._Vector_Environment, xmin, ymin);
        bottomRight = new Vector_Point2D(
                l._Vector_Environment, xmax, ymin);
        topLeft = new Vector_Point2D(
                l._Vector_Environment, xmin, ymax);
        topRight = new Vector_Point2D(
                l._Vector_Environment, xmax, ymax);
        Object[] lineToIntersectLineRemainingDirection = null;
        if (directionIn == 0) {
            // check top
            lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckTop(
                    tollerance,
                    xmin, ymin, xmax, ymax, l,
                    a_DecimalPlacePrecision, handleOutOfMemoryError,
                    topLeft, topRight);
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check right
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckRight(
                        tollerance,
                        xmin, ymin, xmax, ymax, l,
                        a_DecimalPlacePrecision, handleOutOfMemoryError,
                        topRight, bottomRight);
            }
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check left
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckLeft(
                        tollerance,
                        xmin, ymin, xmax, ymax, l,
                        a_DecimalPlacePrecision, handleOutOfMemoryError,
                        topLeft, bottomLeft);
            }
        }

        if (directionIn == 1) {
            // check top
            lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckTop(
                    tollerance,
                    xmin, ymin, xmax, ymax, l,
                    a_DecimalPlacePrecision, handleOutOfMemoryError,
                    topLeft, topRight);
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check right
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckRight(
                        tollerance,
                        xmin, ymin, xmax, ymax, l,
                        a_DecimalPlacePrecision, handleOutOfMemoryError,
                        topRight, bottomRight);
            }
        }

        if (directionIn == 2) {
            // check top
            lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckTop(
                    tollerance,
                    xmin, ymin, xmax, ymax, l,
                    a_DecimalPlacePrecision, handleOutOfMemoryError,
                    topLeft, topRight);
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check right
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckRight(
                        tollerance,
                        xmin, ymin, xmax, ymax, l,
                        a_DecimalPlacePrecision, handleOutOfMemoryError,
                        topRight, bottomRight);
            }
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check bottom
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckBottom(
                        tollerance,
                        xmin, ymin, xmax, ymax, l,
                        a_DecimalPlacePrecision, handleOutOfMemoryError,
                        bottomLeft, bottomRight);
            }
        }

        if (directionIn == 3) {
            // check right
            lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckRight(
                    tollerance,
                    xmin, ymin, xmax, ymax, l,
                    a_DecimalPlacePrecision, handleOutOfMemoryError,
                    topRight, bottomRight);
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check bottom
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckBottom(
                        tollerance,
                        xmin, ymin, xmax, ymax, l,
                        a_DecimalPlacePrecision, handleOutOfMemoryError,
                        bottomLeft, bottomRight);
            }
        }
        if (directionIn == 4) {
            // check right
            lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckRight(
                    tollerance,
                    xmin, ymin, xmax, ymax, l,
                    a_DecimalPlacePrecision, handleOutOfMemoryError,
                    topRight, bottomRight);
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check bottom
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckBottom(
                        tollerance,
                        xmin, ymin, xmax, ymax, l,
                        a_DecimalPlacePrecision, handleOutOfMemoryError,
                        bottomLeft, bottomRight);
            }
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check left
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckLeft(
                        tollerance,
                        xmin, ymin, xmax, ymax, l,
                        a_DecimalPlacePrecision, handleOutOfMemoryError,
                        topLeft, bottomLeft);
            }
        }

        if (directionIn == 5) {
            // check bottom
            lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckBottom(
                    tollerance,
                    xmin, ymin, xmax, ymax, l,
                    a_DecimalPlacePrecision, handleOutOfMemoryError,
                    bottomLeft, bottomRight);
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check left
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckLeft(
                        tollerance,
                        xmin, ymin, xmax, ymax, l,
                        a_DecimalPlacePrecision, handleOutOfMemoryError,
                        topLeft, bottomLeft);
            }
        }

        if (directionIn == 6) {
            // check bottom
            lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckBottom(
                    tollerance,
                    xmin, ymin, xmax, ymax, l,
                    a_DecimalPlacePrecision, handleOutOfMemoryError, bottomLeft, bottomRight);
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check left
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckLeft(
                        tollerance,
                        xmin, ymin, xmax, ymax, l,
                        a_DecimalPlacePrecision, handleOutOfMemoryError,
                        topLeft, bottomLeft);
            }
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check top
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckTop(
                        tollerance,
                        xmin, ymin, xmax, ymax, l, a_DecimalPlacePrecision, handleOutOfMemoryError, topLeft, topRight);
            }
        }

        if (directionIn == 7) {
            // check left
            lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckLeft(
                    tollerance,
                    xmin, ymin, xmax, ymax, l,
                    a_DecimalPlacePrecision, handleOutOfMemoryError,
                    topLeft, bottomLeft);
            if (lineToIntersectLineRemainingDirection[2] == null) {
                // check top
                lineToIntersectLineRemainingDirection = doLineToIntersectLineRemainingDirectionCheckTop(
                        tollerance,
                        xmin, ymin, xmax, ymax, l,
                        a_DecimalPlacePrecision, handleOutOfMemoryError,
                        topLeft, topRight);
            }
        }
        // Do remainder
        if (lineToIntersectLineRemainingDirection[2] == null) {
            result[0] = l;
            result[1] = null;
            result[2] = null;
        } else {
            result = lineToIntersectLineRemainingDirection;
        }
        return result;
    }

    protected static Object[] doLineToIntersectLineRemainingDirectionCheckRight(
            BigDecimal tollerance,
            BigDecimal xmin,
            BigDecimal ymin,
            BigDecimal xmax,
            BigDecimal ymax,
            Vector_LineSegment2D l,
            int a_DecimalPlacePrecision,
            boolean handleOutOfMemoryError,
            Vector_Point2D topRight,
            Vector_Point2D bottomRight) {
        Object[] result;
        result = new Object[3];
        Vector_LineSegment2D line = null;
        Vector_LineSegment2D remainingLine = null;
        Integer directionOut = null;
        Vector_LineSegment2D section;
        Object[] lineToIntersectIntersectPoint;
        topRight = new Vector_Point2D(
                l._Vector_Environment, xmax, ymax);
        section = new Vector_LineSegment2D(
                bottomRight, topRight, a_DecimalPlacePrecision);
        if (l.getIntersects(section, tollerance, a_DecimalPlacePrecision, handleOutOfMemoryError)) {
            lineToIntersectIntersectPoint = getLineToIntersectIntersectPoint(
                    l,
                    section,
                    tollerance,
                    a_DecimalPlacePrecision);
            line = (Vector_LineSegment2D) lineToIntersectIntersectPoint[0];
            Vector_Point2D newStartPoint;
            newStartPoint = (Vector_Point2D) lineToIntersectIntersectPoint[1];
            //if (newStartPoint._y.compareTo(xmax) == 0) {
            BigDecimal deltaXmax;
            deltaXmax = newStartPoint._x.subtract(xmax);
            if (deltaXmax.compareTo(tollerance) == -1 && deltaXmax.compareTo(tollerance.negate()) == 1) {
                //if (newStartPoint._y.compareTo(ymax) == 0) {
                BigDecimal deltaYmax;
                deltaYmax = newStartPoint._y.subtract(ymax);
                if (deltaYmax.compareTo(tollerance) == -1 && deltaYmax.compareTo(tollerance.negate()) == 1) {
                    directionOut = 1;
                } else {
                    //if (newStartPoint._y.compareTo(ymin) == 0) {
                    BigDecimal deltaYmin;
                    deltaYmin = newStartPoint._y.subtract(ymin);
                    if (deltaYmin.compareTo(tollerance) == -1 && deltaYmin.compareTo(tollerance.negate()) == 1) {
                        directionOut = 3;
                    } else {
                        directionOut = 2;
                    }
                }
            } else {
                directionOut = 2;
            }
            remainingLine = new Vector_LineSegment2D(
                    (Vector_Point2D) lineToIntersectIntersectPoint[1],
                    l._End_Point2D, a_DecimalPlacePrecision);
        }
        result[0] = line;
        result[1] = remainingLine;
        result[2] = directionOut;
        return result;
    }

    protected static Object[] doLineToIntersectLineRemainingDirectionCheckLeft(
            BigDecimal tollerance,
            BigDecimal xmin,
            BigDecimal ymin,
            BigDecimal xmax,
            BigDecimal ymax,
            Vector_LineSegment2D l,
            int a_DecimalPlacePrecision,
            boolean handleOutOfMemoryError,
            Vector_Point2D topLeft,
            Vector_Point2D bottomLeft) {
        Object[] result;
        result = new Object[3];
        Vector_LineSegment2D line = null;
        Vector_LineSegment2D remainingLine = null;
        Integer directionOut = null;
        Vector_LineSegment2D section;
        Object[] lineToIntersectIntersectPoint;
        section = new Vector_LineSegment2D(
                bottomLeft, topLeft, a_DecimalPlacePrecision);
        if (l.getIntersects(section, tollerance, a_DecimalPlacePrecision, handleOutOfMemoryError)) {
            lineToIntersectIntersectPoint = getLineToIntersectIntersectPoint(
                    l,
                    section,
                    tollerance,
                    a_DecimalPlacePrecision);
            line = (Vector_LineSegment2D) lineToIntersectIntersectPoint[0];
            Vector_Point2D newStartPoint;
            newStartPoint = (Vector_Point2D) lineToIntersectIntersectPoint[1];
            //if (newStartPoint._y.compareTo(xmin) == 0) {
            BigDecimal deltaXmin;
            deltaXmin = newStartPoint._x.subtract(xmin);
            if (deltaXmin.compareTo(tollerance) == -1 && deltaXmin.compareTo(tollerance.negate()) == 1) {
                //if (newStartPoint._y.compareTo(ymax) == 0) {
                BigDecimal deltaYmax;
                deltaYmax = newStartPoint._y.subtract(ymax);
                if (deltaYmax.compareTo(tollerance) == -1 && deltaYmax.compareTo(tollerance.negate()) == 1) {
                    directionOut = 7;
                } else {
                    //if (newStartPoint._y.compareTo(ymin) == 0) {
                    BigDecimal deltaYmin;
                    deltaYmin = newStartPoint._y.subtract(ymin);
                    if (deltaYmin.compareTo(tollerance) == -1 && deltaYmin.compareTo(tollerance.negate()) == 1) {
                        directionOut = 5;
                    } else {
                        directionOut = 6;
                    }
                }
            } else {
                directionOut = 6;
            }
            remainingLine = new Vector_LineSegment2D(
                    (Vector_Point2D) lineToIntersectIntersectPoint[1],
                    l._End_Point2D, a_DecimalPlacePrecision);
        }
        result[0] = line;
        result[1] = remainingLine;
        result[2] = directionOut;
        return result;
    }

    protected static Object[] doLineToIntersectLineRemainingDirectionCheckTop(
            BigDecimal tollerance,
            BigDecimal xmin,
            BigDecimal ymin,
            BigDecimal xmax,
            BigDecimal ymax,
            Vector_LineSegment2D l,
            int a_DecimalPlacePrecision,
            boolean handleOutOfMemoryError,
            Vector_Point2D topLeft,
            Vector_Point2D topRight) {
        Object[] result;
        result = new Object[3];
        Vector_LineSegment2D line = null;
        Vector_LineSegment2D remainingLine = null;
        Integer directionOut = null;
        Vector_LineSegment2D section;
        Object[] lineToIntersectIntersectPoint;
        section = new Vector_LineSegment2D(
                topLeft, topRight, a_DecimalPlacePrecision);
        if (l.getIntersects(section, tollerance, a_DecimalPlacePrecision, handleOutOfMemoryError)) {
            lineToIntersectIntersectPoint = getLineToIntersectIntersectPoint(
                    l,
                    section,
                    tollerance,
                    a_DecimalPlacePrecision);
            line = (Vector_LineSegment2D) lineToIntersectIntersectPoint[0];
            Vector_Point2D newStartPoint;
            newStartPoint = (Vector_Point2D) lineToIntersectIntersectPoint[1];
            //if (newStartPoint._y.compareTo(ymax) == 0) {
            BigDecimal deltaYmax;
            deltaYmax = newStartPoint._x.subtract(ymax);
            if (deltaYmax.compareTo(tollerance) == -1 && deltaYmax.compareTo(tollerance.negate()) == 1) {
                //if (newStartPoint._y.compareTo(xmax) == 0) {
                BigDecimal deltaXmax;
                deltaXmax = newStartPoint._x.subtract(xmax);
                if (deltaXmax.compareTo(tollerance) == -1 && deltaXmax.compareTo(tollerance.negate()) == 1) {
                    directionOut = 1;
                } else {
                    //if (newStartPoint._y.compareTo(xmin) == 0) {
                    BigDecimal deltaXmin;
                    deltaXmin = newStartPoint._x.subtract(xmin);
                    if (deltaXmin.compareTo(tollerance) == -1 && deltaXmin.compareTo(tollerance.negate()) == 1) {
                        directionOut = 7;
                    } else {
                        directionOut = 0;
                    }
                }
            } else {
                directionOut = 0;
            }
            remainingLine = new Vector_LineSegment2D(
                    (Vector_Point2D) lineToIntersectIntersectPoint[1],
                    l._End_Point2D, a_DecimalPlacePrecision);
        }
        result[0] = line;
        result[1] = remainingLine;
        result[2] = directionOut;
        return result;
    }

    protected static Object[] doLineToIntersectLineRemainingDirectionCheckBottom(
            BigDecimal tollerance,
            BigDecimal xmin,
            BigDecimal ymin,
            BigDecimal xmax,
            BigDecimal ymax,
            Vector_LineSegment2D l,
            int a_DecimalPlacePrecision,
            boolean handleOutOfMemoryError,
            Vector_Point2D bottomLeft,
            Vector_Point2D bottomRight) {
        Object[] result;
        result = new Object[3];
        Vector_LineSegment2D line = null;
        Vector_LineSegment2D remainingLine = null;
        Integer directionOut = null;
        Vector_LineSegment2D section;
        Object[] lineToIntersectIntersectPoint;
        section = new Vector_LineSegment2D(
                bottomLeft, bottomRight, a_DecimalPlacePrecision);
        if (l.getIntersects(section, tollerance, a_DecimalPlacePrecision, handleOutOfMemoryError)) {
            lineToIntersectIntersectPoint = getLineToIntersectIntersectPoint(
                    l,
                    section,
                    tollerance,
                    a_DecimalPlacePrecision);
            line = (Vector_LineSegment2D) lineToIntersectIntersectPoint[0];
            Vector_Point2D newStartPoint;
            newStartPoint = (Vector_Point2D) lineToIntersectIntersectPoint[1];
            //if (newStartPoint._y.compareTo(ymin) == 0) {
            BigDecimal deltaYmin;
            deltaYmin = newStartPoint._y.subtract(ymin);
            if (deltaYmin.compareTo(tollerance) == -1 && deltaYmin.compareTo(tollerance.negate()) == 1) {
                //if (newStartPoint._y.compareTo(xmax) == 0) {
                BigDecimal deltaXmax;
                deltaXmax = newStartPoint._x.subtract(xmax);
                if (deltaXmax.compareTo(tollerance) == -1 && deltaXmax.compareTo(tollerance.negate()) == 1) {
                    directionOut = 3;
                } else {
                    //if (newStartPoint._y.compareTo(xmin) == 0) {
                    BigDecimal deltaXmin;
                    deltaXmin = newStartPoint._x.subtract(xmin);
                    if (deltaXmin.compareTo(tollerance) == -1 && deltaXmin.compareTo(tollerance.negate()) == 1) {
                        directionOut = 5;
                    } else {
                        directionOut = 4;
                    }
                }
            } else {
                directionOut = 4;
            }
            remainingLine = new Vector_LineSegment2D(
                    (Vector_Point2D) lineToIntersectIntersectPoint[1],
                    l._End_Point2D, a_DecimalPlacePrecision);
        }
        result[0] = line;
        result[1] = remainingLine;
        result[2] = directionOut;
        return result;
    }

    protected static Object[] getLineToIntersectIntersectPoint(
            Vector_LineSegment2D l,
            Vector_LineSegment2D section,
            BigDecimal tollerance,
            int a_DecimalPlacePrecision) {
        Object[] result;
        result = new Object[2];
        Vector_LineSegment2D lineToIntersect = null;
        Vector_Point2D intersectPoint = null;
        Vector_AbstractGeometry2D intersection;
        intersection = l.getIntersection(section, tollerance, a_DecimalPlacePrecision);
        if (intersection instanceof Vector_Point2D) {
            intersectPoint = (Vector_Point2D) intersection;
            lineToIntersect = new Vector_LineSegment2D(
                    l._Start_Point2D,
                    intersectPoint,
                    a_DecimalPlacePrecision);
        } else {
            lineToIntersect = (Vector_LineSegment2D) intersection;
            intersectPoint = lineToIntersect._End_Point2D;
        }
        result[0] = lineToIntersect;
        result[1] = intersectPoint;
        return result;
    }

    /**
     * TODO: Control precision! The angle returned is the smallest angle between
     * this and the X axis and so is between Math.PI and -Math.PI.
     *
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
     *
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
     *
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
